package com.berber_co.barber.controller.barber;

import com.berber_co.barber.configuration.constans.ApiResponse;
import com.berber_co.barber.data.request.ServiceCategoryRequest;
import com.berber_co.barber.data.response.ServiceCategoryResponse;
import com.berber_co.barber.entity.barber.BarberService;
import com.berber_co.barber.service.barber.BarberCategoriesService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.berber_co.barber.configuration.constans.ApiPathConstants.BARBER_API;

@RestController
@RequestMapping(BARBER_API + "/categories")
@RequiredArgsConstructor
@SecurityRequirement(name = "BearerAuth")
public class ServiceCategoryController {
    private final BarberCategoriesService barberCategoriesService;

    @PostMapping
    public ResponseEntity<ApiResponse<Boolean>> createCategory(@RequestBody ServiceCategoryRequest request) {
        return ResponseEntity.ok(barberCategoriesService.createCategory(request));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ServiceCategoryResponse>>> getAllCategories() {
        return ResponseEntity.ok(barberCategoriesService.getAllCategories());
    }
}
