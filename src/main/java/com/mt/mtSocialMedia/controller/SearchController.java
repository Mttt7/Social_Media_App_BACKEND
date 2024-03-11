package com.mt.mtSocialMedia.controller;


import com.mt.mtSocialMedia.dto.Post.PostResponseDto;
import com.mt.mtSocialMedia.dto.User.UserResponseDto;
import com.mt.mtSocialMedia.model.UserEntity;
import com.mt.mtSocialMedia.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/search")
@RequiredArgsConstructor
@CrossOrigin("*")
public class SearchController {
    private final SearchService searchService;

    @GetMapping("user/{queryUser}")
    public ResponseEntity<Page<UserResponseDto>> searchForUsers(@PathVariable String queryUser, @RequestParam int pageNumber, @RequestParam int pageSize){
        Page<UserResponseDto> res = searchService.searchForUsers(queryUser, pageNumber, pageSize);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @GetMapping("/post/{queryPost}")
    public ResponseEntity<Page<PostResponseDto>> searchForUPosts(@PathVariable String queryPost, @RequestParam int pageNumber, @RequestParam int pageSize){
        Page<PostResponseDto> res = searchService.searchForPosts(queryPost, pageNumber, pageSize);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }
}
