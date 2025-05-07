package com.berber_co.barber.controller.barber;

import com.berber_co.barber.configuration.constans.ApiResponse;
import com.berber_co.barber.data.request.SellerProfileRequest;
import com.berber_co.barber.data.response.SellerProfileResponse;
import com.berber_co.barber.service.barber.SellerProfileService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.berber_co.barber.configuration.constans.ApiPathConstants.BARBER_API;

@RestController
@RequestMapping(BARBER_API + "/profile")
@SecurityRequirement(name = "BearerAuth")
@RequiredArgsConstructor
public class SellerProfileController {
    private final SellerProfileService sellerProfileService;

    @PutMapping
    public ResponseEntity<ApiResponse<Boolean>> updateProfile(@RequestBody SellerProfileRequest request) {
        return ResponseEntity.ok(sellerProfileService.updateProfile(request));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<SellerProfileResponse>> getProfile() {
        return ResponseEntity.ok(sellerProfileService.getProfile());
    }
}
