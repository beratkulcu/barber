package com.berber_co.barber.controller.barber;

import com.berber_co.barber.configuration.constans.ApiResponse;
import com.berber_co.barber.service.barber.SellerAppointmentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.berber_co.barber.configuration.constans.ApiPathConstants.BARBER_API;

@RestController
@RequestMapping(BARBER_API + "/appointment")
@SecurityRequirement(name = "BearerAuth")
@RequiredArgsConstructor
public class BarberAppointmentController {
    private final SellerAppointmentService sellerAppointmentService;

    @PostMapping("/confirm/{appointmentId}")
    public ResponseEntity<ApiResponse<Boolean>> confirmAppointment(@PathVariable Long appointmentId) {
        return ResponseEntity.ok(sellerAppointmentService.confirmAppointment(appointmentId));
    }

    @PostMapping("/cancel/{appointmentId}")
    public ResponseEntity<ApiResponse<Boolean>> cancelAppointment(@PathVariable Long appointmentId, @RequestParam String reason) {
        return ResponseEntity.ok(sellerAppointmentService.cancelAppointment(appointmentId, reason));
    }
}
