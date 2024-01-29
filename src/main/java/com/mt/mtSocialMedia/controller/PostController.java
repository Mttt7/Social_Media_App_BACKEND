package com.mt.mtSocialMedia.controller;

import com.mt.mtSocialMedia.dto.Post.PostDto;
import com.mt.mtSocialMedia.dto.Post.PostResponseDto;
import com.mt.mtSocialMedia.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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

    @PatchMapping("/{id}")
    public ResponseEntity<PostResponseDto> updatePostById(@PathVariable Long id, @RequestBody PostDto postDto){
        PostResponseDto updatedPost = postService.updatePostById(id,postDto);
        if(updatedPost==null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }else{
            return ResponseEntity.status(HttpStatus.OK).body(updatedPost);
        }
    }



    @PostMapping("")
    public ResponseEntity<String> createPost(@RequestBody  PostDto postDto){
        String res = postService.createPost(postDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(res);
    }

    @GetMapping("")
    public ResponseEntity<Page<PostResponseDto>>
    getPostsByUserIdPaginate(@RequestParam Long id,
                             @RequestParam int pageSize,
                             @RequestParam int pageNumber){
       Page<PostResponseDto> res = postService.getPostsByUserIdPaginate(id,pageSize,pageNumber);
       return ResponseEntity.status(HttpStatus.OK).body(res);
    }
}
