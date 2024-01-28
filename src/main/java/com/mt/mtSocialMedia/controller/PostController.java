package com.mt.mtSocialMedia.controller;

import com.mt.mtSocialMedia.dto.Post.PostDto;
import com.mt.mtSocialMedia.dto.Post.PostResponseDto;
import com.mt.mtSocialMedia.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
@CrossOrigin("*")
public class PostController {
    private final PostService postService;

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> getPostById(@PathVariable Long id) throws Exception {
        PostResponseDto res = postService.getPostById(id);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @PostMapping("/")
    public ResponseEntity<String> createPost(@RequestBody  PostDto postDto){
        String res = postService.createPost(postDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(res);
    }
}