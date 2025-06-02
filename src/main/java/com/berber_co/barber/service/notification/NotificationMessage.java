package com.berber_co.barber.service.notification;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class NotificationMessage {
    private Long sellerId;
    private String message;
    private String notificationType;
    private String reason;
    private LocalDateTime timestamp;

    public NotificationMessage() {}

    public NotificationMessage(Long sellerId, String message, String notificationType, LocalDateTime timestamp, String reason) {
        this.sellerId = sellerId;
        this.message = message;
        this.notificationType = notificationType;
        this.timestamp = timestamp;
        this.reason = reason;
    }
}
