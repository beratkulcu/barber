package com.berber_co;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Validations {
    public static final String ERROR = "Error";
    public static final String USER_NOT_FOUND = "User not found";
    public static final String INVALID_CREDENTIALS = "Invalid credentials";
    public static final String EMAIL_ALREADY_EXISTS = "Email already exists";
    public static final String USERNAME_ALREADY_EXISTS = "Username already exists";
    public static final String ROLE_NOT_FOUND = "Role not found";
    public static final String SELLER_NOT_FOUND = "Seller not found";
    public static final String CITY_NOT_FOUND = "City not found";
    public static final String DISTRICT_NOT_FOUND = "District not found";
    public static final String CITY_DISTRICT_NOT_MATCH = "City and district do not match";
}
