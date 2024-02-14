package com.mt.mtSocialMedia.service.serviceImpl;

import com.mt.mtSocialMedia.dto.Comment.CommentResponseDto;
import com.mt.mtSocialMedia.mapper.CommentMapper;
import com.mt.mtSocialMedia.model.Comment;
import com.mt.mtSocialMedia.model.UserEntity;
import com.mt.mtSocialMedia.repository.CommentRepository;
import com.mt.mtSocialMedia.repository.PostRepository;
import com.mt.mtSocialMedia.repository.UserRepository;
import com.mt.mtSocialMedia.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Override
    public Page<CommentResponseDto> getCommentsByPostId(Long postId,int pageSize, int pageNumber, String sortBy) {
        Sort sort = Sort.by("createdAt").descending();
        if(Objects.equals(sortBy, "dateAsc")){
            sort = Sort.by("createdAt").ascending();
        }else if(sortBy == "reactionAsc"){
            sort = Sort.by("reactionCount").ascending();
        } else if (sortBy == "reactionDesc") {
            sort = Sort.by("reactionCountAsc").descending();
        }

        Page<Comment> commentPage = commentRepository.findAllByPost_Id(postId,PageRequest.of(pageNumber,pageSize,sort));

       return new PageImpl<>(
               commentPage
                       .stream()
                       .map(CommentMapper::mapToCommentResponseDto)
                       .collect(Collectors.toList()),PageRequest.of(pageNumber,pageSize),
               commentPage.getTotalElements()
       );
    }

    @Override
    public String addComment(Long postId, String commentContent) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userRepository.findByUsername(username).orElse(null);
        Comment comment = new Comment();
        comment.setAuthor(user);
        comment.setContent(commentContent);
        comment.setPost(postRepository.findById(postId).orElse(null));
        commentRepository.save(comment);
        return "Comment Added";
    }

}


