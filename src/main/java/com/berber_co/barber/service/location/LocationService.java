package com.berber_co.barber.service.location;

import com.berber_co.barber.configuration.constans.ApiResponse;
import com.berber_co.barber.data.response.DistrictResponse;
import org.springframework.data.domain.Page;

public interface LocationService {
    ApiResponse<Page<DistrictResponse>> getDistricts(int page, int size);
}
