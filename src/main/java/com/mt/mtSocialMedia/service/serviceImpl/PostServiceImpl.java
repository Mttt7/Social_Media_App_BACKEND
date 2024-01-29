package com.mt.mtSocialMedia.service.serviceImpl;

import com.mt.mtSocialMedia.dto.Post.PostDto;
import com.mt.mtSocialMedia.dto.Post.PostResponseDto;
import com.mt.mtSocialMedia.mapper.PostMapper;
import com.mt.mtSocialMedia.model.Post;
import com.mt.mtSocialMedia.model.Topic;
import com.mt.mtSocialMedia.model.UserEntity;
import com.mt.mtSocialMedia.repository.PostRepository;
import com.mt.mtSocialMedia.repository.TopicRepository;
import com.mt.mtSocialMedia.repository.UserRepository;
import com.mt.mtSocialMedia.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final UserRepository userRepository;
    private final TopicRepository topicRepository;
    private final PostRepository postRepository;
    private final PostMapper postMapper;
    @Override
    public String createPost(PostDto postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        if(postDto.getImageUrl() != null) post.setImageUrl(postDto.getImageUrl());
        if(postDto.getTopicId() != null){
            Topic topic = topicRepository.findById(postDto.getTopicId()).orElse(null);
            post.setTopic(topic);
        }
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userRepository.findByUsername(username).orElse(null);

        post.setUserEntity(user);
        postRepository.save(post);

        return "Post created!";
    }

    @Override
    public PostResponseDto getPostById(Long id) throws Exception {
        Post post = postRepository.findById(id).orElseThrow(()-> new Exception("Post Not Found"));
        return postMapper.mapToPostResponseDto(post);
    }

    @Override
    public Page<PostResponseDto> getPostsByUserIdPaginate(Long id, int pageSize, int pageNumber) {
        Page<Post> postsPage = postRepository.findAll(PageRequest.of(pageNumber,
                pageSize,
                Sort.by("createdAt").descending()));

        return new PageImpl<>(
                postsPage
                        .stream()
                        .map(PostMapper::mapToPostResponseDto)
                        .collect(Collectors.toList()),
                PageRequest.of(pageNumber,pageSize),
                postsPage.getTotalElements()
        );
    }
}
