package com.devpilot.ai.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devpilot.ai.dtos.CommentDto;
import com.devpilot.ai.services.CommentService;

@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentDto> createComment(
            @RequestBody CommentDto commentDto) {

        CommentDto savedComment =
                commentService.createComment(commentDto);

        return new ResponseEntity<CommentDto>(
                savedComment, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentDto> updateComment(
            @PathVariable Long id,
            @RequestBody CommentDto commentDto) {

        CommentDto updatedComment =
                commentService.updateComment(id, commentDto);

        return ResponseEntity.ok(updatedComment);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentDto> getCommentById(
            @PathVariable Long id) {

        CommentDto comment =
                commentService.getCommentById(id);

        return ResponseEntity.ok(comment);
    }

    @GetMapping
    public ResponseEntity<List<CommentDto>> getAllComments() {

        List<CommentDto> comments =
                commentService.getAllComments();

        return ResponseEntity.ok(comments);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long id) {

        commentService.deleteComment(id);

        return ResponseEntity.noContent().build();
    }
}