package com.berber_co.barber.repository.barber;

import com.berber_co.barber.entity.barber.Seller;
import com.berber_co.barber.entity.barber.SellerAvailability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;

@Repository
public interface SellerAvailabilityRepository extends JpaRepository<SellerAvailability, Long> {
    List<SellerAvailability> findBySellerId(Long sellerId);

    Optional<SellerAvailability> findBySellerAndDayOfWeek(Seller seller, DayOfWeek dayOfWeek);
}
