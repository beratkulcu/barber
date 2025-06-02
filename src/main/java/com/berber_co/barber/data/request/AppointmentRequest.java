package com.berber_co.barber.data.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record AppointmentRequest(
        @NotNull(message = "Service cannot be null")
        Long barberServiceId,
        @NotNull(message = "Appointment date cannot be null")
        @Future(message = "Appointment date must be in the future")
        LocalDateTime appointmentDate
) {
}
