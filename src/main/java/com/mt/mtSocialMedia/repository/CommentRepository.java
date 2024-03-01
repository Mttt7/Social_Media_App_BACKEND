package com.mt.mtSocialMedia.repository;

import com.mt.mtSocialMedia.model.Comment;
import com.mt.mtSocialMedia.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {

    List<Comment> findAllByPost_Id(Long postId);
    Page<Comment> findAllByPost_Id(Long postId, PageRequest pg);

    Comment findTopByPost_IdOrderByReactionCountDesc(Long postId);
}
