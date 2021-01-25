package com.thanhle.springboot.redditclone.controller;

import com.thanhle.springboot.redditclone.dto.CommentDTO;
import com.thanhle.springboot.redditclone.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/comments")
public class CommentsController {
    private final CommentService commentService;

    public CommentsController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<Void> createComment(@RequestBody CommentDTO payload) {
        commentService.createComment(payload);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/by-post/{postId}")
    public ResponseEntity<List<CommentDTO>> getAllCommentsForPost(@PathVariable("postId") Long postId) {
        return status(HttpStatus.OK)
                .body(commentService.getCommentByPost(postId));
    }

    @GetMapping("/by-username/{username}")
    public ResponseEntity<List<CommentDTO>> getAllCommentsByUser(@PathVariable("username") String userName) {
        return status(HttpStatus.OK).body(commentService.getCommentsByUser(userName));
    }
}
