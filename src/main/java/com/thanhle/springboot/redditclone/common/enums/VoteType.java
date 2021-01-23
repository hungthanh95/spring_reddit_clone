package com.thanhle.springboot.redditclone.common.enums;

public enum VoteType {
    UPVOTE(1),
    DOWNVOTE(-1),
    ;

    VoteType(int direction) {
    }
}
