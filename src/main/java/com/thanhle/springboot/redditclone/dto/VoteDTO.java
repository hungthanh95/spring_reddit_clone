package com.thanhle.springboot.redditclone.dto;

import com.thanhle.springboot.redditclone.common.enums.VoteType;

public class VoteDTO {
    private VoteType voteType;
    private Long postId;

    public VoteType getVoteType() {
        return voteType;
    }

    public void setVoteType(VoteType voteType) {
        this.voteType = voteType;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }
}
