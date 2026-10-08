package com.devpilot.ai.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devpilot.ai.entities.ActivityLog;

public interface ActivityLogRepository extends JpaRepository<ActivityLog, Long> {

}