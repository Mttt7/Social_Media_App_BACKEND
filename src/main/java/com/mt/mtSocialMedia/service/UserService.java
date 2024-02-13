package com.mt.mtSocialMedia.service;

import com.mt.mtSocialMedia.dto.User.UserRequestDto;
import com.mt.mtSocialMedia.dto.User.UserResponseDto;
import com.mt.mtSocialMedia.model.UserEntity;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;

public interface UserService {


    Long getUserId();

    UserResponseDto getUserProfileById(Long userId);

    HashMap<String, String> sendFriendRequest(Long friendId);


    List<UserResponseDto> getFriendList(Long userId);

    List<UserResponseDto> getSentFriendRequest();

    List<UserResponseDto> getReceivedFriendRequest();

    HashMap<String, String> removeFriend(Long friendId);

    HashMap<String, String> getStatusWithGivenUser(Long userId);

    UserResponseDto editUserProfile(UserRequestDto userRequestDto);
}
