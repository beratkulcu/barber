package com.berber_co.barber.configuration.seeder;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CityRepository extends MongoRepository<CityDocument, String> {
    Optional<CityDocument> findByNameIgnoreCase(String name);
}
