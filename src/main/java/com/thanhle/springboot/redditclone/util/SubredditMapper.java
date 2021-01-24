package com.thanhle.springboot.redditclone.util;

import com.thanhle.springboot.redditclone.dto.SubredditDTO;
import com.thanhle.springboot.redditclone.model.Subreddit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SubredditMapper {
    SubredditDTO entity2Dto (Subreddit subreddit);

    @Mapping(target = "id", ignore = true)
    Subreddit dto2Entity (SubredditDTO dto);
}
