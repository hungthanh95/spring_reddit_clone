package com.thanhle.springboot.redditclone.repository;

import com.thanhle.springboot.redditclone.model.Comment;
import com.thanhle.springboot.redditclone.model.Post;
import com.thanhle.springboot.redditclone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);
    List<Comment> findAllByUser(User user);
}
