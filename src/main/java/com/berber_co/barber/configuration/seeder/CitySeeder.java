package com.berber_co.barber.configuration.seeder;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
//@Component
@RequiredArgsConstructor
public class CitySeeder{ //implements CommandLineRunner {
//    private final DistrictRepository districtRepository;
//    private final CityRepository cityRepository;
//    private final ObjectMapper objectMapper;
//    private final RestTemplate restTemplate = new RestTemplate();
//
//    private static final String CITY_API = "https://turkiyeapi.dev/api/v1/provinces";
//    private static final String DISTRICT_API_TEMPLATE = "https://turkiyeapi.dev/api/v1/districts?provinceId=%d";
//
//    @Override
//    public void run(String... args) throws Exception {
//        if (cityRepository.count() == 0 && districtRepository.count() == 0) {
//            log.info("Seeding cities and districts from TürkiyeAPI...");
//
//            String cityJson = restTemplate.getForObject(CITY_API, String.class);
//            List<CityDocument> cities = objectMapper.readValue(
//                    objectMapper.readTree(cityJson).get("data").toString(),
//                    new TypeReference<>() {}
//            );
//
//            for (CityDocument city : cities) {
//                cityRepository.save(city);
//
//                String districtUrl = String.format(DISTRICT_API_TEMPLATE, Integer.parseInt(city.getId()));
//                String districtJson = restTemplate.getForObject(districtUrl, String.class);
//
//                List<DistrictDocument> districts = objectMapper.readValue(
//                        objectMapper.readTree(districtJson).get("data").toString(),
//                        new TypeReference<>() {}
//                );
//
//                for (DistrictDocument district : districts) {
//                    district.setCityId(city.getId());
//                    districtRepository.save(district);
//                }
//            }
//
//            log.info("Seeding completed from TürkiyeAPI.");
//        } else {
//            log.info("City and district data already present. Skipping seeding.");
//        }
//    }
}
