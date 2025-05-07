package com.berber_co.barber.security;

import com.berber_co.barber.entity.barber.Seller;
import com.berber_co.barber.repository.barber.SellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomSellerDetailService implements UserDetailsService {
    private final SellerRepository sellerRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Seller seller = sellerRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Seller not found"));
        return new CustomSellerDetails(seller);
    }

}
