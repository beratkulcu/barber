package com.berber_co.barber.service.user;

import com.berber_co.barber.configuration.constans.ApiResponse;
import com.berber_co.barber.data.request.UserProfileRequest;
import com.berber_co.barber.data.response.UserProfileResponse;

public interface UserProfileService {
    ApiResponse<Boolean> createProfile(UserProfileRequest request);

    ApiResponse<Boolean> updateProfile(Long id, UserProfileRequest request);

    ApiResponse<UserProfileResponse> getProfile();
}
