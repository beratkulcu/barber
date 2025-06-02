package com.berber_co.barber.repository.barber;

import com.berber_co.barber.entity.barber.BarberService;
import com.berber_co.barber.enums.ActivityStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BarberServiceRepository extends JpaRepository<BarberService, Long> {
    List<BarberService> findAllBySellerIdAndStatus(Long sellerId, ActivityStatus activityStatus);

    Optional<BarberService> findByIdAndSellerId(Long serviceId, Long sellerId);
}
