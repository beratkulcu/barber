package com.berber_co.barber.service.impl.barber;

import com.berber_co.barber.configuration.constans.ApiResponse;
import com.berber_co.barber.configuration.seeder.CityRepository;
import com.berber_co.barber.configuration.seeder.DistrictRepository;
import com.berber_co.barber.data.request.SellerLoginRequest;
import com.berber_co.barber.data.request.SellerRegisterRequest;
import com.berber_co.barber.data.response.AuthenticationResponse;
import com.berber_co.barber.entity.Role;
import com.berber_co.barber.entity.Token;
import com.berber_co.barber.entity.barber.Seller;
import com.berber_co.barber.enums.ActivityStatus;
import com.berber_co.barber.enums.RoleType;
import com.berber_co.barber.enums.TokenStatusEnum;
import com.berber_co.barber.exception.AppException;
import com.berber_co.barber.repository.RoleRepository;
import com.berber_co.barber.repository.barber.SellerRepository;
import com.berber_co.barber.repository.TokenRepository;
import com.berber_co.barber.security.JwtService;
import com.berber_co.barber.service.barber.SellerAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Set;

import static com.berber_co.Validations.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class SellerAuthServiceImpl implements SellerAuthService {
    private final RoleRepository roleRepository;
    private final SellerRepository sellerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final TokenRepository tokenRepository;
    private final CityRepository cityRepository;
    private final DistrictRepository districtRepository;


    @Override
    public ApiResponse<AuthenticationResponse> register(SellerRegisterRequest request) {
        if (sellerRepository.existsByEmail(request.email())) {
            throw new AppException(ERROR, EMAIL_ALREADY_EXISTS);
        }

        Seller seller = new Seller();
        seller.setEmail(request.email());
        seller.setPassword(passwordEncoder.encode(request.password()));
        seller.setStatus(ActivityStatus.ACTIVE);

        Role barberRole = roleRepository.findByName(RoleType.BARBER)
                .orElseThrow(() -> new AppException(ERROR, ROLE_NOT_FOUND));

        seller.setRoles(Set.of(barberRole));
        sellerRepository.save(seller);
        return generateAuthResponse(seller);
    }

    @Override
    public ApiResponse<AuthenticationResponse> login(SellerLoginRequest request) {
        Seller seller = sellerRepository.findByEmail(request.email())
                .orElseThrow(() -> new AppException(ERROR, SELLER_NOT_FOUND));

        if (!passwordEncoder.matches(request.password(), seller.getPassword())) {
            throw new AppException(ERROR, INVALID_CREDENTIALS);
        }

        return generateAuthResponse(seller);
    }

    private ApiResponse<AuthenticationResponse> generateAuthResponse(Seller seller) {
        String accessToken = jwtService.generateAccessToken(
                seller.getEmail(),
                Set.of(RoleType.BARBER.name())
        );

        String refreshToken = jwtService.generateRefreshToken(
                seller.getEmail(),
                Set.of(RoleType.BARBER.name())
        );

        Token token = Token.builder()
                .refreshToken(refreshToken)
                .accessToken(accessToken)
                .seller(seller)
                .status(TokenStatusEnum.ACTIVE)
                .expiresAt(ZonedDateTime.now())
                .build();
        tokenRepository.save(token);

        return ApiResponse.success(new AuthenticationResponse(accessToken, refreshToken));
    }

}
