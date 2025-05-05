package com.berber_co.barber.configuration.seeder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "cities")
public class CityDocument {
    @Id
    private String id;
    private String name;
    private List<DistrictDocument> district;
}
