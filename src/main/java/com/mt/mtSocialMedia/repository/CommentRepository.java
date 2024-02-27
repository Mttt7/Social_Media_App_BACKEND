package com.mt.mtSocialMedia.repository;

import com.mt.mtSocialMedia.model.Comment;
import com.mt.mtSocialMedia.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long> {

    Page<Comment> findAllByPost_Id(Long postId, PageRequest pg);

    Comment findTopByPost_IdOrderByReactionCountDesc(Long postId);
}
