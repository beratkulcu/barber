package com.berber_co.barber.data.response;

import java.math.BigDecimal;

public record BarberServiceResponse(
        String name,
        Integer durationInMinutes,
        BigDecimal price,
        String categoryName
) {
}
