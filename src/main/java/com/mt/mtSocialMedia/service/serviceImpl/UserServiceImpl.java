package com.mt.mtSocialMedia.service.serviceImpl;

import com.mt.mtSocialMedia.dto.User.UserResponseDto;
import com.mt.mtSocialMedia.mapper.UserMapper;
import com.mt.mtSocialMedia.model.UserEntity;
import com.mt.mtSocialMedia.repository.UserRepository;
import com.mt.mtSocialMedia.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    @Override
    public Long getUserId() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userRepository.findByUsername(username).orElse(null);
        return user.getId();
    }

    @Override
    public UserResponseDto getUserProfileById(Long userId) {
        return UserMapper.mapToUserResponseDto(userRepository.findById(userId).orElse(null));
    }
}
