package com.mt.mtSocialMedia.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.mt.mtSocialMedia.dto.Comment.CommentResponseDto;
import com.mt.mtSocialMedia.dto.Post.PostDto;
import com.mt.mtSocialMedia.dto.Post.PostReactionCountResponseDto;
import com.mt.mtSocialMedia.dto.Post.PostResponseDto;
import com.mt.mtSocialMedia.dto.PostWithPhotoDto;
import com.mt.mtSocialMedia.enums.Reaction;
import com.mt.mtSocialMedia.mapper.StringResponseMapper;
import com.mt.mtSocialMedia.service.CommentService;
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
    private final CommentService commentService;
    private final StringResponseMapper stringResponseMapper;

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
    public ResponseEntity<HashMap<String,String>> deletePostById(@PathVariable Long id){
        String res = postService.deletePostById(id);
        if(res==null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }else{
            return ResponseEntity.status(HttpStatus.OK).body(StringResponseMapper.mapToMap(res));
        }
    }

    @PostMapping("")
    public ResponseEntity<HashMap<String,String>> createPost(@RequestBody  PostDto postDto){
        String res = postService.createPost(postDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(StringResponseMapper.mapToMap(res));
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
    public ResponseEntity<Page<PostResponseDto>> getFeedPostsPaginate(@RequestParam int pageSize,
                                                                                       @RequestParam int pageNumber){
        Page<PostResponseDto> res = postService.getFeedPostsPaginate(pageSize,pageNumber);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @GetMapping("photos/{userId}")
    public ResponseEntity<Page<PostWithPhotoDto>> getPostsWithPhotosPaginate(@PathVariable Long userId,@RequestParam int pageSize,
                                                                                @RequestParam int pageNumber){
        Page<PostWithPhotoDto> res = postService.getPostsWithPhotosPaginate(userId,pageSize,pageNumber);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }
    @GetMapping("/friends")
    public  ResponseEntity<Page<PostResponseDto>> getFriendsPostsPaginate(@RequestParam int pageSize,
                                                                       @RequestParam int pageNumber){
        Page<PostResponseDto> res = postService.getFriendsPostsPaginate(pageSize,pageNumber);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @GetMapping("/{id}/reactions")
    public ResponseEntity<PostReactionCountResponseDto> getReactionsCount(@PathVariable Long id){
       return ResponseEntity.status(HttpStatus.OK).body(postService.getReactionsCount(id));
    }

    @PostMapping("/{id}/{reactionType}")
    public ResponseEntity<PostReactionCountResponseDto> reactToPost(@PathVariable Long id, @PathVariable int reactionType) throws Exception {
        PostReactionCountResponseDto res = postService.reactToPost(id,reactionType);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @GetMapping("/{id}/userReaction")
    public ResponseEntity<Map<String, Integer>> checkUserReaction(@PathVariable Long id) {
        Integer res = postService.checkUserReaction(id);
        Map<String, Integer> responseBody = new HashMap<>();
        responseBody.put("reaction", res);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @GetMapping("/{postId}/bestComment")
    public ResponseEntity<CommentResponseDto> getBestComment(@PathVariable Long postId){
        CommentResponseDto res = commentService.getBestComment(postId);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

}
