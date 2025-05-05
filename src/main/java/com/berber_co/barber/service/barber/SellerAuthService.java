package com.berber_co.barber.service.barber;

import com.berber_co.barber.configuration.constans.ApiResponse;
import com.berber_co.barber.data.request.SellerLoginRequest;
import com.berber_co.barber.data.request.SellerRegisterRequest;
import com.berber_co.barber.data.response.AuthenticationResponse;

public interface SellerAuthService {
    ApiResponse<AuthenticationResponse> register(SellerRegisterRequest request);
    ApiResponse<AuthenticationResponse> login(SellerLoginRequest request);
}
