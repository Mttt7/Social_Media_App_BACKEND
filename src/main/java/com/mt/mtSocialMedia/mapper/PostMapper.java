package com.mt.mtSocialMedia.mapper;

import com.mt.mtSocialMedia.dto.Post.PostReactionResponseDto;
import com.mt.mtSocialMedia.dto.Post.PostResponseDto;
import com.mt.mtSocialMedia.dto.PostWithPhotoDto;
import com.mt.mtSocialMedia.model.Post;
import com.mt.mtSocialMedia.model.PostReaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostMapper {
    public static PostResponseDto mapToPostResponseDto(Post post){
        List<PostReactionResponseDto> reactions = new ArrayList<>();
        for (PostReaction pr:
             post.getReactions()) {
            reactions.add(PostReactionMapper.mapToPostReactionResponse(pr));
        }

        return PostResponseDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .lastUpdated(post.getLastUpdated())
                .reactionCount(post.getReactionCount()==null ? 0 : post.getReactionCount())
                .commentCount(post.getCommentCount())
                .reactions(reactions)
                .imageUrl(post.getImageUrl())
                .topic(post.getTopic())
                .userId(post.getAuthor().getId())
                .build();
    }

    public static PostWithPhotoDto mapToPostWithPhotoDto(Post post){
        return PostWithPhotoDto.builder()
                .postId(post.getId())
                .imageUrl(post.getImageUrl())
                .createdAt(post.getCreatedAt())
                .build();
    }
}