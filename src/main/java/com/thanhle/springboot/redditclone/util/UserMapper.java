package com.thanhle.springboot.redditclone.util;

import com.thanhle.springboot.redditclone.dto.request.RegisterRequest;
import com.thanhle.springboot.redditclone.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "password", ignore = true)
    User request2Entity(RegisterRequest request);
}
