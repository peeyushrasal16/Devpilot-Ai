package com.devpilot.ai.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.devpilot.ai.enums.ProjectStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    private ProjectStatus status;

    private LocalDate startDate;

    private LocalDate endDate;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
    
    @OneToMany(mappedBy = "project")
    private List<ProjectMember> projectMembers;
    
    @OneToMany(mappedBy = "project")
    private List<Task> tasks;
    
    @OneToMany(mappedBy = "project")
    private List<Bug> bugs;
    
    @OneToMany(mappedBy = "project")
    private List<Documentation> documentations;
    
    @OneToMany(mappedBy = "project")
    private List<ActivityLog> activityLogs;
}