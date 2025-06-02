package com.berber_co.barber.service.impl.barber;

import com.berber_co.barber.configuration.constans.ApiResponse;
import com.berber_co.barber.data.request.SellerAvailabilityRequest;
import com.berber_co.barber.data.response.SellerAvailabilityResponse;
import com.berber_co.barber.entity.barber.Seller;
import com.berber_co.barber.entity.barber.SellerAvailability;
import com.berber_co.barber.exception.AppException;
import com.berber_co.barber.repository.barber.SellerAvailabilityRepository;
import com.berber_co.barber.repository.barber.SellerRepository;
import com.berber_co.barber.service.barber.SellerAvailabilityService;
import com.berber_co.barber.util.DateUtil;
import com.berber_co.barber.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.berber_co.Validations.ERROR;
import static com.berber_co.Validations.SELLER_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class SellerAvailabilityServiceImpl implements SellerAvailabilityService {
    private final SellerAvailabilityRepository sellerAvailabilityRepository;
    private final SellerRepository sellerRepository;

    @Override
    public ApiResponse<Boolean> addAvailability(SellerAvailabilityRequest request) {
        Long sellerId = SecurityUtil.getSellerId();
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new AppException(ERROR, SELLER_NOT_FOUND));

        SellerAvailability sellerAvailability = SellerAvailability.builder()
                .seller(seller)
                .dayOfWeek(request.dayOfWeek())
                .startTime(DateUtil.createLocalTime(request.startHour(), request.startMinute()))
                .endTime(DateUtil.createLocalTime(request.endHour(), request.endMinute()))
                .isOpen(request.isOpen())
                .build();
        sellerAvailabilityRepository.save(sellerAvailability);
        return ApiResponse.success(Boolean.TRUE);
    }

    @Override
    public ApiResponse<Boolean> updateAvailability(Long id, SellerAvailabilityRequest request) {
        SellerAvailability availability = findAvailabilityById(id);

        if (request.dayOfWeek() != null) {
            availability.setDayOfWeek(request.dayOfWeek());
        }
        if (request.startHour() != null && request.startMinute() != null) {
            availability.setStartTime(DateUtil.createLocalTime(request.startHour(), request.startMinute()));
        }
        if (request.endHour() != null && request.endMinute() != null) {
            availability.setEndTime(DateUtil.createLocalTime(request.endHour(), request.endMinute()));
        }
        if (request.isOpen() != null) {
            availability.setIsOpen(request.isOpen());
        }

        sellerAvailabilityRepository.save(availability);
        log.info("Availability updated: {}", availability);
        return ApiResponse.success(Boolean.TRUE);
    }

    @Override
    public ApiResponse<List<SellerAvailabilityResponse>> getAvailabilityForSeller() {
        Long sellerId = SecurityUtil.getSellerId();
        List<SellerAvailability> availabilities = sellerAvailabilityRepository.findBySellerId(sellerId);

        if (availabilities.isEmpty()) {
            return ApiResponse.success(List.of());
        }

        List<SellerAvailabilityResponse> responses = availabilities.stream()
                .map(avail -> new SellerAvailabilityResponse(
                        avail.getDayOfWeek(),
                        avail.getStartTime(),
                        avail.getEndTime(),
                        avail.getIsOpen()
                ))
                .toList();
        return ApiResponse.success(responses);
    }


    private Seller findSellerById(Long sellerId) {
        return sellerRepository.findById(sellerId)
                .orElseThrow(() -> new AppException(ERROR, SELLER_NOT_FOUND));
    }

    private SellerAvailability findAvailabilityById(Long id) {
        return sellerAvailabilityRepository.findById(id)
                .orElseThrow(() -> new AppException(ERROR, "Availability not found"));
    }
}
