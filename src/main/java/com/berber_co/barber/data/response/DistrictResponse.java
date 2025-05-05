package com.berber_co.barber.data.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DistrictResponse {
    private String districtName;
    private String cityId;
    private String cityName;
}
