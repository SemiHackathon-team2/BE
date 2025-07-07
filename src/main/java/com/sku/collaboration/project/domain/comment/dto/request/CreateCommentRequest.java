package com.sku.collaboration.project.domain.comment.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateCommentRequest {
    private String content;
    private Long userId;  // 댓글 작성자

    // 필요시 생성자 추가
    public CreateCommentRequest(String content, Long userId) {
        this.content = content;
        this.userId = userId;
    }
}