package com.mt.mtSocialMedia.service;

import com.mt.mtSocialMedia.dto.Comment.CommentReactionCountResponseDto;
import com.mt.mtSocialMedia.dto.Comment.CommentResponseDto;
import org.springframework.data.domain.Page;

public interface CommentService {
    Page<CommentResponseDto> getCommentsByPostId(Long postId, int pageSize, int pageNumber, String sortBy);

    String addComment(Long postId, String commentContent);

    CommentReactionCountResponseDto reactToComment(Long id, int reactionType) throws Exception;

    public CommentReactionCountResponseDto getReactionsCount(Long commentId);


}
