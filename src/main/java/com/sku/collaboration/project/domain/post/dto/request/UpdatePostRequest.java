package com.sku.collaboration.project.domain.post.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
@Schema(title = "UpdatePostRequest: 게시글 수정 요청 DTO")
public class UpdatePostRequest {

  @NotBlank(message = "제목은 비어 있을 수 없습니다.")
  @Schema(description = "게시글 제목", example = "수정된 제목입니다")
  private String title;

  @NotBlank(message = "내용은 비어 있을 수 없습니다.")
  @Schema(description = "게시글 내용", example = "수정된 내용입니다")
  private String content;

  @Schema(description = "익명 여부", example = "true")
  private boolean isAnonymous;

}
