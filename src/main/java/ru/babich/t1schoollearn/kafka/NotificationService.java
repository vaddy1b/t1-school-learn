package ru.babich.t1schoollearn.service;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final JavaMailSender mailSender;
    private static final Logger logger = (Logger) LoggerFactory.getLogger(NotificationService.class);

    public void sendStatusChangeNotification(Long taskId, String newStatus) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("xray-zone@yandex.ru");
            message.setTo("aray-zone@example.com"); // Замените на реальный email
            message.setSubject("Task Status Update - Task #" + taskId);
            message.setText(String.format(
                    "The status of task #%d has been changed to: %s",
                    taskId, newStatus
            ));

            mailSender.send(message);
            logger.info("Email notification sent for TaskID={}", taskId);
        } catch (Exception e) {
            logger.error("Failed to send email notification for TaskID={}", taskId, e);
        }
    }
}