package com.sku.collaboration.project.domain.comment.service;

import com.sku.collaboration.project.domain.comment.dto.request.CreateCommentRequest;
import com.sku.collaboration.project.domain.comment.dto.response.CommentResponse;
import com.sku.collaboration.project.domain.comment.entity.Comment;
import com.sku.collaboration.project.domain.comment.repository.CommentRepository;
import com.sku.collaboration.project.domain.post.entity.Post;
import com.sku.collaboration.project.domain.post.exception.PostErrorCode;
import com.sku.collaboration.project.domain.post.repository.PostRepository;
import com.sku.collaboration.project.domain.user.entity.User;
import com.sku.collaboration.project.domain.user.exception.UserErrorCode;
import com.sku.collaboration.project.domain.user.repository.UserRepository;
import com.sku.collaboration.project.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public CommentResponse createComment(Long postId, CreateCommentRequest request) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(PostErrorCode.POST_NOT_FOUND));
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

        Comment comment = Comment.builder()
                .content(request.getContent())
                .post(post)
                .user(user)
                .build();

        return CommentResponse.from(commentRepository.save(comment));
    }

    @Transactional(readOnly = true)
    public List<CommentResponse> getCommentsByPost(Long postId) {
        return commentRepository.findByPostId(postId).stream()
                .map(CommentResponse::from)
                .toList();
    }

    @Transactional
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    @Transactional(readOnly = true)
    public List<String> getCommentContentsByPost(Long postId) {
        return commentRepository.findByPostId(postId).stream()
            .map(Comment::getContent)
            .toList();
    }
}