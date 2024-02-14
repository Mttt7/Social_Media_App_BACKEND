package com.mt.mtSocialMedia.controller;

import com.mt.mtSocialMedia.dto.Comment.CommentResponseDto;
import com.mt.mtSocialMedia.mapper.StringResponseMapper;
import com.mt.mtSocialMedia.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
@CrossOrigin("*")
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/{postId}")
    public ResponseEntity<Page<CommentResponseDto>> getCommentsByPostIdPaginate(@PathVariable Long postId,
                                                                                @RequestParam int pageSize,
                                                                                @RequestParam int pageNumber,
                                                                                String sortBy){
       return ResponseEntity.status(HttpStatus.OK).body(commentService.getCommentsByPostId(postId,pageSize,pageNumber,sortBy));
    }

    @PostMapping("/{postId}")
    public ResponseEntity<Map<String,String>> addComment(@PathVariable Long postId,@RequestBody String commentContent){
        String res = commentService.addComment(postId,commentContent);
        return ResponseEntity.status(HttpStatus.OK).body(StringResponseMapper.mapToMap(res));
    }
}
