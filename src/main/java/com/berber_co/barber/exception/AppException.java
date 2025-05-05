package com.berber_co.barber.exception;

import org.apache.kafka.common.protocol.types.Field;

public class AppException extends RuntimeException {
    private final String message;
    private final String data;

    public AppException(String message, String data) {
        super(message);
        this.data = data;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
