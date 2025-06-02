package com.berber_co.barber.service.notification;

import com.berber_co.barber.enums.NotificationType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {
    private final KafkaTemplate<String, NotificationMessage> kafkaTemplate;

    public void sendNotification(NotificationMessage notificationMessage) {
        try {
            kafkaTemplate.send("appointment-notification", notificationMessage);
            log.info("Notification sent: {}", notificationMessage);
        } catch (Exception e) {
            log.error("Error sending notification: {}", e.getMessage());
            throw new RuntimeException("Failed to send Kafka message");
        }
    }

    public void sendCancellationNotification(String message, String reason) {
        NotificationMessage notification = new NotificationMessage(
                null,
                message,
                NotificationType.CANCEL.getValue(),
                LocalDateTime.now(),
                reason
        );
        kafkaTemplate.send("appointment-notification", notification);
        log.info("Cancellation notification sent: {}", notification);
    }

    public void sendSellerConfirmationNotification(Long sellerId, String message) {
        NotificationMessage notification = new NotificationMessage(
                sellerId,
                message,
                NotificationType.APPROVE.getValue(),
                LocalDateTime.now(),
                null
        );
        sendNotification(notification);
        log.info("Seller confirmation notification sent: {}", notification);
    }

    public void sendSellerCancellationNotification(Long sellerId, String message, String reason) {
        NotificationMessage notification = new NotificationMessage(
                sellerId,
                message,
                NotificationType.CANCEL.getValue(),
                LocalDateTime.now(),
                reason
        );
        sendNotification(notification);
        log.info("Seller cancellation notification sent: {}", notification);
    }
}
