package com.mt.mtSocialMedia.repository;

import com.mt.mtSocialMedia.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {

    //Page<Post> findAll(PageRequest pg);
    Page<Post> findAllByUserEntity_Id(Long id, PageRequest pg);
}
