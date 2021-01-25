package com.thanhle.springboot.redditclone.service;

import com.thanhle.springboot.redditclone.dto.request.PostRequest;
import com.thanhle.springboot.redditclone.dto.response.PostResponse;
import com.thanhle.springboot.redditclone.exception.ResourceNotFound;
import com.thanhle.springboot.redditclone.model.Post;
import com.thanhle.springboot.redditclone.model.Subreddit;
import com.thanhle.springboot.redditclone.model.User;
import com.thanhle.springboot.redditclone.repository.PostRepository;
import com.thanhle.springboot.redditclone.repository.SubredditRepository;
import com.thanhle.springboot.redditclone.repository.UserRepository;
import com.thanhle.springboot.redditclone.util.PostMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PostService {
    private final PostRepository postRepository;
    private final SubredditRepository subredditRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final PostMapper postMapper;

    public PostService(PostRepository postRepository, SubredditRepository subredditRepository, UserRepository userRepository, AuthService authService, PostMapper postMapper) {
        this.postRepository = postRepository;
        this.subredditRepository = subredditRepository;
        this.userRepository = userRepository;
        this.authService = authService;
        this.postMapper = postMapper;
    }
    @Transactional(readOnly = true)
    public PostResponse getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound(Post.class + " not found with id " + id.toString()));
        return postMapper.entity2Response(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(postMapper::entity2Response)
                .collect(Collectors.toList());
    }

    public void save(PostRequest postRequest) {
        Subreddit subreddit = subredditRepository.findByName(postRequest.getSubredditName())
                .orElseThrow(() -> new ResourceNotFound(Subreddit.class + " not found with name " + postRequest.getSubredditName()));
        Post post = postMapper.request2Entity(postRequest);
        User user = authService.getCurrentUser();
        post.setSubreddit(subreddit);
        post.setUser(user);
        postRepository.save(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsBySubreddit(Long subredditId) {
        Subreddit subreddit = subredditRepository.findById(subredditId)
                .orElseThrow(() -> new ResourceNotFound(Subreddit.class + " not found with id " + subredditId.toString()));
        List<Post> posts = postRepository.findAllBySubreddit(subreddit);
        return posts.stream().map(postMapper::entity2Response).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFound(User.class + " not found with username " + username));
        return postRepository.findByUser(user)
                .stream()
                .map(postMapper::entity2Response)
                .collect(Collectors.toList());
    }
}
