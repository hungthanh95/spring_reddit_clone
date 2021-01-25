package com.thanhle.springboot.redditclone.util;

import com.thanhle.springboot.redditclone.dto.CommentDTO;
import com.thanhle.springboot.redditclone.model.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    @Mapping(target = "postId", source = "post.postId")
    @Mapping(target = "userName", source = "user.username")
    CommentDTO entity2Dto(Comment entity);
    @Mapping(target = "id", ignore = true)
    Comment Dto2Entity(CommentDTO dto);
}
