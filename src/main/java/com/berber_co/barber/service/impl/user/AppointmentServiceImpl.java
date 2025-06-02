package com.berber_co.barber.service.impl.user;

import com.berber_co.barber.configuration.constans.ApiResponse;
import com.berber_co.barber.data.request.AppointmentRequest;
import com.berber_co.barber.data.response.AppointmentResponse;
import com.berber_co.barber.entity.barber.BarberService;
import com.berber_co.barber.entity.barber.Seller;
import com.berber_co.barber.entity.user.Appointment;
import com.berber_co.barber.entity.user.User;
import com.berber_co.barber.entity.user.UserProfile;
import com.berber_co.barber.enums.ActivityStatus;
import com.berber_co.barber.enums.AppointmentStatus;
import com.berber_co.barber.enums.NotificationType;
import com.berber_co.barber.exception.AppException;
import com.berber_co.barber.repository.barber.BarberServiceRepository;
import com.berber_co.barber.repository.barber.SellerAvailabilityRepository;
import com.berber_co.barber.repository.user.AppointmentRepository;
import com.berber_co.barber.repository.user.UserProfileRepository;
import com.berber_co.barber.repository.user.UserRepository;
import com.berber_co.barber.service.notification.NotificationMessage;
import com.berber_co.barber.service.notification.NotificationService;
import com.berber_co.barber.service.user.AppointmentService;
import com.berber_co.barber.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static com.berber_co.Validations.*;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final BarberServiceRepository barberServiceRepository;
    private final UserRepository userRepository;
    private final SellerAvailabilityRepository sellerAvailabilityRepository;
    private final UserProfileRepository userProfileRepository;
    private final NotificationService notificationService;

    @Override
    @Transactional
    public ApiResponse<Boolean> createAppointment(AppointmentRequest request) {
        Long userId = SecurityUtil.getUserId();

        UserProfile userProfile = userProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new AppException(ERROR, "You cannot make an appointment without creating a profile."));

        User user = userProfile.getUser();

        BarberService barberService = barberServiceRepository.findById(request.barberServiceId())
                .orElseThrow(() -> new AppException(ERROR, BARBER_SERVICE_NOT_FOUND));

        Seller seller = barberService.getSeller();
        LocalDateTime appointmentTime = request.appointmentDate();

        if (hasOverlappingUserAppointment(userId, appointmentTime)) {
            throw new AppException(ERROR, "You already have another appointment near this time");
        }

        if (!seller.getCity().equals(userProfile.getCity())) {
            throw new AppException(ERROR, "You can't book a service in another city");
        }

        if (!ActivityStatus.ACTIVE.equals(seller.getStatus())) {
            throw new AppException(ERROR, "Seller is not active");
        }

        LocalDateTime maxAllowedDate = LocalDateTime.now().plusMonths(6);
        if (appointmentTime.isAfter(maxAllowedDate)) {
            throw new AppException(ERROR, "Appointment date cannot be more than 6 months in advance");
        }


        if (!isWithinWorkingHours(seller, appointmentTime)) {
            throw new AppException(ERROR, "Appointment time is outside of working hours");
        }

        if (isAppointmentSlotOccupied(seller, appointmentTime)) {
            throw new AppException(ERROR, "Appointment slot is already occupied");
        }

        if (appointmentRepository.existsByUserIdAndAppointmentDate(userId, appointmentTime)) {
            throw new AppException(ERROR, "You already have an appointment at this time");
        }

        Appointment appointment = Appointment.builder()
                .barberService(barberService)
                .seller(seller)
                .appointmentDate(appointmentTime)
                .appointmentStatus(AppointmentStatus.PENDING)
                .customerName(userProfile.getFirstName() + " " + userProfile.getLastName())
                .customerPhone(userProfile.getPhoneNumber())
                .user(user)
                .build();
        appointmentRepository.save(appointment);

        notificationService.sendNotification(new NotificationMessage(
                seller.getId(),
                "New Appointment",
                NotificationType.REMINDER.getValue(),
                LocalDateTime.now(),
                null
        ));

        return ApiResponse.success(Boolean.TRUE);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppointmentResponse> getAppointmentsByUser() {
        Long userId = SecurityUtil.getUserId();

        List<Appointment> appointments = appointmentRepository.findAllByUserIdOrderByAppointmentDateDesc(userId);

        if (appointments.isEmpty()){
            throw new AppException(ERROR, "No appointments found for this user");
        }

        return appointments.stream()
                .map(this::toAppointmentResponse)
                .toList();
    }

    @Override
    public ApiResponse<Boolean> cancelAppointment(Long appointmentId) {
        Long userId = SecurityUtil.getUserId();

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new AppException(ERROR, APPOINTMENT_NOT_FOUND));

        if (!appointment.getUser().getId().equals(userId) && !appointment.getSeller().getId().equals(userId)) {
            throw new AppException(ERROR, "You are not authorized to cancel this appointment");
        }

        if (appointment.getAppointmentStatus() == AppointmentStatus.COMPLETED) {
            throw new AppException(ERROR, "Cannot cancel a completed appointment");
        }

        if (appointment.getAppointmentStatus() == AppointmentStatus.CANCELED) {
            throw new AppException(ERROR, "Appointment is already canceled");
        }

        appointment.setAppointmentStatus(AppointmentStatus.CANCELED);
        appointmentRepository.save(appointment);

        notificationService.sendNotification(new NotificationMessage(
                appointment.getSeller().getId(),
                "Appointment Canceled",
                NotificationType.CANCEL.getValue(),
                LocalDateTime.now(),
                null
        ));

        return ApiResponse.success(Boolean.TRUE);
    }

    private boolean isWithinWorkingHours(Seller seller, LocalDateTime appointmentDate) {
        DayOfWeek dayOfWeek = appointmentDate.getDayOfWeek();
        LocalTime appointmentTime = appointmentDate.toLocalTime();

        return sellerAvailabilityRepository.findBySellerAndDayOfWeek(seller, dayOfWeek)
                .filter(availability -> Boolean.TRUE.equals(availability.getIsOpen())
                        && !appointmentTime.isBefore(availability.getStartTime())
                        && !appointmentTime.isAfter(availability.getEndTime()))
                .isPresent();
    }

    private boolean isAppointmentSlotOccupied(Seller seller, LocalDateTime appointmentDate) {
        LocalDateTime startTime = appointmentDate;
        LocalDateTime endTime = appointmentDate.plusMinutes(30);

        return appointmentRepository.existsBySellerAndAppointmentDateBetweenAndAppointmentStatus(
                seller, startTime, endTime, AppointmentStatus.COMPLETED);
    }

    private boolean hasOverlappingUserAppointment(Long userId, LocalDateTime appointmentTime) {
        LocalDateTime start = appointmentTime.minusMinutes(60);
        LocalDateTime end = appointmentTime.plusMinutes(60);
        return appointmentRepository.existsByUserIdAndAppointmentDateBetween(userId, start, end);
    }

    private AppointmentResponse toAppointmentResponse(Appointment appointment) {
        return new AppointmentResponse(
                appointment.getId(),
                appointment.getSeller().getStoreName(),
                appointment.getBarberService().getTitle(),
                appointment.getAppointmentDate(),
                appointment.getAppointmentStatus()
        );
    }

}
