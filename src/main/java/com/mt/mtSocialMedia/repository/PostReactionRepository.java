package com.mt.mtSocialMedia.repository;

import com.mt.mtSocialMedia.enums.Reaction;
import com.mt.mtSocialMedia.model.Post;
import com.mt.mtSocialMedia.model.PostReaction;
import com.mt.mtSocialMedia.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostReactionRepository extends JpaRepository<PostReaction,Long> {
    Long countByReactionTypeAndPost_Id(Reaction type, Long id);
    Boolean existsByAuthorAndPost(UserEntity author, Post post);
    PostReaction findByAuthorAndPost(UserEntity author, Post post);
}
