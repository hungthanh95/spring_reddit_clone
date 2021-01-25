package com.thanhle.springboot.redditclone.util;

import com.thanhle.springboot.redditclone.dto.SubredditDTO;
import com.thanhle.springboot.redditclone.model.Post;
import com.thanhle.springboot.redditclone.model.Subreddit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SubredditMapper {
    @Mapping(target = "postCount", expression = "java(mapPosts(subreddit.getPosts()))")
    SubredditDTO entity2Dto (Subreddit subreddit);

    default Integer mapPosts(List<Post> numberOfPosts) {
        return numberOfPosts.size();
    }

    @Mapping(target = "subredditId", ignore = true)
    Subreddit dto2Entity (SubredditDTO dto);
}
