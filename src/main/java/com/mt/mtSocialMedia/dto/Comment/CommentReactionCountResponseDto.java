package com.mt.mtSocialMedia.dto.Comment;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentReactionCountResponseDto {
    private Long overall;
    private Long like;
    private Long love;
    private Long haha;
    private Long sad;
    private Long angry;
}
