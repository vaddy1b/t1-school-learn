package ru.babich.t1schoollearn.kafka.consumer;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.babich.t1schoollearn.service.NotificationService;

@Service
@RequiredArgsConstructor
public class TaskConsumer {
    private final NotificationService notificationService;
    private static final Logger logger = LoggerFactory.getLogger(TaskConsumer.class);

    @KafkaListener(topics = "task-updates", groupId = "task-group")
    public void listenTaskUpdates(String message) {
        try {
            String[] parts = message.split(":");
            Long taskId = Long.parseLong(parts[0]);
            String newStatus = parts[1];

            logger.info("Received task update from Kafka: TaskID={}, Status={}", taskId, newStatus);
            notificationService.sendStatusChangeNotification(taskId, newStatus);
        } catch (Exception e) {
            logger.error("Error processing Kafka message: {}", message, e);
        }
    }
}