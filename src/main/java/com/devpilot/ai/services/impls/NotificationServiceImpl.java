package com.devpilot.ai.services.impls;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devpilot.ai.dtos.NotificationDto;
import com.devpilot.ai.entities.Notification;
import com.devpilot.ai.repositories.NotificationRepository;
import com.devpilot.ai.services.NotificationService;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public NotificationDto createNotification(NotificationDto notificationDto) {

        Notification notification = modelMapper.map(notificationDto, Notification.class);
        
        notification.setCreatedAt(java.time.LocalDateTime.now());

        Notification savedNotification = notificationRepository.save(notification);

        return modelMapper.map(savedNotification, NotificationDto.class);
    }

    @Override
    public NotificationDto getNotificationById(Long id) {

        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found"));

        return modelMapper.map(notification, NotificationDto.class);
    }

    @Override
    public List<NotificationDto> getAllNotifications() {

        List<Notification> notifications = notificationRepository.findAll();

        return notifications.stream()
                .map(notification -> modelMapper.map(notification, NotificationDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public NotificationDto markAsRead(Long id) {

        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found"));

        notification.setRead(true);

        Notification updatedNotification = notificationRepository.save(notification);

        return modelMapper.map(updatedNotification, NotificationDto.class);
    }

    @Override
    public void deleteNotification(Long id) {

        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found"));

        notificationRepository.delete(notification);
    }
}