package com.berber_co.barber.controller.user;

import com.berber_co.barber.configuration.constans.ApiResponse;
import com.berber_co.barber.data.request.UserLoginRequest;
import com.berber_co.barber.data.request.UserRegisterRequest;
import com.berber_co.barber.data.response.AuthenticationResponse;
import com.berber_co.barber.service.user.UserAuthService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.berber_co.barber.configuration.constans.ApiPathConstants.USER_API;

@Validated
@RestController
@RequestMapping(USER_API + "/auth")
@RequiredArgsConstructor
@SecurityRequirement(name = "BearerAuth")
public class UserAuthController {
    private final UserAuthService userAuthService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthenticationResponse>> register(@RequestBody UserRegisterRequest request) {
        return ResponseEntity.ok(userAuthService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthenticationResponse>> login(@RequestBody UserLoginRequest request) {
        return ResponseEntity.ok(userAuthService.login(request));
    }
}
