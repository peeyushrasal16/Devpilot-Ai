package com.devpilot.ai.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devpilot.ai.entities.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}