package com.berber_co.barber.repository.user;

import com.berber_co.barber.entity.barber.Seller;
import com.berber_co.barber.entity.user.Appointment;
import com.berber_co.barber.enums.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    boolean existsBySellerAndAppointmentDateBetweenAndAppointmentStatus(Seller seller, LocalDateTime start, LocalDateTime end, AppointmentStatus appointmentStatus);

    boolean existsByUserIdAndAppointmentDate(Long userId, LocalDateTime appointmentDate);

    boolean existsByUserIdAndAppointmentDateBetween(Long userId, LocalDateTime start, LocalDateTime end);

    List<Appointment> findAllByUserIdOrderByAppointmentDateDesc(Long userId);
}
