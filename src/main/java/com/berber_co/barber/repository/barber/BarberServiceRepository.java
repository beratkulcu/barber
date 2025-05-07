package com.berber_co.barber.repository.barber;

import com.berber_co.barber.entity.barber.BarberService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BarberServiceRepository extends JpaRepository<BarberService, Long> {
    List<BarberService> findAllBySellerId(Long sellerId);
}
