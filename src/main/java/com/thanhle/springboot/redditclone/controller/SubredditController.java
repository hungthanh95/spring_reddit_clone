package com.thanhle.springboot.redditclone.controller;

import com.thanhle.springboot.redditclone.dto.SubredditDTO;
import com.thanhle.springboot.redditclone.service.SubredditService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/subreddit")
public class SubredditController {

    private final SubredditService subredditService;

    public SubredditController(SubredditService subredditService) {
        this.subredditService = subredditService;
    }

    @GetMapping
    public List<SubredditDTO> getAllSubreddits() {
        return subredditService.findAll();
    }

    @GetMapping("/{id}")
    public SubredditDTO getSubreddit(@PathVariable Long id) {
        return subredditService.getSubreddit(id);
    }

    @PostMapping
    public SubredditDTO create(@RequestBody @Valid SubredditDTO subredditDto) {
        return subredditService.save(subredditDto);
    }
}