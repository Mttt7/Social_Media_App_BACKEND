package com.mt.mtSocialMedia.mapper;

import com.mt.mtSocialMedia.dto.Post.PostResponseDto;
import com.mt.mtSocialMedia.model.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostMapper {
    public PostResponseDto mapToPostResponseDto(Post post){
        return PostResponseDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .lastUpdated(post.getLastUpdated())
                .reactionCount(post.getReactionCount()==null ? 0 : post.getReactionCount())
                .imageUrl(post.getImageUrl())
                .topic(post.getTopic())
                .userId(post.getUserEntity().getId())
                .build();
    }
}
