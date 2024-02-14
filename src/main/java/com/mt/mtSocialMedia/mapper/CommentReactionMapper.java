package com.mt.mtSocialMedia.mapper;

import com.mt.mtSocialMedia.dto.Comment.CommentReactionResponseDto;
import com.mt.mtSocialMedia.model.CommentReaction;
import org.springframework.stereotype.Service;

@Service
public class CommentReactionMapper {

   public static CommentReactionResponseDto mapToCommentReactionResponse(CommentReaction commentReaction){
       return CommentReactionResponseDto.builder()
               .id(commentReaction.getId())
               .commentId(commentReaction.getComment().getId())
               .authorId(commentReaction.getAuthor().getId())
               .reactionType(commentReaction.getReactionType().getValue())
               .build();
   }
}
