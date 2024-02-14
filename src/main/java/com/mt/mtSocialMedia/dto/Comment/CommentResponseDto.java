package com.mt.mtSocialMedia.dto.Comment;

import com.mt.mtSocialMedia.dto.Post.PostReactionResponseDto;
import com.mt.mtSocialMedia.model.Comment;
import com.mt.mtSocialMedia.model.UserEntity;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.List;

@Data
@Builder
public class CommentResponseDto {
    private Long id;
    private String content;
    private Long authorId;
    private Long reactionCount;
    private Timestamp createdAt;
    private Timestamp lastUpdated;
    private List<CommentReactionResponseDto> reactions;
}
