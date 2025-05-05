package com.berber_co.barber.data.request;

public record SellerProfileRequest(
        String firstName,
        String lastName,
        String phoneNumber,
        String address,
        String city,
        String district,
        String storeName,
        String storeDescription
) {
}
