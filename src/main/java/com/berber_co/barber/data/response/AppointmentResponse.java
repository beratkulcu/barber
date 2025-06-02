package com.berber_co.barber.data.response;

import com.berber_co.barber.enums.AppointmentStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentResponse {
    private Long appointmentId;
    private String barberName;
    private String berberServiceName;
    private LocalDateTime appointmentTime;
    private AppointmentStatus status;
}
