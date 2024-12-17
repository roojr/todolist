package com.br.projeto.notification.consumer;


import com.br.projeto.notification.dto.NotificationDTO;
import com.br.projeto.notification.service.NotificationService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class NotificationConsumer {

    final NotificationService notificationService;

    public NotificationConsumer(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @RabbitListener(queues = "${spring.rabbitmq.queue.name}")
    public void sendNotification(@Payload NotificationDTO notificationDTO) {
    notificationService.sendEmail(notificationDTO);

    }

}
