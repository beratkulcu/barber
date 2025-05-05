package com.berber_co.barber.admin.data.request;

public record AdminLoginRequest(
        String email,
        String password
) {
}
