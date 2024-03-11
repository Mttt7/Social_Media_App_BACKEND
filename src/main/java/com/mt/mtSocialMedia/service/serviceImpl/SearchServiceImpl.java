package com.mt.mtSocialMedia.service.serviceImpl;

import com.mt.mtSocialMedia.dto.Post.PostResponseDto;
import com.mt.mtSocialMedia.dto.User.UserResponseDto;
import com.mt.mtSocialMedia.mapper.PostMapper;
import com.mt.mtSocialMedia.mapper.UserMapper;
import com.mt.mtSocialMedia.model.Post;
import com.mt.mtSocialMedia.model.UserEntity;
import com.mt.mtSocialMedia.repository.PostRepository;
import com.mt.mtSocialMedia.repository.UserRepository;
import com.mt.mtSocialMedia.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;


    @Override
    public Page<UserResponseDto> searchForUsers(String query, int pageNumber, int pageSize) {
        query = query.trim();
        Page<UserEntity> users = userRepository.findByUsernameOrFirstNameOrLastNameContaining(query,
                PageRequest.of(pageNumber,
                        pageSize,
                        Sort.by("createdAt").descending()));

        return new PageImpl<>(
                users.getContent().stream().map(
                        UserMapper::mapToUserResponseDto).collect(Collectors.toList()),
                PageRequest.of(pageNumber,pageSize),
                users.getTotalElements()
        );
    }

    @Override
    public Page<PostResponseDto> searchForPosts(String query, int pageNumber, int pageSize) {
        query = query.trim();
        Page<Post> posts = postRepository.findByUsernameOrFirstNameOrLastNameContaining(query,PageRequest.of(
                pageNumber,
                pageSize,
                Sort.by("createdAt").descending()));

        return new PageImpl<>(
                posts.getContent().stream().map(
                        PostMapper::mapToPostResponseDto).collect(Collectors.toList()),
                PageRequest.of(pageNumber,pageSize),
                posts.getTotalElements()
        );
    }
}
