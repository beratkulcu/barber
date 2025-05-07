package com.berber_co.barber.service.barber;

import com.berber_co.barber.configuration.constans.ApiResponse;
import com.berber_co.barber.data.request.SellerProfileRequest;
import com.berber_co.barber.data.response.SellerProfileResponse;

public interface SellerProfileService {
    ApiResponse<SellerProfileResponse> getProfile();

    ApiResponse<Boolean> updateProfile(SellerProfileRequest request);
}
