package com.devpilot.ai.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devpilot.ai.entities.ProjectMember;
import com.devpilot.ai.enums.ProjectMemberRole;

public interface ProjectMemberRepository extends JpaRepository<ProjectMember, Long> {
	

    long countByUser_Id(Long userId);

    long countByUser_IdAndRole(Long userId, ProjectMemberRole role);

}