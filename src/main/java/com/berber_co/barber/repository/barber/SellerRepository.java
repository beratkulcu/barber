package com.berber_co.barber.repository.barber;

import com.berber_co.barber.entity.barber.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {
    Optional<Seller> findByEmail(String email);

    boolean existsByEmail(String email);
}
