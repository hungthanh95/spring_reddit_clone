package com.thanhle.springboot.redditclone.repository;

import com.thanhle.springboot.redditclone.model.Post;
import com.thanhle.springboot.redditclone.model.User;
import com.thanhle.springboot.redditclone.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User user);
}
