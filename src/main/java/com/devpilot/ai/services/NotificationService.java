package com.devpilot.ai.services;

import java.util.List;
import com.devpilot.ai.dtos.NotificationDto;

public interface NotificationService {

    NotificationDto createNotification(NotificationDto notificationDto);

    NotificationDto getNotificationById(Long id);

    List<NotificationDto> getAllNotifications();

    NotificationDto markAsRead(Long id);

    void deleteNotification(Long id);
}