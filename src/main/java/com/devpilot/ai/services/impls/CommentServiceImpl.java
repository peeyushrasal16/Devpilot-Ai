package com.devpilot.ai.services.impls;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devpilot.ai.dtos.CommentDto;
import com.devpilot.ai.entities.Comment;
import com.devpilot.ai.repositories.CommentRepository;
import com.devpilot.ai.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDto createComment(CommentDto commentDto) {

        Comment comment = modelMapper.map(commentDto, Comment.class);
        
        comment.setCreatedAt(java.time.LocalDateTime.now());
        comment.setUpdatedAt(java.time.LocalDateTime.now());

        Comment savedComment = commentRepository.save(comment);

        return modelMapper.map(savedComment, CommentDto.class);
    }

    @Override
    public CommentDto updateComment(Long id, CommentDto commentDto) {

        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        comment.setContent(commentDto.getContent());

        Comment updatedComment = commentRepository.save(comment);

        return modelMapper.map(updatedComment, CommentDto.class);
    }

    @Override
    public CommentDto getCommentById(Long id) {

        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        return modelMapper.map(comment, CommentDto.class);
    }

    @Override
    public List<CommentDto> getAllComments() {

        List<Comment> comments = commentRepository.findAll();

        return comments.stream()
                .map(comment -> modelMapper.map(comment, CommentDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteComment(Long id) {

        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        commentRepository.delete(comment);
    }
}