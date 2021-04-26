package com.thanhle.springboot.redditclone.util;

import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.thanhle.springboot.redditclone.dto.request.PostRequest;
import com.thanhle.springboot.redditclone.dto.response.PostResponse;
import com.thanhle.springboot.redditclone.model.Post;
import com.thanhle.springboot.redditclone.repository.CommentRepository;
import com.thanhle.springboot.redditclone.repository.VoteRepository;
import com.thanhle.springboot.redditclone.service.AuthService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class PostMapper {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private AuthService authService;

    @Mapping(target = "subredditName", source = "subreddit.name")
    @Mapping(target = "userName", source = "user.username")
    @Mapping(target = "commentCount", expression = "java(commentCount(post))")
    @Mapping(target = "duration", expression = "java(getDuration(post))")
    public abstract PostResponse entity2Response(Post post);

    @Mapping(target = "voteCount", constant = "0")
    public abstract Post request2Entity(PostRequest request);

    Integer commentCount(Post post) {
        return commentRepository.findByPost(post).size();
    }

    String getDuration(Post post) {
        return TimeAgo.using(post.getCreatedDate().toEpochMilli());
    }
}
