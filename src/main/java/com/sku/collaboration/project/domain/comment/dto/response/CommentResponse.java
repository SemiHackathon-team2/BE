package com.sku.collaboration.project.domain.comment.dto.response;

import com.sku.collaboration.project.domain.comment.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentResponse {
    private Long commentId;
    private String content;
    private String nickname;  // 작성자 이름 또는 닉네임

    public static CommentResponse from(Comment comment) {
        return new CommentResponse(
                comment.getId(),
                comment.getContent(),
                comment.getUser().getUsername()
        );
    }
}