package com.berber_co.barber.service.user;

import com.berber_co.barber.configuration.constans.ApiResponse;
import com.berber_co.barber.data.request.UserLoginRequest;
import com.berber_co.barber.data.request.UserRegisterRequest;
import com.berber_co.barber.data.response.AuthenticationResponse;

public interface UserAuthService {
    ApiResponse<AuthenticationResponse> register(UserRegisterRequest request);

    ApiResponse<AuthenticationResponse> login(UserLoginRequest request);
}
