package com.mt.mtSocialMedia.dto;

import com.mt.mtSocialMedia.model.UserEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Data
@Builder
public class NotificationResponseDto {

    private Long id;
    private Long authorId;
    private String authorName;
    private Long receiverId;
    private String type;
    private Long contentId;
    private String authorProfilePhotoSrc;
    private Boolean read;
    private Timestamp createdAt;





}
