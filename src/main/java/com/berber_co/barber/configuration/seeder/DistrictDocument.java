package com.berber_co.barber.configuration.seeder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DistrictDocument {
    private String id;
    private String name;
    private String cityId;
}
