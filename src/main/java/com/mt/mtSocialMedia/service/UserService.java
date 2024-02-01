package com.mt.mtSocialMedia.service;

import com.mt.mtSocialMedia.dto.User.UserResponseDto;
import org.springframework.http.ResponseEntity;

public interface UserService {


    Long getUserId();

    UserResponseDto getUserProfileById(Long userId);
}
