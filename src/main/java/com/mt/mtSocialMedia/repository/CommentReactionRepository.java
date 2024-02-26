package com.mt.mtSocialMedia.repository;

import com.mt.mtSocialMedia.enums.Reaction;
import com.mt.mtSocialMedia.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentReactionRepository extends JpaRepository<CommentReaction,Long> {

    Long countByReactionTypeAndComment_Id(Reaction type, Long id);
    Boolean existsByAuthorAndComment(UserEntity author, Comment comment);
    CommentReaction findByAuthorAndComment(UserEntity author, Comment comment);
}
