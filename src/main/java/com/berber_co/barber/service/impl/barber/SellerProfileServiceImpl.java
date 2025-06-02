package com.berber_co.barber.service.impl.barber;

import com.berber_co.barber.configuration.constans.ApiResponse;
import com.berber_co.barber.configuration.seeder.CityRepository;
import com.berber_co.barber.configuration.seeder.DistrictRepository;
import com.berber_co.barber.data.mapstruct.SellerProfileMapper;
import com.berber_co.barber.data.request.SellerProfileRequest;
import com.berber_co.barber.data.response.SellerProfileResponse;
import com.berber_co.barber.entity.barber.Seller;
import com.berber_co.barber.exception.AppException;
import com.berber_co.barber.repository.barber.SellerRepository;
import com.berber_co.barber.service.barber.FileStorageService;
import com.berber_co.barber.service.barber.SellerProfileService;
import com.berber_co.barber.service.location.LocationService;
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
    private final FileStorageService fileService;
    private final LocationService locationService;


    @Override
    public ApiResponse<Boolean> updateProfile(SellerProfileRequest request) {
        Long sellerId = SecurityUtil.getSellerId();
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new AppException(ERROR, SELLER_NOT_FOUND));

        if (request.city() != null && request.district() != null) {
            String cityName = locationService.getCityNameByCode(request.city());
            String districtName = locationService.getDistrictNameByCode(request.district());

            if (cityName == null || districtName == null) {
                throw new AppException(ERROR, "Invalid city or district");
            }

            seller.setCity(request.city());
            seller.setDistrict(request.district());
        }

        seller.setLatitude(request.latitude() != null ? request.latitude() : seller.getLatitude());
        seller.setLongitude(request.longitude() != null ? request.longitude() : seller.getLongitude());
        seller.setStorePhotoUrl(request.profilePhotoUrl() != null ? request.profilePhotoUrl() : seller.getStorePhotoUrl());
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
