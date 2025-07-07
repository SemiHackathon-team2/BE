package com.sku.collaboration.project.domain.post.exception;

import com.sku.collaboration.project.global.exception.model.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum PostErrorCode implements BaseErrorCode {
  POST_NOT_FOUND("POST_4041", "해당 게시글을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
  INVALID_POST_TITLE("POST_4001", "게시글 제목은 필수입니다.", HttpStatus.BAD_REQUEST),
  INVALID_POST_CONTENT("POST_4002", "게시글 내용은 필수입니다.", HttpStatus.BAD_REQUEST),
  TITLE_TOO_LONG("POST_4003", "게시글 제목은 10자 이하로 작성해야 합니다.", HttpStatus.BAD_REQUEST);

  private final String code;
  private final String message;
  private final HttpStatus status;
}
