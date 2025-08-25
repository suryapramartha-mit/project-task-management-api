package com.project.management.api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private static final Logger log = LoggerFactory.getLogger(NotificationService.class);

    @Async("taskExecutor")
    public void sendTaskNotification(String assigneeName, String taskName, String email) {
        log.info("Sending email to {}", email);
        log.info("[EMAIL] Task '{}' has been created and assigned to {}.", taskName, assigneeName);
    }
}

