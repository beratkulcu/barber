package com.berber_co.barber.configuration.seeder;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DistrictRepository extends MongoRepository<DistrictDocument, String> {
}
