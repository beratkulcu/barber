package com.berber_co.barber.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NotificationType {
    CREATE("CREATE"),
    CANCEL("CANCEL"),
    APPROVE("APPROVE"),
    REMINDER("REMINDER"),;

    private final String value;
}
