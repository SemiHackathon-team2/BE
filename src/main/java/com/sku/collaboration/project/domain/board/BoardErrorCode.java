package com.sku.collaboration.project.domain.board;

import com.sku.collaboration.project.global.exception.model.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum BoardErrorCode implements BaseErrorCode {
  BOARD_NOT_FOUND("BOARD_4041", "해당 게시판을 찾을 수 없습니다.", HttpStatus.NOT_FOUND);

  private final String code;
  private final String message;
  private final HttpStatus status;
}
