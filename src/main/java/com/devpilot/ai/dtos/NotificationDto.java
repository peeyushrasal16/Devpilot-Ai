package com.devpilot.ai.dtos;

import java.time.LocalDateTime;

import com.devpilot.ai.enums.NotificationType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDto {

    private Long id;

    private String title;

    private String message;

    private NotificationType type;

    private boolean isRead;

    private LocalDateTime createdAt;

    private Long userId;
}

