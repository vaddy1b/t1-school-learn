package ru.babich.t1schoollearn.kafka.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class TaskProducer {
    @Value("${spring.kafka.topics.task-updated}")
    private String topic;

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendTaskUpdate(Long taskId, String newStatus) {
        String message = taskId + ":" + newStatus;
        kafkaTemplate.send(topic, message);
        log.info("Sent task update to Kafka: TaskID={}, Status={}", taskId, newStatus);
    }
}
