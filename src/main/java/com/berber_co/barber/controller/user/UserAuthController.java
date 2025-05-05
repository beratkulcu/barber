package com.berber_co.barber.controller.user;

import com.berber_co.barber.configuration.constans.ApiResponse;
import com.berber_co.barber.data.request.UserLoginRequest;
import com.berber_co.barber.data.request.UserRegisterRequest;
import com.berber_co.barber.data.response.AuthenticationResponse;
import com.berber_co.barber.service.user.UserAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.berber_co.barber.configuration.constans.ApiPathConstants.USER_API;

@RestController
@RequestMapping(USER_API + "/auth")
@RequiredArgsConstructor
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
