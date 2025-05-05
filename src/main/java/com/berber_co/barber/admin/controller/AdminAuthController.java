package com.berber_co.barber.admin.controller;

import com.berber_co.barber.admin.data.request.AdminLoginRequest;
import com.berber_co.barber.admin.data.request.AdminRegisterRequest;
import com.berber_co.barber.admin.service.AdminService;
import com.berber_co.barber.configuration.constans.ApiResponse;
import com.berber_co.barber.data.response.AuthenticationResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.berber_co.barber.configuration.constans.ApiPathConstants.ADMIN_API;

@RestController
@RequestMapping(ADMIN_API + "/auth")
@RequiredArgsConstructor
@SecurityRequirement(name = "BearerAuth")
public class AdminAuthController {
    private final AdminService adminAuthService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthenticationResponse>> register(
            @RequestBody @Valid AdminRegisterRequest request) {
        return ResponseEntity.ok(adminAuthService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthenticationResponse>> login(
            @RequestBody @Valid AdminLoginRequest request) {
        return ResponseEntity.ok(adminAuthService.login(request));
    }

}
