package ru.babich.t1schoollearn.kafka.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.babich.t1schoollearn.kafka.NotificationService;

@Service
@Slf4j
@RequiredArgsConstructor
public class TaskConsumer {
    private final NotificationService notificationService;

    @KafkaListener(topics = "${kafka.topics.task-updates}", groupId = "${kafka.topi}")
    public void listenTaskUpdates(String message) {
        try {
            String[] parts = message.split(":");
            Long taskId = Long.parseLong(parts[0]);
            String newStatus = parts[1];

            log.info("Received task update from Kafka: TaskID={}, Status={}", taskId, newStatus);
            notificationService.sendStatusChangeNotification(taskId, newStatus);
        } catch (Exception e) {
            log.error("Error processing Kafka message: {}", message, e);
        }
    }
}