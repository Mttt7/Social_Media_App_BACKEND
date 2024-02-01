package com.mt.mtSocialMedia.controller;

import com.mt.mtSocialMedia.dto.User.UserResponseDto;
import com.mt.mtSocialMedia.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
