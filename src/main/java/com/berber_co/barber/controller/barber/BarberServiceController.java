package com.berber_co.barber.controller.barber;

import com.berber_co.barber.configuration.constans.ApiResponse;
import com.berber_co.barber.data.request.BarberServiceRequest;
import com.berber_co.barber.data.response.BarberServiceResponse;
import com.berber_co.barber.service.barber.BarberServices;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.berber_co.barber.configuration.constans.ApiPathConstants.BARBER_API;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "BearerAuth")
@RequestMapping(BARBER_API + "/services")
public class BarberServiceController {
    private final BarberServices barberServices;

    @PostMapping
    public ResponseEntity<ApiResponse<Boolean>> createService(@RequestBody BarberServiceRequest request) {
        return ResponseEntity.ok(barberServices.createBarberService(request));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<BarberServiceResponse>>> getAllServicesForBarber() {
        return ResponseEntity.ok(barberServices.getBarberServices());
    }

    @PutMapping("/{serviceId}")
    public ResponseEntity<ApiResponse<Boolean>> updateService(@PathVariable Long serviceId, @RequestBody BarberServiceRequest request) {
        return ResponseEntity.ok(barberServices.updateBarberService(serviceId, request));
    }

    @PutMapping("/{serviceId}/deactivate")
    public ResponseEntity<ApiResponse<Boolean>> deactivateService(@PathVariable Long serviceId) {
        return ResponseEntity.ok(barberServices.deactivateBarberService(serviceId));
    }
}
