package com.berber_co.barber.data.request;

import java.math.BigDecimal;

public record BarberServiceRequest(
        Long categoryId,
        String name,
        Integer durationInMinutes,
        String description,
        BigDecimal price
) {
}
