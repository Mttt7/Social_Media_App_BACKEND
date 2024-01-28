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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

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
}
