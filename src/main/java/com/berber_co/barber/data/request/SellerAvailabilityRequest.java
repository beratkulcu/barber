package com.berber_co.barber.data.request;

import java.time.DayOfWeek;

public record SellerAvailabilityRequest(
        DayOfWeek dayOfWeek,
        Integer startHour,
        Integer startMinute,
        Integer endHour,
        Integer endMinute,
        Boolean isOpen
) {
}
