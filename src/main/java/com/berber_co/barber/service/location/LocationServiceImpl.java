package com.berber_co.barber.service.location;

import com.berber_co.barber.configuration.constans.ApiResponse;
import com.berber_co.barber.configuration.seeder.CityDocument;
import com.berber_co.barber.configuration.seeder.CityRepository;
import com.berber_co.barber.configuration.seeder.DistrictDocument;
import com.berber_co.barber.configuration.seeder.DistrictRepository;
import com.berber_co.barber.data.response.DistrictResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService{
    private final DistrictRepository districtRepository;
    private final CityRepository cityRepository;

    @Override
    public ApiResponse<Page<DistrictResponse>> getDistricts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("cityId").ascending());
        Page<DistrictDocument> districtPage = districtRepository.findAll(pageable);

        List<DistrictResponse> responses = districtPage.getContent().stream()
                .map(district -> {
                    String cityName = cityRepository.findById(district.getCityId().toString())
                            .map(CityDocument::getName)
                            .orElse("Unknown");
                    return new DistrictResponse(district.getName(), district.getCityId(), cityName);
                })
                .toList();

        Page<DistrictResponse> responsePage = new PageImpl<>(responses, pageable, districtPage.getTotalElements());

        return ApiResponse.success(responsePage);
    }


}
