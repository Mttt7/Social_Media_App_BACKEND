package com.mt.mtSocialMedia.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.mt.mtSocialMedia.dto.Post.PostDto;
import com.mt.mtSocialMedia.dto.Post.PostReactionCountResponseDto;
import com.mt.mtSocialMedia.dto.Post.PostResponseDto;
import com.mt.mtSocialMedia.enums.Reaction;
import com.mt.mtSocialMedia.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePostById(@PathVariable Long id){
        String res = postService.deletePostById(id);
        if(res==null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }else{
            return ResponseEntity.status(HttpStatus.OK).body(res);
        }
    }

    @PostMapping("")
    public ResponseEntity<HashMap<String,String>> createPost(@RequestBody  PostDto postDto){
        String res = postService.createPost(postDto);
        HashMap<String,String> mapRes = new HashMap<>();
        mapRes.put("message",res);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mapRes);
    }

    @GetMapping("")
    public ResponseEntity<Page<PostResponseDto>>
    getPostsByUserIdPaginate(@RequestParam Long id,
                             @RequestParam int pageSize,
                             @RequestParam int pageNumber){
       Page<PostResponseDto> res = postService.getPostsByUserIdPaginate(id,pageSize,pageNumber);
       return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @GetMapping("/feed")
    public  ResponseEntity<Page<PostResponseDto>> getFeedPostsPaginate(@RequestParam int pageSize,
                                                                                       @RequestParam int pageNumber){
        Page<PostResponseDto> res = postService.getFeedPostsPaginate(pageSize,pageNumber);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @GetMapping("/{id}/reactions")
    public ResponseEntity<PostReactionCountResponseDto> getReactionsCount(@PathVariable Long id){
       return ResponseEntity.status(HttpStatus.OK).body(postService.getReactionsCount(id));
    }

    @PostMapping("/{id}/{reactionType}")
    public ResponseEntity<PostReactionCountResponseDto> reactToPost(@PathVariable Long id, @PathVariable int reactionType) throws Exception {
        PostReactionCountResponseDto res= this.postService.reactToPost(id,reactionType);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

}
