package com.thanhle.springboot.redditclone.service;

import com.thanhle.springboot.redditclone.dto.CommentDTO;
import com.thanhle.springboot.redditclone.exception.ResourceNotFound;
import com.thanhle.springboot.redditclone.model.Comment;
import com.thanhle.springboot.redditclone.model.NotificationEmail;
import com.thanhle.springboot.redditclone.model.Post;
import com.thanhle.springboot.redditclone.model.User;
import com.thanhle.springboot.redditclone.repository.CommentRepository;
import com.thanhle.springboot.redditclone.repository.PostRepository;
import com.thanhle.springboot.redditclone.repository.UserRepository;
import com.thanhle.springboot.redditclone.util.CommentMapper;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class CommentService {

    private static final String POST_URL = "";
    private final CommentMapper commentMapper;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final MailContentBuilder mailContentBuilder;
    private final MailService mailService;

    public CommentService(CommentMapper commentMapper,
                          PostRepository postRepository,
                          CommentRepository commentRepository,
                          UserRepository userRepository,
                          AuthService authService,
                          MailContentBuilder mailContentBuilder,
                          MailService mailService) {
        this.commentMapper = commentMapper;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.authService = authService;
        this.mailContentBuilder = mailContentBuilder;
        this.mailService = mailService;
    }

    public void createComment (CommentDTO dto) {
        Post post = postRepository.findById(dto.getPostId())
                .orElseThrow(() -> new ResourceNotFound(Post.class + " not found with id + " + dto.getPostId()));
        Comment comment = commentMapper.Dto2Entity(dto);
        comment.setPost(post);
        comment.setUser(authService.getCurrentUser());
        commentRepository.save(comment);

        String message = mailContentBuilder.build(post.getUser().getUsername() + " posted a comment on your post." + POST_URL);
        sendCommentNotification(message, post.getUser());
    }

    private void sendCommentNotification(String message, User user) {
        mailService.sendMail(new NotificationEmail(user.getUsername() + " Commented on your post", user.getEmail(), message));
    }

    public List<CommentDTO> getCommentByPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFound(Post.class + " not found with id + " + postId));
        return commentRepository.findByPost(post)
                .stream()
                .map(commentMapper::entity2Dto)
                .collect(toList());
    }

    public List<CommentDTO> getCommentsByUser(String userName) {
        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new ResourceNotFound(User.class + " not found with username + " + userName));
        return commentRepository.findAllByUser(user)
                .stream()
                .map(commentMapper::entity2Dto)
                .collect(toList());
    }
}
