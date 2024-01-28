package com.mt.mtSocialMedia.service;

import com.mt.mtSocialMedia.dto.Post.PostDto;
import com.mt.mtSocialMedia.dto.Post.PostResponseDto;

public interface PostService {
    String createPost(PostDto postDto);

    PostResponseDto getPostById(Long id) throws Exception;
}
