package com.berber_co.barber.data.response;

public record UserProfileResponse(
        Long id,
        String firstName,
        String lastName,
        String phoneNumber,
        String city,
        String street,
        Double latitude,
        Double longitude
) {
}
