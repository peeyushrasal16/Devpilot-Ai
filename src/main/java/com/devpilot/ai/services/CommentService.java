package com.devpilot.ai.services;

import java.util.List;
import com.devpilot.ai.dtos.CommentDto;

public interface CommentService {

    CommentDto createComment(CommentDto commentDto);

    CommentDto updateComment(Long id, CommentDto commentDto);

    CommentDto getCommentById(Long id);

    List<CommentDto> getAllComments();

    void deleteComment(Long id);
}