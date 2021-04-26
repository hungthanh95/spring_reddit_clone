package com.thanhle.springboot.redditclone.service;

import com.thanhle.springboot.redditclone.common.enums.VoteType;
import com.thanhle.springboot.redditclone.dto.VoteDTO;
import com.thanhle.springboot.redditclone.exception.ResourceNotFound;
import com.thanhle.springboot.redditclone.exception.SpringRedditException;
import com.thanhle.springboot.redditclone.model.Post;
import com.thanhle.springboot.redditclone.model.User;
import com.thanhle.springboot.redditclone.model.Vote;
import com.thanhle.springboot.redditclone.repository.PostRepository;
import com.thanhle.springboot.redditclone.repository.VoteRepository;
import com.thanhle.springboot.redditclone.util.VoteMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class VoteService {

    private final VoteRepository voteRepository;
    private final PostRepository postRepository;
    private final AuthService authService;

    private final VoteMapper voteMapper;

    public VoteService(VoteRepository voteRepository,
                       PostRepository postRepository,
                       AuthService authService, VoteMapper voteMapper) {
        this.voteRepository = voteRepository;
        this.postRepository = postRepository;
        this.authService = authService;
        this.voteMapper = voteMapper;
    }

    @Transactional
    public void vote(VoteDTO voteDTO) {
        User user = authService.getCurrentUser();
        Post post = postRepository.findById(voteDTO.getPostId())
                .orElseThrow(() ->new ResourceNotFound(Post.class + " not found with id " + voteDTO.getPostId()));

        Optional<Vote> voteByPostAndUser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post, user);
        if (voteByPostAndUser.isPresent() && voteByPostAndUser.get().getVoteType().equals(voteDTO.getVoteType())) {
            throw new SpringRedditException("You have already " + voteDTO.getVoteType() + "'d for this post.");
        }

        if (VoteType.UPVOTE.equals(voteDTO.getVoteType())) {
            post.setVoteCount(post.getVoteCount() + 1);
        } else {
            post.setVoteCount(post.getVoteCount() - 1);
        }
        Vote vote = voteMapper.Dto2Entity(voteDTO);
        vote.setPost(post);
        vote.setUser(user);
        voteRepository.save(vote);
        postRepository.save(post);
    }
}
