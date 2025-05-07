package com.berber_co.barber.service.barber;

import com.berber_co.barber.configuration.constans.ApiResponse;
import com.berber_co.barber.data.request.ServiceCategoryRequest;
import com.berber_co.barber.data.response.ServiceCategoryResponse;

import java.util.List;

public interface BarberCategoriesService {
    ApiResponse<Boolean> createCategory(ServiceCategoryRequest request);

    ApiResponse<List<ServiceCategoryResponse>> getAllCategories();
}