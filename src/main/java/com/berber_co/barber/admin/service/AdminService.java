package com.berber_co.barber.admin.service;

import com.berber_co.barber.admin.data.request.AdminLoginRequest;
import com.berber_co.barber.admin.data.request.AdminRegisterRequest;
import com.berber_co.barber.configuration.constans.ApiResponse;
import com.berber_co.barber.data.response.AuthenticationResponse;

public interface AdminService {
    ApiResponse<AuthenticationResponse> register(AdminRegisterRequest request);
    ApiResponse<AuthenticationResponse> login(AdminLoginRequest request);

}
