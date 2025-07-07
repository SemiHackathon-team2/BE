package com.sku.collaboration.project.domain.post.dto.response;

import com.sku.collaboration.project.domain.post.entity.Post;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(title = "PostResponse: 게시글 응답 DTO")
public class PostResponse {

  @Schema(description = "게시글 ID", example = "1")
  private Long postId;

  @Schema(description = "게시글 제목", example = "5주차 세션")
  private String title;

  @Schema(description = "게시글 내용", example = "이번주 세션도 화이팅!")
  private String content;

  @Schema(description = "작성자 이름", example = "익명")
  private String writerName; //익명 여부에 따라 결정

  public static PostResponse of(Post post) {
    String nickname = post.getIsAnonymous() ? "익명" : post.getUser().getName();
    return new PostResponse(
        post.getId(),
        post.getTitle(),
        post.getContent(),
        nickname
    );
  }
}
