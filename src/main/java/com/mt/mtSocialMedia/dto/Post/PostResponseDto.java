package com.mt.mtSocialMedia.dto.Post;

import com.mt.mtSocialMedia.model.Topic;
import com.mt.mtSocialMedia.model.UserEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Data
@Builder
public class PostResponseDto {

    private Long id;

    private String title;

    private String content;

    private Timestamp createdAt;

    private Timestamp lastUpdated;

    private Long reactionCount;

    private String imageUrl;

    private Topic topic;

    private Long userId;

}
