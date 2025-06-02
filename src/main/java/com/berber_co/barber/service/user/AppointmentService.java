package com.berber_co.barber.service.user;

import com.berber_co.barber.configuration.constans.ApiResponse;
import com.berber_co.barber.data.request.AppointmentRequest;
import com.berber_co.barber.data.response.AppointmentResponse;

import java.util.List;

public interface AppointmentService {
    ApiResponse<Boolean> createAppointment(AppointmentRequest request);

    List<AppointmentResponse> getAppointmentsByUser();

    ApiResponse<Boolean> cancelAppointment(Long appointmentId);
}
