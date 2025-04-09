package ru.babich.t1schoollearn.kafka.producer;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskProducer {
    private static final Logger logger = LoggerFactory.getLogger(TaskProducer.class);
    private static final String TOPIC = "task-updates";

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendTaskUpdate(Long taskId, String newStatus) {
        String message = taskId + ":" + newStatus;
        kafkaTemplate.send(TOPIC, message);
        logger.info("Sent task update to Kafka: TaskID={}, Status={}", taskId, newStatus);
    }
}
