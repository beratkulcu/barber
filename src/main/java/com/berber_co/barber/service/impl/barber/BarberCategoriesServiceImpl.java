package com.berber_co.barber.service.impl.barber;

import com.berber_co.barber.configuration.constans.ApiResponse;
import com.berber_co.barber.data.request.ServiceCategoryRequest;
import com.berber_co.barber.data.response.ServiceCategoryResponse;
import com.berber_co.barber.entity.barber.ServiceCategory;
import com.berber_co.barber.repository.barber.ServiceCategoryRepository;
import com.berber_co.barber.service.barber.BarberCategoriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BarberCategoriesServiceImpl implements BarberCategoriesService {
    private final ServiceCategoryRepository serviceCategoryRepository;


    @Override
    public ApiResponse<Boolean> createCategory(ServiceCategoryRequest request) {
        ServiceCategory serviceCategory = ServiceCategory.builder()
                .name(request.name())
                .description(request.description())
                .build();
        serviceCategoryRepository.save(serviceCategory);
        return ApiResponse.success(Boolean.TRUE);
    }

    @Override
    public ApiResponse<List<ServiceCategoryResponse>> getAllCategories() {
        List<ServiceCategory> serviceCategories = serviceCategoryRepository.findAll();
        List<ServiceCategoryResponse> serviceCategoryResponses = serviceCategories.stream()
                .map(serviceCategory -> new ServiceCategoryResponse(
                        serviceCategory.getName(),
                        serviceCategory.getDescription()
                ))
                .toList();
        return ApiResponse.success(serviceCategoryResponses);
    }
}