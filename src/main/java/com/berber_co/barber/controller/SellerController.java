package com.berber_co.barber.controller;

import com.berber_co.barber.configuration.constans.ApiResponse;
import com.berber_co.barber.data.request.SellerLoginRequest;
import com.berber_co.barber.data.request.SellerRegisterRequest;
import com.berber_co.barber.data.response.AuthenticationResponse;
import com.berber_co.barber.service.barber.SellerAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.berber_co.barber.configuration.constans.ApiPathConstants.BARBER_API;

@RestController
@RequestMapping(BARBER_API + "/auth")
@RequiredArgsConstructor
public class SellerController {
    private final SellerAuthService sellerAuthService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthenticationResponse>> register(@RequestBody SellerRegisterRequest request) {
        return ResponseEntity.ok(sellerAuthService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthenticationResponse>> login(@RequestBody SellerLoginRequest request) {
        return ResponseEntity.ok(sellerAuthService.login(request));
    }

}
