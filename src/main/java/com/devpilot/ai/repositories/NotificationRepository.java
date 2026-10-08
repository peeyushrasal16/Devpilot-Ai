package com.devpilot.ai.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devpilot.ai.entities.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

}