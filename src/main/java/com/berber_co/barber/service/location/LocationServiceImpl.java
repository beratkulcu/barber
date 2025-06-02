package com.berber_co.barber.service.location;

import com.berber_co.barber.configuration.constans.ApiResponse;
import com.berber_co.barber.configuration.seeder.CityDocument;
import com.berber_co.barber.configuration.seeder.CityRepository;
import com.berber_co.barber.configuration.seeder.DistrictDocument;
import com.berber_co.barber.configuration.seeder.DistrictRepository;
import com.berber_co.barber.data.response.DistrictResponse;
import com.berber_co.barber.exception.AppException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.berber_co.Validations.ERROR;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService{
    private final DistrictRepository districtRepository;
    private final CityRepository cityRepository;
    private final MongoTemplate mongoTemplate;

    @Override
    public String getCityNameByCode(String cityCode) {
        Query query = new Query(Criteria.where("_id").is(cityCode));
        CityDocument city = mongoTemplate.findOne(query, CityDocument.class, "cities");
        if (city == null) {
            throw new AppException(ERROR, cityCode);
        }
        return city.getName();
    }

    @Override
    public String getDistrictNameByCode(String cityCode, String districtCode) {
        try {
            Query query = new Query(Criteria.where("cityId").is(cityCode).and("_id").is(new ObjectId(districtCode)));
            DistrictDocument district = mongoTemplate.findOne(query, DistrictDocument.class, "districts");
            if (district == null) {
                throw new AppException(ERROR, "District not found for city code: " + cityCode + " and district code: " + districtCode);
            }
            return district.getName();
        } catch (IllegalArgumentException e) {
            throw new AppException(ERROR, "Invalid district code format: " + districtCode);
        }
    }



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
