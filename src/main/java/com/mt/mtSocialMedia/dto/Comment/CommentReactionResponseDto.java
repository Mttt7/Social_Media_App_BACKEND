package com.mt.mtSocialMedia.dto.Comment;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentReactionResponseDto {
    private Long id;
    private int reactionType;
    private Long authorId;
    private Long commentId;
}
