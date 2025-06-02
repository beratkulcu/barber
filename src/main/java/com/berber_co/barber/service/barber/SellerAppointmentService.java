package com.berber_co.barber.service.barber;

import com.berber_co.barber.configuration.constans.ApiResponse;
import com.berber_co.barber.entity.user.Appointment;
import com.berber_co.barber.enums.AppointmentStatus;
import com.berber_co.barber.repository.user.AppointmentRepository;
import com.berber_co.barber.service.notification.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.berber_co.Validations.ERROR;

@Slf4j
@Service
@RequiredArgsConstructor
public class SellerAppointmentService {
    private final NotificationService notificationService;
    private final AppointmentRepository appointmentRepository;

    public ApiResponse<Boolean> confirmAppointment(Long appointmentId) {
        try {
            Optional<Appointment> optionalAppointment = appointmentRepository.findById(appointmentId);
            if (optionalAppointment.isEmpty()) {
                return ApiResponse.error(ERROR, "Appointment not found");
            }

            Appointment appointment = optionalAppointment.get();
            appointment.setAppointmentStatus(AppointmentStatus.CONFIRMED);
            appointmentRepository.save(appointment);

            String message = "Appointment confirmed";
            notificationService.sendSellerConfirmationNotification(appointmentId, message);
            return ApiResponse.success(true);
        } catch (Exception e) {
            log.error("ERROR", e.getMessage());
            return ApiResponse.error(ERROR, "Appointment confirmation failed");
        }
    }

    public ApiResponse<Boolean> cancelAppointment(Long appointmentId, String reason) {
        try {
            Optional<Appointment> optionalAppointment = appointmentRepository.findById(appointmentId);
            if (optionalAppointment.isEmpty()) {
                return ApiResponse.error(ERROR, "Appointment not found");
            }

            Appointment appointment = optionalAppointment.get();
            appointment.setAppointmentStatus(AppointmentStatus.CANCELED);
            appointment.setReason(reason);
            appointmentRepository.save(appointment);

            String message = "Randevu iptal edildi";
            notificationService.sendSellerCancellationNotification(appointmentId, message, reason);
            log.info("Randevu iptal edildi: Appointment ID: {}, Reason: {}", appointmentId, reason);
            return ApiResponse.success(true);
        } catch (Exception e) {
            log.error("Randevu iptal hatası: {}", e.getMessage());
            return ApiResponse.error(ERROR,"Randevu iptal işlemi başarısız");
        }
    }
}
