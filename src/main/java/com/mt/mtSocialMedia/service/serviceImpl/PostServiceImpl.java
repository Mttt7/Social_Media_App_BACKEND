package com.mt.mtSocialMedia.service.serviceImpl;

import com.mt.mtSocialMedia.dto.Post.PostDto;
import com.mt.mtSocialMedia.dto.Post.PostReactionCountResponseDto;
import com.mt.mtSocialMedia.dto.Post.PostResponseDto;
import com.mt.mtSocialMedia.dto.PostWithPhotoDto;
import com.mt.mtSocialMedia.dto.User.UserResponseDto;
import com.mt.mtSocialMedia.enums.Reaction;
import com.mt.mtSocialMedia.mapper.PostMapper;
import com.mt.mtSocialMedia.model.*;
import com.mt.mtSocialMedia.repository.*;
import com.mt.mtSocialMedia.service.NotificationService;
import com.mt.mtSocialMedia.service.PostService;
import com.mt.mtSocialMedia.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final UserRepository userRepository;
    private final TopicRepository topicRepository;
    private final PostRepository postRepository;
    private final PostReactionRepository postReactionRepository;
    private final CommentRepository commentRepository;
    private final UserService userService;
    private final NotificationService notificationService;

    @Override
    public String createPost(PostDto postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        if(postDto.getImageUrl() != null && !postDto.getImageUrl().equals("")) post.setImageUrl(postDto.getImageUrl());
        if(postDto.getTopicId() != null){
            Topic topic = topicRepository.findById(postDto.getTopicId()).orElse(null);
            post.setTopic(topic);
        }
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userRepository.findByUsername(username).orElse(null);

        post.setAuthor(user);
        postRepository.save(post);

        return "Post created!";
    }

    @Override
    public PostResponseDto updatePostById(Long id, PostDto postDto) {
        Post post = postRepository.findById(id).orElse(null);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        if(Objects.equals(post.getAuthor().getUsername(), username)){
           if(postDto.getTitle() != null) post.setTitle(postDto.getTitle());
           if(postDto.getContent() != null) post.setContent(postDto.getContent());
           if(postDto.getImageUrl() != null) post.setImageUrl(postDto.getImageUrl());
           if(postDto.getTopicId() !=null ){
               post.setTopic(topicRepository.findById(postDto.getTopicId()).orElse(null));
           }

           postRepository.save(post);
           return PostMapper.mapToPostResponseDto(post);
        }else{
            return null;
        }
    }

    @Override
    public String deletePostById(Long id) {
        Post post = postRepository.findById(id).orElse(null);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        if(Objects.equals(post.getAuthor().getUsername(), username)){

            List<Comment> comments = commentRepository.findAllByPost_Id(id);
            commentRepository.deleteAll(comments);

            postRepository.delete(post);
            return "Post removed id-"+id;
        }else{
            return null;
        }
    }

    @Override
    public PostReactionCountResponseDto reactToPost(Long id, int reactionType) throws Exception {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userRepository.findByUsername(username).orElse(null);

        Post post = postRepository.findById(id).orElse(null);

        if(post==null){
            throw new Exception("Post does not exist");
        }


        Reaction reaction = switch (reactionType) {
            case 0 -> Reaction.LIKE;
            case 1 -> Reaction.LOVE;
            case 2 -> Reaction.HAHA;
            case 3 -> Reaction.SAD;
            case 4 -> Reaction.ANGRY;
            default -> throw new IllegalArgumentException("Wrong number: " + reactionType);
        };

        if(postReactionRepository.existsByAuthorAndPost(user,post)){
            PostReaction dbReaction = postReactionRepository.findByAuthorAndPost(user,post);
            if(dbReaction.getReactionType() == reaction){
                post.setReactionCount(post.getReactionCount()-1);
                postReactionRepository.delete(dbReaction);
                postRepository.save(post);
            }else{
                dbReaction.setReactionType(reaction);
                postReactionRepository.save(dbReaction);
            }
        }else{
            PostReaction postReaction = PostReaction.builder()
                    .reactionType(reaction)
                    .author(user)
                    .post(post)
                    .build();
            if(post.getReactionCount()==null) post.setReactionCount(0L);
            post.setReactionCount(post.getReactionCount()+1);
            postReactionRepository.save(postReaction);
            postRepository.save(post);

            notificationService.createNotification(user,post.getAuthor(),"reactedToPost",post.getId());
        }

        return this.getReactionsCount(id);
    }

    @Override
    public PostReactionCountResponseDto getReactionsCount(Long postId) {
        Post post = postRepository.findById(postId).orElse(null);
        return PostReactionCountResponseDto.builder()
                        .overall(post.getReactionCount()==null ? 0L : post.getReactionCount())
                        .like(postReactionRepository.countByReactionTypeAndPost_Id(Reaction.LIKE,postId))
                        .love(postReactionRepository.countByReactionTypeAndPost_Id(Reaction.LOVE,postId))
                        .haha(postReactionRepository.countByReactionTypeAndPost_Id(Reaction.HAHA,postId))
                        .sad(postReactionRepository.countByReactionTypeAndPost_Id(Reaction.SAD,postId))
                        .angry( postReactionRepository.countByReactionTypeAndPost_Id(Reaction.ANGRY,postId))
                .build();
    }



    @Override
    public PostResponseDto getPostById(Long id) throws Exception {
        Post post = postRepository.findById(id).orElseThrow(()-> new Exception("Post Not Found"));
        return PostMapper.mapToPostResponseDto(post);
    }

    @Override
    public Page<PostResponseDto> getPostsByUserIdPaginate(Long id, int pageSize, int pageNumber) {
        Page<Post> postsPage = postRepository.findAllByAuthor_Id(id,PageRequest.of(pageNumber,
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

    @Override
    public Page<PostResponseDto> getFeedPostsPaginate(int pageSize, int pageNumber) {
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

    @Override
    public Page<PostResponseDto> getFriendsPostsPaginate(int pageSize, int pageNumber) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userRepository.findByUsername(username).orElse(null);

        List<Long> friendsIds = userService.getFriendList(user.getId())
                .stream()
                .map(UserResponseDto::getId)
                .collect(Collectors.toList());

        Page<Post> postsPage = postRepository.findAllByUserEntity_IdIn(friendsIds, PageRequest.of(pageNumber, pageSize));


        return new PageImpl<>(
                postsPage.getContent().stream()
                        .map(PostMapper::mapToPostResponseDto)
                        .collect(Collectors.toList()),
                postsPage.getPageable(),
                postsPage.getTotalElements()
        );
    }

    @Override
    public Integer checkUserReaction(Long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userRepository.findByUsername(username).orElse(null);

        Post post = postRepository.findById(id).orElse(null);

        if(postReactionRepository.existsByAuthorAndPost(user,post)){
            return postReactionRepository.findByAuthorAndPost(user,post).getReactionType().getValue();
        }else{
            return -1;
        }

    }

    @Override
    public Page<PostWithPhotoDto> getPostsWithPhotosPaginate(Long userId, int pageSize, int pageNumber) {
        UserEntity user = userRepository.findById(userId).orElseThrow();

        Page<Post> postsPage = postRepository.findAllByAuthorAndImageUrlIsNotNull(user,PageRequest.of(pageNumber,
                pageSize,
                Sort.by("createdAt").descending()));
        return new PageImpl<>(postsPage.getContent().stream().map(PostMapper::mapToPostWithPhotoDto).collect(Collectors.toList()),
                postsPage.getPageable(),
                postsPage.getTotalElements()
        );

    }


}
