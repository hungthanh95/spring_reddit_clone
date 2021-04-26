package com.thanhle.springboot.redditclone.util;

import com.thanhle.springboot.redditclone.dto.VoteDTO;
import com.thanhle.springboot.redditclone.model.Vote;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VoteMapper {
    VoteDTO entity2Dto(Vote entity);
    Vote Dto2Entity(VoteDTO dto);
}
