package com.mt.mtSocialMedia.service.serviceImpl;

import com.mt.mtSocialMedia.dto.Comment.CommentReactionCountResponseDto;
import com.mt.mtSocialMedia.dto.Comment.CommentResponseDto;
import com.mt.mtSocialMedia.dto.Post.PostReactionCountResponseDto;
import com.mt.mtSocialMedia.enums.Reaction;
import com.mt.mtSocialMedia.mapper.CommentMapper;
import com.mt.mtSocialMedia.model.Comment;
import com.mt.mtSocialMedia.model.CommentReaction;
import com.mt.mtSocialMedia.model.Post;
import com.mt.mtSocialMedia.model.UserEntity;
import com.mt.mtSocialMedia.repository.CommentReactionRepository;
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
    private final CommentReactionRepository commentReactionRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Override
    public Page<CommentResponseDto> getCommentsByPostId(Long postId,int pageSize, int pageNumber, String sortBy) {
        Sort sort = Sort.by("createdAt").descending();
        if(Objects.equals(sortBy, "dateAsc")){
            sort = Sort.by("createdAt").ascending();
        }else if(Objects.equals(sortBy, "reactionAsc")){
            sort = Sort.by("reactionCount").ascending();
        } else if (Objects.equals(sortBy, "reactionDesc")) {
            sort = Sort.by("reactionCount").descending();
        }

        Page<Comment> commentPage = commentRepository.findAllByPost_Id(postId,PageRequest.of(pageNumber,pageSize,sort));

       return new PageImpl<>(
               commentPage
                       .stream()
                       .map(CommentMapper::mapToCommentResponseDto)
                       .collect(Collectors.toList()),PageRequest.of(pageNumber,pageSize,sort),
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
        Post post = postRepository.findById(postId).orElse(null);

        if (post.getCommentCount() == null) {
            post.setCommentCount(1L);
        } else {
            post.setCommentCount(post.getCommentCount() + 1);
        }

        comment.setPost(post);

        postRepository.save(post);
        commentRepository.save(comment);
        return "Comment Added";
    }

    @Override
    public CommentReactionCountResponseDto reactToComment(Long id, int reactionType) throws Exception {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userRepository.findByUsername(username).orElse(null);

        Comment comment = commentRepository.findById(id).orElse(null);

        if(comment==null){
            throw new Exception("Comment does not exist");
        }

        Reaction reaction = switch (reactionType) {
            case 0 -> Reaction.LIKE;
            case 1 -> Reaction.LOVE;
            case 2 -> Reaction.HAHA;
            case 3 -> Reaction.SAD;
            case 4 -> Reaction.ANGRY;
            default -> throw new IllegalArgumentException("Wrong number: " + reactionType);
        };

        if(commentReactionRepository.existsByAuthorAndComment(user,comment)){
            CommentReaction dbReaction = commentReactionRepository.findByAuthorAndComment(user,comment);

            if(reaction == dbReaction.getReactionType()){
                comment.setReactionCount(comment.getReactionCount()-1);
                commentReactionRepository.delete(dbReaction);
                commentRepository.save(comment);
            }else{
                dbReaction.setReactionType(reaction);
                commentReactionRepository.save(dbReaction);
            }

        }else{
            CommentReaction commentReaction = CommentReaction.builder()
                    .reactionType(reaction)
                    .author(user)
                    .comment(comment)
                    .build();
            if(comment.getReactionCount()==null) comment.setReactionCount(0L);
            comment.setReactionCount(comment.getReactionCount()+1);

            commentReactionRepository.save(commentReaction);
            commentRepository.save(comment);
        }
        return this.getReactionsCount(id);
    }

    @Override
    public CommentReactionCountResponseDto getReactionsCount(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElse(null);
        return CommentReactionCountResponseDto.builder()
                .overall(comment.getReactionCount()==null ? 0L : comment.getReactionCount())
                .like(commentReactionRepository.countByReactionTypeAndComment_Id(Reaction.LIKE,commentId))
                .love(commentReactionRepository.countByReactionTypeAndComment_Id(Reaction.LOVE,commentId))
                .haha(commentReactionRepository.countByReactionTypeAndComment_Id(Reaction.HAHA,commentId))
                .sad(commentReactionRepository.countByReactionTypeAndComment_Id(Reaction.SAD,commentId))
                .angry( commentReactionRepository.countByReactionTypeAndComment_Id(Reaction.ANGRY,commentId))
                .build();
    }

    @Override
    public CommentResponseDto getBestComment(Long postId) {
        Comment comment = commentRepository.findTopByPost_IdOrderByReactionCountDesc(postId);
        if(comment==null) return CommentResponseDto.builder().build();

        return CommentMapper.mapToCommentResponseDto(comment);

    }

    @Override
    public String deleteComment(Long id) {
        Comment comment = commentRepository.findById(id).orElse(null);
        if(comment == null) return "Comment does not exist";

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userRepository.findByUsername(username).orElse(null);

        if(user==comment.getAuthor()){
            Post post = comment.getPost();
            commentRepository.delete(comment);
            post.setCommentCount(post.getCommentCount()-1);
            postRepository.save(post);
            return "success";
        }else{
            return "Unauthorized!";
        }

    }


}


