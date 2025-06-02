package com.berber_co.barber.controller.barber;

import com.berber_co.barber.configuration.constans.ApiResponse;
import com.berber_co.barber.data.request.SellerAvailabilityRequest;
import com.berber_co.barber.data.response.SellerAvailabilityResponse;
import com.berber_co.barber.service.barber.SellerAvailabilityService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.berber_co.barber.configuration.constans.ApiPathConstants.BARBER_API;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "BearerAuth")
@RequestMapping(BARBER_API + "/availability")
public class SellerAvailabilityController {
    private final SellerAvailabilityService sellerAvailabilityService;

    @PostMapping
    public ResponseEntity<ApiResponse<Boolean>> addSellerAvailability(@RequestBody SellerAvailabilityRequest request) {
        return ResponseEntity.ok(sellerAvailabilityService.addAvailability(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Boolean>> updateAvailability(@PathVariable Long id,
                                                                   @RequestBody SellerAvailabilityRequest request) {
        return ResponseEntity.ok(sellerAvailabilityService.updateAvailability(id, request));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<SellerAvailabilityResponse>>> getAvailability() {
        return ResponseEntity.ok(sellerAvailabilityService.getAvailabilityForSeller());
    }
}
