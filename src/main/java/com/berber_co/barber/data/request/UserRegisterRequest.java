package com.berber_co.barber.data.request;

import jakarta.validation.constraints.Email;

public record UserRegisterRequest(
        @Email
        String email,
        String password,
        String username
) {
}
