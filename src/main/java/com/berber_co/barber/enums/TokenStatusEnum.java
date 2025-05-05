package com.berber_co.barber.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TokenStatusEnum {
    ACTIVE,
    EXPIRED,
    REVOKED;
}
