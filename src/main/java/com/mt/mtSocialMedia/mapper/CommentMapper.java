package com.mt.mtSocialMedia.mapper;

import com.mt.mtSocialMedia.dto.Comment.CommentReactionResponseDto;
import com.mt.mtSocialMedia.dto.Comment.CommentResponseDto;
import com.mt.mtSocialMedia.model.Comment;
import com.mt.mtSocialMedia.model.CommentReaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentMapper {
    public static CommentResponseDto mapToCommentResponseDto(Comment comment){
        List<CommentReactionResponseDto> reactions = new ArrayList<>();
        for (CommentReaction cr:
        comment.getReactions()){
            reactions.add(CommentReactionMapper.mapToCommentReactionResponse(cr));
        }


        return CommentResponseDto
                .builder()
                .id(comment.getId())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .lastUpdated(comment.getLastUpdated())
                .reactionCount(comment.getReactionCount()==null ? 0 : comment.getReactionCount())
                .reactions(reactions)
                .authorId(comment.getAuthor().getId())
                .build();
    }
}
