package com.sku.collaboration.project.domain.post.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
@Schema(title = "CreatePostRequest: 게시글 생성 요청 DTO") //스웨서 문서화를 위한 어노테이션!
public class CreatePostRequest {

  @NotBlank(message = "제목은 비어 있을 수 없습니다.")
  @Schema(description = "게시글 제목", example = "멋사 세미해커톤 개최")
  private String title;

  @NotBlank(message = "내용은 비어 있을 수 없습니다.")
  @Schema(description = "게시글 내용", example = "내용내용내용내용")
  private String content;

  @Schema(description = "게시판 ID", example = "1")
  private Long boardId; // 만약 서비스에서 필요하다면

  @Schema(description = "게시글 내용", example = "내용내용내용내용")
  private boolean isAnonymous; // 익명 여부 추가

  @Schema(description = "게시글 작성자 ID", example = "1")
  private Long userId; // 사용자 ID (임시용)

}
