package com.thanhle.springboot.redditclone.service;

import com.thanhle.springboot.redditclone.dto.SubredditDTO;
import com.thanhle.springboot.redditclone.exception.ResourceNotFound;
import com.thanhle.springboot.redditclone.model.Subreddit;
import com.thanhle.springboot.redditclone.repository.SubredditRepository;
import com.thanhle.springboot.redditclone.util.SubredditMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubredditService {

    private final SubredditRepository subredditRepository;
    private final AuthService authService;
    private final SubredditMapper subredditMapper;

    public SubredditService(SubredditRepository subredditRepository, AuthService authService, SubredditMapper subredditMapper) {
        this.subredditRepository = subredditRepository;
        this.authService = authService;
        this.subredditMapper = subredditMapper;
    }

    @Transactional(readOnly = true)
    public List<SubredditDTO> findAll() {
        return subredditRepository.findAll().stream()
                .map(subredditMapper::entity2Dto)
                .collect(Collectors.toList());
    }

    @Transactional
    public SubredditDTO save (SubredditDTO dto) {
        Subreddit subreddit = subredditRepository.save(subredditMapper.dto2Entity(dto));
        dto.setId(subreddit.getSubredditId());
        return dto;
    }

    @Transactional(readOnly = true)
    public SubredditDTO getSubreddit(Long id) {
        Subreddit subreddit = subredditRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Resource " + Subreddit.class + "not found by id" + id.toString()));
        return subredditMapper.entity2Dto(subreddit);
    }
}
