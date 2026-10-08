package com.devpilot.ai.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devpilot.ai.dtos.ActivityLogDto;
import com.devpilot.ai.services.ActivityLogService;

@RestController
@RequestMapping("/activity-logs")
public class ActivityLogController {

    @Autowired
    private ActivityLogService activityLogService;

    @PostMapping
    public ResponseEntity<ActivityLogDto> createActivityLog(
            @RequestBody ActivityLogDto activityLogDto) {

        ActivityLogDto savedActivityLog =
                activityLogService.createActivityLog(activityLogDto);

        return new ResponseEntity<ActivityLogDto>(
                savedActivityLog, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActivityLogDto> getActivityLogById(
            @PathVariable Long id) {

        ActivityLogDto activityLog =
                activityLogService.getActivityLogById(id);

        return ResponseEntity.ok(activityLog);
    }

    @GetMapping
    public ResponseEntity<List<ActivityLogDto>> getAllActivityLogs() {

        List<ActivityLogDto> activityLogs =
                activityLogService.getAllActivityLogs();

        return ResponseEntity.ok(activityLogs);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActivityLog(
            @PathVariable Long id) {

        activityLogService.deleteActivityLog(id);

        return ResponseEntity.noContent().build();
    }
}