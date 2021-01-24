package com.thanhle.springboot.redditclone.service;

import com.thanhle.springboot.redditclone.repository.SubredditRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SubredditService {

    private final SubredditRepository subredditRepository;
    private final AuthService authService;

    public SubredditService(SubredditRepository subredditRepository, AuthService authService) {
        this.subredditRepository = subredditRepository;
        this.authService = authService;
    }

//    @Transactional(readOnly = true)
//    public List<Subredd>

}
