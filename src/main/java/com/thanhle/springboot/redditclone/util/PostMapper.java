package com.thanhle.springboot.redditclone.util;

import com.thanhle.springboot.redditclone.dto.request.PostRequest;
import com.thanhle.springboot.redditclone.dto.response.PostResponse;
import com.thanhle.springboot.redditclone.model.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostMapper {
    @Mapping(target = "subredditName", source = "subreddit.name")
    @Mapping(target = "userName", source = "user.username")
    PostResponse entity2Response(Post post);
    Post request2Entity(PostRequest request);
}
