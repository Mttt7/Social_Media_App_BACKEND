package com.mt.mtSocialMedia.dto.Post;

import com.mt.mtSocialMedia.enums.Reaction;
import com.mt.mtSocialMedia.model.Post;
import com.mt.mtSocialMedia.model.UserEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostReactionResponseDto {
    private Long id;
    private int reactionType;
    private Long authorId;
    private Long postId;
}
