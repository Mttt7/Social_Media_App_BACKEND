package com.mt.mtSocialMedia.controller;

import com.mt.mtSocialMedia.config.security.JWTGenerator;
import com.mt.mtSocialMedia.dto.AuthResponseDto;
import com.mt.mtSocialMedia.dto.LoginDto;
import com.mt.mtSocialMedia.dto.RegisterDto;
import com.mt.mtSocialMedia.mapper.StringResponseMapper;
import com.mt.mtSocialMedia.model.Role;
import com.mt.mtSocialMedia.model.UserEntity;
import com.mt.mtSocialMedia.repository.RoleRepository;
import com.mt.mtSocialMedia.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTGenerator jwtGenerator;


    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginDto loginDto){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsername(),loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication);
        return new ResponseEntity<>(new AuthResponseDto(token),HttpStatus.OK);
    }


    @PostMapping("/register")
    public ResponseEntity<Map<String,String>> register(@RequestBody RegisterDto registerDto){
        if(!Objects.equals(registerDto.getPassword(), registerDto.getPasswordRepeated())){
            return new ResponseEntity<>(StringResponseMapper.mapToMap("Passwords don't match"), HttpStatus.BAD_REQUEST);
        }
        if(userRepository.existsByUsername(registerDto.getUsername())){
            return new ResponseEntity<>(StringResponseMapper.mapToMap("Username is taken!"), HttpStatus.BAD_REQUEST);
        }
        if(registerDto.getFirstName().length()>25 ||
                registerDto.getFirstName().length()<3 ||
                registerDto.getLastName().length()>25 ||
                registerDto.getLastName().length()<3){

            return new ResponseEntity<>(StringResponseMapper.mapToMap("Bad Name!"), HttpStatus.BAD_REQUEST);
        }

        UserEntity user = new UserEntity();
        user.setUsername(registerDto.getUsername());
        user.setFirstName(registerDto.getFirstName());
        user.setLastName(registerDto.getLastName());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Role role = roleRepository.findByName("USER").get();
        user.setRoles(Collections.singletonList(role));

        userRepository.save(user);
        return new ResponseEntity<>(StringResponseMapper.mapToMap("success"), HttpStatus.OK);
    }
}
