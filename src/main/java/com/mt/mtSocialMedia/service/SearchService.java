package com.mt.mtSocialMedia.service;

import com.mt.mtSocialMedia.dto.Post.PostResponseDto;
import com.mt.mtSocialMedia.dto.User.UserResponseDto;
import com.mt.mtSocialMedia.model.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SearchService {
    Page<UserResponseDto> searchForUsers(String query, int pageNumber, int pageSize);

    Page<PostResponseDto> searchForPosts(String queryPost, int pageNumber, int pageSize);

}
