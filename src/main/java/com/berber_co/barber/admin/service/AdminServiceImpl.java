package com.berber_co.barber.admin.service;

import com.berber_co.barber.admin.data.request.AdminLoginRequest;
import com.berber_co.barber.admin.data.request.AdminRegisterRequest;
import com.berber_co.barber.admin.entity.Admin;
import com.berber_co.barber.admin.repository.AdminRepository;
import com.berber_co.barber.configuration.constans.ApiResponse;
import com.berber_co.barber.data.response.AuthenticationResponse;
import com.berber_co.barber.entity.Token;
import com.berber_co.barber.enums.ActivityStatus;
import com.berber_co.barber.enums.RoleType;
import com.berber_co.barber.enums.TokenStatusEnum;
import com.berber_co.barber.exception.AppException;
import com.berber_co.barber.repository.TokenRepository;
import com.berber_co.barber.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Set;
import java.util.stream.Collectors;

import static com.berber_co.Validations.*;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final TokenRepository tokenRepo;

    @Override
    public ApiResponse<AuthenticationResponse> register(AdminRegisterRequest req) {
        if (adminRepo.existsByEmail(req.email())) {
            throw new AppException(ERROR, EMAIL_ALREADY_EXISTS);
        }

        Admin admin = adminRepo.save(
                Admin.builder()
                        .email(req.email())
                        .password(passwordEncoder.encode(req.password()))
                        .status(ActivityStatus.ACTIVE)
                        .roles(Set.of(RoleType.ADMIN))
                        .build());

        return buildTokens(admin);
    }

    @Override
    public ApiResponse<AuthenticationResponse> login(AdminLoginRequest req) {
        Admin admin = adminRepo.findByEmail(req.email())
                .orElseThrow(() -> new AppException(ERROR, USER_NOT_FOUND));

        if (!passwordEncoder.matches(req.password(), admin.getPassword())) {
            throw new AppException(ERROR, INVALID_CREDENTIALS);
        }

        adminRepo.save(admin);

        return buildTokens(admin);
    }

    private ApiResponse<AuthenticationResponse> buildTokens(Admin admin) {
        Set<String> roles = admin.getRoles().stream()
                .map(RoleType::name)
                .collect(Collectors.toSet());

        String access  = jwtService.generateAccessToken(admin.getEmail(), roles);
        String refresh = jwtService.generateRefreshToken(admin.getEmail(), roles);

        tokenRepo.save(Token.builder()
                .admin(admin)
                .accessToken(access)
                .refreshToken(refresh)
                .status(TokenStatusEnum.ACTIVE)
                .expiresAt(ZonedDateTime.now().plusDays(7))
                .build());

        return ApiResponse.success(new AuthenticationResponse(access, refresh));
    }
}
