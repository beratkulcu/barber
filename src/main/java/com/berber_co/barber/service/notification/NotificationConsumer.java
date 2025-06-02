package com.berber_co.barber.service.notification;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class NotificationConsumer {

    @KafkaListener(topics = "appointment-notification", groupId = "notification-group")
    public void consume(NotificationMessage notificationMessage) {
       log.info("Consumed notification message: {}", notificationMessage);
    }
}
