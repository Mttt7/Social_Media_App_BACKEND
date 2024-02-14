package com.mt.mtSocialMedia.service;

import com.mt.mtSocialMedia.dto.Comment.CommentResponseDto;
import org.springframework.data.domain.Page;

public interface CommentService {
    Page<CommentResponseDto> getCommentsByPostId(Long postId, int pageSize, int pageNumber, String sortBy);

    String addComment(Long postId, String commentContent);
}
