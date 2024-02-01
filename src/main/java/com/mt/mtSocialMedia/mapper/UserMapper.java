package com.mt.mtSocialMedia.mapper;

import com.mt.mtSocialMedia.dto.User.UserResponseDto;
import com.mt.mtSocialMedia.model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserMapper {
    public static UserResponseDto mapToUserResponseDto(UserEntity userEntity){
       return UserResponseDto.builder()
                .id(userEntity.getId())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .username(userEntity.getUsername())
                .email(userEntity.getEmail())
                .about(userEntity.getAbout())
                .phone(userEntity.getPhone())
                .photoUrl(userEntity.getPhotoUrl())
                .backgroundUrl(userEntity.getBackgroundUrl())
                .createdAt(userEntity.getCreatedAt())
                .roles(userEntity.getRoles())
                .build();
    }
}
