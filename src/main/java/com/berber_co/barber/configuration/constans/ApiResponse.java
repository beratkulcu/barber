package com.berber_co.barber.configuration.constans;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private final boolean success;
    private final T data;
    private final ErrorBody error;

    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
                .success(true)
                .data(data)
                .error(null)
                .build();
    }

    public static ApiResponse<Void> error(String code, String message) {
        return ApiResponse.<Void>builder()
                .success(false)
                .data(null)
                .error(new ErrorBody(code, message))
                .build();
    }

    @Getter
    public static class ErrorBody {
        private final String message;
        private final String code;

        public ErrorBody(String message, String code) {
            this.message = message;
            this.code = code;
        }
    }
}
