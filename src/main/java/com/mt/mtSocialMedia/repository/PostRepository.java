package com.mt.mtSocialMedia.repository;

import com.mt.mtSocialMedia.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long> {
}
