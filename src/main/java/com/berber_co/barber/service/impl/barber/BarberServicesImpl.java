package com.berber_co.barber.service.impl.barber;

import com.berber_co.barber.configuration.constans.ApiResponse;
import com.berber_co.barber.data.mapstruct.BarberServiceMapper;
import com.berber_co.barber.data.request.BarberServiceRequest;
import com.berber_co.barber.data.response.BarberServiceResponse;
import com.berber_co.barber.entity.barber.BarberService;
import com.berber_co.barber.entity.barber.Seller;
import com.berber_co.barber.entity.barber.ServiceCategory;
import com.berber_co.barber.exception.AppException;
import com.berber_co.barber.repository.barber.BarberServiceRepository;
import com.berber_co.barber.repository.barber.SellerRepository;
import com.berber_co.barber.repository.barber.ServiceCategoryRepository;
import com.berber_co.barber.service.barber.BarberServices;
import com.berber_co.barber.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

import static com.berber_co.Validations.ERROR;
import static com.berber_co.Validations.SELLER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class BarberServicesImpl implements BarberServices {
    private final BarberServiceRepository barberServiceRepository;
    private final ServiceCategoryRepository serviceCategoryRepository;
    private final SellerRepository sellerRepository;
    private final BarberServiceMapper barberServiceMapper;

    @Override
    public ApiResponse<Boolean> createBarberService(BarberServiceRequest request) {
        Long sellerId = SecurityUtil.getSellerId();

        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new AppException(ERROR, SELLER_NOT_FOUND));

        ServiceCategory serviceCategory = serviceCategoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new AppException(ERROR, "Service category not found"));

        BarberService barberService = BarberService.builder()
                .title(request.name())
                .price(request.price())
                .durationInMinutes(request.durationInMinutes())
                .category(serviceCategory)
                .seller(seller)
                .build();
        barberServiceRepository.save(barberService);
        return ApiResponse.success(Boolean.TRUE);
    }

    @Override
    public ApiResponse<List<BarberServiceResponse>> getBarberServices() {
        Long sellerId = SecurityUtil.getSellerId();

        List<BarberService> barberServices = barberServiceRepository.findAllBySellerId(sellerId);

        if (barberServices.isEmpty()) {
            return ApiResponse.success(Collections.emptyList());
        }

        List<BarberServiceResponse> responses = barberServices.stream()
                .map(service -> new BarberServiceResponse(
                        service.getTitle(),
                        service.getDurationInMinutes(),
                        service.getPrice(),
                        service.getCategory().getName()
                ))
                .toList();

        return ApiResponse.success(responses);
    }
}
