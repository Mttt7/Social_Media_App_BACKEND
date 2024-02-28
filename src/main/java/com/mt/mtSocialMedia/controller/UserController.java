package com.mt.mtSocialMedia.controller;

import com.mt.mtSocialMedia.dto.User.UserRequestDto;
import com.mt.mtSocialMedia.dto.User.UserResponseDto;
import com.mt.mtSocialMedia.model.UserEntity;
import com.mt.mtSocialMedia.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@CrossOrigin("*")
public class UserController {

    private final UserService userService;

    @GetMapping("/userId")
    public ResponseEntity<Long> getUserId(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserId());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDto> getUserProfile(@PathVariable Long userId){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserProfileById(userId));
    }

    @PatchMapping("")
    public ResponseEntity<UserResponseDto> editUserProfile(@RequestBody UserRequestDto userRequestDto){
        return ResponseEntity.status(HttpStatus.OK).body(userService.editUserProfile(userRequestDto));
    }

    // -- Frienship --
    @PostMapping("/friendship/{friendId}")
    public ResponseEntity<HashMap<String,String>> sendFriendRequest(@PathVariable Long friendId){
        return ResponseEntity.status(HttpStatus.OK).body(userService.sendFriendRequest(friendId));
    }
    @DeleteMapping("/friendship/{friendId}")
    public ResponseEntity<HashMap<String,String>> removeFriend(@PathVariable Long friendId){
        return ResponseEntity.status(HttpStatus.OK).body(userService.removeFriend(friendId));
    }

    @GetMapping("/{userId}/status")
    public ResponseEntity<HashMap<String,String>> getStatusWithGivenUser(@PathVariable Long userId){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getStatusWithGivenUser(userId));
    }

    @GetMapping("/{userId}/friends")
    public ResponseEntity<List<UserResponseDto>> getFriendList(@PathVariable Long userId){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getFriendList(userId));
    }

    @GetMapping("/{userId}/searchFriends/{query}")
    public ResponseEntity<List<UserResponseDto>> searchFriendOfUser(@PathVariable Long userId,
                                                                    @PathVariable String query){
        return ResponseEntity.status(HttpStatus.OK).body(userService.searchFriendsOfUser(userId,query));
    }

    @GetMapping("/friendRequests/sent")
    public ResponseEntity<List<UserResponseDto>> getSentFriendRequests(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getSentFriendRequest());
    }

    @GetMapping("/friendRequests/received")
    public ResponseEntity<List<UserResponseDto>> getReceivedFriendRequests(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getReceivedFriendRequest());
    }

    // ---- ----

}
