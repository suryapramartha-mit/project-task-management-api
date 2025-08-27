package com.project.management.api.dto;

import com.project.management.api.entity.Task;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SendTaskNotificationDTO {
    private Task task;
}
