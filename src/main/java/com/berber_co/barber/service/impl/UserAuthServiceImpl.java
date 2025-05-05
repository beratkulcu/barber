package com.berber_co.barber.service.impl;

import com.berber_co.barber.configuration.constans.ApiResponse;
import com.berber_co.barber.data.request.UserRegisterRequest;
import com.berber_co.barber.data.response.AuthenticationResponse;
import com.berber_co.barber.entity.Role;
import com.berber_co.barber.entity.Token;
import com.berber_co.barber.entity.User;
import com.berber_co.barber.enums.RoleType;
import com.berber_co.barber.enums.TokenStatusEnum;
import com.berber_co.barber.exception.AppException;
import com.berber_co.barber.repository.RoleRepository;
import com.berber_co.barber.repository.TokenRepository;
import com.berber_co.barber.repository.user.UserRepository;
import com.berber_co.barber.security.JwtService;
import com.berber_co.barber.service.user.UserAuthService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserAuthServiceImpl implements UserAuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final JwtService jwtService;
    private final TokenRepository tokenRepository;


    @Override
    @Transactional
    public ApiResponse<AuthenticationResponse> register(UserRegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new AppException("Email already exists");
        }

        if (userRepository.existsByUsername(request.username())) {
            throw new AppException("Username already exists");
        }

        Role role = roleRepository.findByName(RoleType.USER)
                .orElseThrow(() -> new AppException("Role not found"));


        User user = User.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .username(request.username())
                .roles(Set.of(role))
                .enabled(true)
                .build();

        userRepository.save(user);
        Set<String> roleNames = user.getRoles().stream()
                .map(roles -> role.getName().name())
                .collect(Collectors.toSet());

        String accessToken = jwtService.generateAccessToken(user.getEmail(), roleNames);
        String refreshToken = jwtService.generateRefreshToken(user.getEmail());

        Token token = Token.builder()
                .user(user)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .status(TokenStatusEnum.ACTIVE)
                .expiresAt(ZonedDateTime.now().plusHours(24))
                .build();
        tokenRepository.save(token);

        return ApiResponse.success(
                new AuthenticationResponse(accessToken, refreshToken)
        );
    }
}
