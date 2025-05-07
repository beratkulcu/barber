package com.berber_co.barber.repository.barber;

import com.berber_co.barber.entity.barber.ServiceCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceCategoryRepository extends JpaRepository<ServiceCategory, Long> {
}