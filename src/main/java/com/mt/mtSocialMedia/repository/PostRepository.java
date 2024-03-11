package com.mt.mtSocialMedia.repository;

import com.mt.mtSocialMedia.model.Comment;
import com.mt.mtSocialMedia.model.Post;
import com.mt.mtSocialMedia.model.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {
    Page<Post> findAllByAuthor_Id(Long id, PageRequest pg);

    @Query("SELECT p FROM Post p WHERE p.author.id IN :friendIds")
    Page<Post> findAllByUserEntity_IdIn(List<Long> friendIds, Pageable pageable);

    Page<Post> findAllByAuthorAndImageUrlIsNotNull(UserEntity author,PageRequest pg);

    @Query("SELECT p FROM Post p WHERE p.content LIKE %:query% OR p.title LIKE %:query%")
    Page<Post> findByUsernameOrFirstNameOrLastNameContaining(@Param("query") String query, Pageable pageable);


}
