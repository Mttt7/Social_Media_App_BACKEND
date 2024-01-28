package com.mt.mtSocialMedia.controller;

import com.mt.mtSocialMedia.dto.PostDto;
import com.mt.mtSocialMedia.model.Post;
import com.mt.mtSocialMedia.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
@CrossOrigin("*")
public class PostController {
    private final PostService postService;

    @PostMapping("/")
    public ResponseEntity<String> createPost(@RequestBody  PostDto postDto){
        postService.createPost(postDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Post created!");
    }
}
