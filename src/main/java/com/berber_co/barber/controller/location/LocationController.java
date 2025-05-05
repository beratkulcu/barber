package com.berber_co.barber.controller.location;

import com.berber_co.barber.configuration.constans.ApiResponse;
import com.berber_co.barber.configuration.seeder.CityRepository;
import com.berber_co.barber.configuration.seeder.DistrictRepository;
import com.berber_co.barber.data.response.CitiesResponse;
import com.berber_co.barber.data.response.DistrictResponse;
import com.berber_co.barber.service.location.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.berber_co.barber.configuration.constans.ApiPathConstants.LOCATION_API;

@RestController
@RequiredArgsConstructor
@RequestMapping(LOCATION_API)
public class LocationController {
    private final CityRepository cityRepository;
    private final DistrictRepository districtRepository;
    private final LocationService locationService;

    @GetMapping("/cities")
    public ResponseEntity<ApiResponse<List<CitiesResponse>>> getCities() {
        List<CitiesResponse> cities = cityRepository.findAll()
                .stream()
                .map(city -> new CitiesResponse(city.getId(), city.getName()))
                .toList();
        return ResponseEntity.ok(ApiResponse.success(cities));
    }

    @GetMapping("/districts")
    public ResponseEntity<ApiResponse<Page<DistrictResponse>>> getDistricts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(locationService.getDistricts(page, size));
    }


}
