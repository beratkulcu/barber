package com.berber_co.barber.service.impl.barber;

import com.berber_co.barber.configuration.constans.ApiResponse;
import com.berber_co.barber.configuration.seeder.CityDocument;
import com.berber_co.barber.configuration.seeder.CityRepository;
import com.berber_co.barber.configuration.seeder.DistrictDocument;
import com.berber_co.barber.configuration.seeder.DistrictRepository;
import com.berber_co.barber.data.mapstruct.SellerProfileMapper;
import com.berber_co.barber.data.request.SellerProfileRequest;
import com.berber_co.barber.data.response.SellerProfileResponse;
import com.berber_co.barber.entity.barber.Seller;
import com.berber_co.barber.exception.AppException;
import com.berber_co.barber.repository.SellerRepository;
import com.berber_co.barber.service.barber.SellerProfileService;
import com.berber_co.barber.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.berber_co.Validations.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class SellerProfileServiceImpl implements SellerProfileService {
    private final SellerRepository sellerRepository;
    private final CityRepository cityRepository;
    private final DistrictRepository districtRepository;
    private final SellerProfileMapper sellerProfileMapper;


    @Override
    public ApiResponse<Boolean> updateProfile(SellerProfileRequest request) {
        Long sellerId = SecurityUtil.getSellerId();
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new AppException(ERROR, SELLER_NOT_FOUND));

        if (request.city() != null && request.district() != null) {
            CityDocument city = cityRepository.findById(request.city())
                    .orElseThrow(() -> new AppException(ERROR, CITY_NOT_FOUND));

            DistrictDocument district = districtRepository.findById(request.district())
                    .orElseThrow(() -> new AppException(ERROR, DISTRICT_NOT_FOUND));

            if (!district.getCityId().equals(city.getId())) {
                throw new AppException(ERROR, CITY_DISTRICT_NOT_MATCH);
            }

            seller.setCity(city.getName());
            seller.setDistrict(district.getName());
        }

        seller.setFirstName(request.firstName() != null ? request.firstName() : seller.getFirstName());
        seller.setLastName(request.lastName() != null ? request.lastName() : seller.getLastName());
        seller.setAddress(request.address() != null ? request.address() : seller.getAddress());
        seller.setStoreName(request.storeName() != null ? request.storeName() : seller.getStoreName());
        seller.setStoreDescription(request.storeDescription() != null ? request.storeDescription() : seller.getStoreDescription());

        sellerRepository.save(seller);
        return ApiResponse.success(Boolean.TRUE);
    }


    @Override
    public ApiResponse<SellerProfileResponse> getProfile() {
        Long sellerId = SecurityUtil.getSellerId();
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new AppException(ERROR, SELLER_NOT_FOUND));

        SellerProfileResponse dto = sellerProfileMapper.toResponse(seller);
        return ApiResponse.success(dto);
    }
}
