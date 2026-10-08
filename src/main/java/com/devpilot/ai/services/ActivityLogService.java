package com.devpilot.ai.services;

import java.util.List;

import com.devpilot.ai.dtos.ActivityLogDto;

public interface ActivityLogService {

    ActivityLogDto createActivityLog(ActivityLogDto activityLogDto);

    ActivityLogDto getActivityLogById(Long id);

    List<ActivityLogDto> getAllActivityLogs();

    void deleteActivityLog(Long id);
}