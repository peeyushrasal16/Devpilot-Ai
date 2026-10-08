package com.devpilot.ai.services.impls;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devpilot.ai.dtos.ActivityLogDto;
import com.devpilot.ai.entities.ActivityLog;
import com.devpilot.ai.repositories.ActivityLogRepository;
import com.devpilot.ai.services.ActivityLogService;

@Service
public class ActivityLogServiceImpl implements ActivityLogService {

    @Autowired
    private ActivityLogRepository activityLogRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ActivityLogDto createActivityLog(ActivityLogDto activityLogDto) {

        ActivityLog activityLog = modelMapper.map(activityLogDto, ActivityLog.class);

        ActivityLog savedActivityLog = activityLogRepository.save(activityLog);

        return modelMapper.map(savedActivityLog, ActivityLogDto.class);
    }

    @Override
    public ActivityLogDto getActivityLogById(Long id) {

        ActivityLog activityLog = activityLogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Activity log not found"));

        return modelMapper.map(activityLog, ActivityLogDto.class);
    }

    @Override
    public List<ActivityLogDto> getAllActivityLogs() {

        List<ActivityLog> activityLogs = activityLogRepository.findAll();

        return activityLogs.stream()
                .map(activityLog -> modelMapper.map(activityLog, ActivityLogDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteActivityLog(Long id) {

        ActivityLog activityLog = activityLogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Activity log not found"));

        activityLogRepository.delete(activityLog);
    }
}