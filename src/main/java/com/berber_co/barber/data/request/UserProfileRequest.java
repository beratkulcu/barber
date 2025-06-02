package com.berber_co.barber.data.request;

public record UserProfileRequest(
        String firstName,
        String lastName,
        String phoneNumber,
        String address,
        String profilePictureUrl,
        String city,
        String district,
        Double latitude,
        Double longitude
) {
}
