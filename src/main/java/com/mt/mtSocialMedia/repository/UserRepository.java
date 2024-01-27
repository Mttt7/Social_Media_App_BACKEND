package com.mt.mtSocialMedia.repository;

import com.mt.mtSocialMedia.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,Long> {
    Optional<UserEntity> findByUsername(String username);

    Boolean existsByUsername(String username);
}
