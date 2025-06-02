package com.berber_co.barber.controller.user;

import com.berber_co.barber.configuration.constans.ApiResponse;
import com.berber_co.barber.data.request.AppointmentRequest;
import com.berber_co.barber.data.response.AppointmentResponse;
import com.berber_co.barber.service.user.AppointmentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.berber_co.barber.configuration.constans.ApiPathConstants.USER_API;

@RestController
@RequestMapping(USER_API + "/appointment")
@SecurityRequirement(name = "BearerAuth")
@RequiredArgsConstructor
public class AppointmentController {
    private final AppointmentService appointmentService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Boolean>> createAppointment(@Valid @RequestBody AppointmentRequest request) {
        return ResponseEntity.ok(appointmentService.createAppointment(request));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<List<AppointmentResponse>>> getMyAppointments() {
        return ResponseEntity.ok(ApiResponse.success(appointmentService.getAppointmentsByUser()));
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<ApiResponse<Boolean>> cancelAppointment(@PathVariable Long id) {
        return ResponseEntity.ok(appointmentService.cancelAppointment(id));
    }
}
