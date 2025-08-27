package com.project.management.api.service;

import com.project.management.api.dto.SendTaskNotificationDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
public class NotificationService {

    private static final Logger log = LoggerFactory.getLogger(NotificationService.class);

    @Async("taskExecutor")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleTaskCreatedEvent(SendTaskNotificationDTO event) {
        var task = event.getTask();
        sendTaskNotification(task.getAssignedTo().getName(), task.getTaskName(), task.getAssignedTo().getEmail());
    }
    private void sendTaskNotification(String assigneeName, String taskName, String email) {
        log.info("Sending email to {}", email);
        log.info("[EMAIL] Task '{}' has been created and assigned to {}.", taskName, assigneeName);
    }
}

