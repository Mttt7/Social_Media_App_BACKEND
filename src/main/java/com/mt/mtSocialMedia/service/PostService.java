package com.mt.mtSocialMedia.service;

import com.mt.mtSocialMedia.dto.Post.PostDto;
import com.mt.mtSocialMedia.dto.Post.PostReactionCountResponseDto;
import com.mt.mtSocialMedia.dto.Post.PostResponseDto;
import org.springframework.data.domain.Page;

public interface PostService {
    String createPost(PostDto postDto);
    PostResponseDto getPostById(Long id) throws Exception;
    Page<PostResponseDto> getPostsByUserIdPaginate(Long id,int pageSize, int pageNumber);
    PostResponseDto updatePostById(Long id, PostDto postDto);
    String deletePostById(Long id);
    PostReactionCountResponseDto reactToPost(Long id, int reactionType) throws Exception;
    PostReactionCountResponseDto getReactionsCount(Long id);

    Page<PostResponseDto> getFeedPostsPaginate(int pageSize, int pageNumber);

}
