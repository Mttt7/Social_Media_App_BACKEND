package com.mt.mtSocialMedia.mapper;

import com.mt.mtSocialMedia.dto.Post.PostReactionResponseDto;
import com.mt.mtSocialMedia.model.PostReaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostReactionMapper {
    public static PostReactionResponseDto mapToPostReactionResponse(PostReaction postReaction){
        return PostReactionResponseDto.builder()
                .id(postReaction.getId())
                .postId(postReaction.getPost().getId())
                .authorId(postReaction.getAuthor().getId())
                .reactionType(postReaction.getReactionType().getValue())
                .build();
    }
}
