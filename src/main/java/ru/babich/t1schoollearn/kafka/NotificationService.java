package ru.babich.t1schoollearn.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationService {
    private final JavaMailSender mailSender;

    @Value("${notification.email.from}")
    private String fromEmail;

    @Value("${notification.email.to}")
    private String toEmail;

    @Value("${notification.email.subject-prefix}")
    private String subjectPrefix;

    public void sendStatusChangeNotification(Long taskId, String newStatus) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(toEmail);
            message.setSubject(subjectPrefix + taskId);
            message.setText(String.format(
                    "The status of task #%d has been changed to: %s",
                    taskId, newStatus
            ));

            mailSender.send(message);
            log.info("Email notification sent for TaskID={}", taskId);
        } catch (Exception e) {
            log.error("Failed to send email notification for TaskID={}", taskId, e);
        }
    }
}