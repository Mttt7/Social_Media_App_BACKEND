package com.mt.mtSocialMedia.dto;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class PostWithPhotoDto {
    private String imageUrl;
    private Long postId;
    private Timestamp createdAt;
}
