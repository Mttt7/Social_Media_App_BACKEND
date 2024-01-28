package com.mt.mtSocialMedia.repository;

import com.mt.mtSocialMedia.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TopicRepository extends JpaRepository<Topic,Long> {
    @Override
    Optional<Topic> findById(Long id);
}
