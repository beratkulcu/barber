package com.berber_co.barber.service.barber;

import com.berber_co.barber.configuration.constans.ApiResponse;
import com.berber_co.barber.data.request.BarberServiceRequest;
import com.berber_co.barber.data.response.BarberServiceResponse;

import java.util.List;

public interface BarberServices {
    ApiResponse<Boolean> createBarberService(BarberServiceRequest request);

    ApiResponse<List<BarberServiceResponse>> getBarberServices();
}
