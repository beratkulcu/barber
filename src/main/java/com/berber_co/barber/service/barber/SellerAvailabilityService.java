package com.berber_co.barber.service.barber;

import com.berber_co.barber.configuration.constans.ApiResponse;
import com.berber_co.barber.data.request.SellerAvailabilityRequest;
import com.berber_co.barber.data.response.SellerAvailabilityResponse;

import java.util.List;

public interface SellerAvailabilityService {
    ApiResponse<Boolean> addAvailability(SellerAvailabilityRequest request);

    ApiResponse<Boolean> updateAvailability(Long id, SellerAvailabilityRequest request);

    ApiResponse<List<SellerAvailabilityResponse>> getAvailabilityForSeller();
}
