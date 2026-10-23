package com.devpilot.ai.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devpilot.ai.entities.ProjectMember;
import com.devpilot.ai.enums.ProjectMemberRole;

public interface ProjectMemberRepository extends JpaRepository<ProjectMember, Long> {
	

    long countByUser_Id(Long userId);

    long countByUser_IdAndRole(Long userId, ProjectMemberRole role);

    // change the code: check duplicate member in project
    boolean existsByUser_IdAndProject_Id(Long userId, Long projectId);

    // change the code: check duplicate member excluding current id on update
    boolean existsByUser_IdAndProject_IdAndIdNot(Long userId, Long projectId, Long id);

    // change the code: delete all project members when project is deleted
    void deleteByProject_Id(Long projectId);

}