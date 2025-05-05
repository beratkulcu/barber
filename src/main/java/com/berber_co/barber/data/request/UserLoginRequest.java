package com.berber_co.barber.data.request;

public record UserLoginRequest(
        String email,
        String password
) {
}
