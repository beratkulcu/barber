package com.berber_co.barber.data.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class SellerProfileResponse {
    private Long id;
    private String fullName;
    private String city;
    private String district;
    private String address;
    private String phone;
    private String email;
    private String storeName;
    private String storeDescription;
    private LocalDateTime membershipDate;
}
