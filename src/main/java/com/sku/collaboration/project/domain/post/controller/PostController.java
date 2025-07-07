package com.sku.collaboration.project.domain.post.controller;

import com.sku.collaboration.project.domain.post.dto.request.CreatePostRequest;
import com.sku.collaboration.project.domain.post.dto.request.UpdatePostRequest;
import com.sku.collaboration.project.domain.post.dto.response.PostResponse;
import com.sku.collaboration.project.domain.post.service.PostService;
import com.sku.collaboration.project.global.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController  //API요청을 처리하는 컨트롤러임을 명시!
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "Post", description = "게시글 관련 API")
public class PostController {

  private final PostService postService;

  @Operation(summary = "게시글 생성", description = "게시판 페이지에서 게시글 작성 후 생성 버튼을 눌렀을 때 요청되는 API")
  @PostMapping("/boards/{boardId}/posts")
  public ResponseEntity<BaseResponse<PostResponse>> createPost(
      @Parameter(description = "게시글 작성 내용")  //Swagger에서 파라미터의 의미를 설명함! 실제 동작엔 관계X
      @RequestBody @Valid CreatePostRequest createPostRequest) { //@RequestBody : JSON -> java객체로 변환!
    PostResponse response = postService.createPost(createPostRequest);
    return ResponseEntity.ok(
        BaseResponse.success("게시글 생성 성공", response)); //비지니스 로직 처리하는 PosstService의 메서드를 호출함!
  }

  @Operation(summary = "게시글 단일 조회", description = "게시글 상세 페이지 진입 시 단일 게시글을 조회합니다.")
  @GetMapping("/posts/{postId}")
  public ResponseEntity<BaseResponse<PostResponse>> getPost(
      @Parameter(description = "조회할 게시글 ID") @PathVariable Long postId) {

    PostResponse response = postService.getPost(postId);
    return ResponseEntity.ok(BaseResponse.success("게시글 조회 성공", response));
  }


  @Operation(summary = "게시글 수정", description = "게시글 수정 페이지에서 저장 버튼을 눌렀을 때 요청되는 API")
  @PatchMapping("/posts/{postId}")
  public ResponseEntity<BaseResponse<PostResponse>> updatePost(
      @PathVariable Long postId,
      @RequestBody @Valid UpdatePostRequest updatePostRequest) {

    PostResponse response = postService.updatePost(postId, updatePostRequest);
    return ResponseEntity.ok(BaseResponse.success("게시글 수정 성공", response));
  }

  @Operation(summary = "게시글 삭제", description = "게시글 상세 페이지에서 삭제 버튼을 눌렀을 때 요청되는 API")
  @DeleteMapping("/posts/{postId}")
  public ResponseEntity<BaseResponse<Void>> deletePost(
      @Parameter(description = "삭제할 게시글 ID") @PathVariable Long postId) {

    postService.deletePost(postId);
    return ResponseEntity.ok(BaseResponse.success("게시글 삭제 성공", null));
  }

  @Operation(summary = "게시글 요약", description = "GPT를 통해 게시글 내용을 요약합니다.")
  @PostMapping("/posts/{postId}/summary")
  public ResponseEntity<BaseResponse<String>> summarizePost(@PathVariable Long postId) {
    String summary = postService.summarizePost(postId);
    return ResponseEntity.ok(BaseResponse.success("요약 성공", summary));
  }

  @Operation(summary = "댓글 반응 요약", description = "GPT를 이용하여 댓글들의 전반적인 반응을 요약해줍니다.")
  @GetMapping("/posts/{postId}/comments/summary")
  public ResponseEntity<BaseResponse<String>> summarizeComments(@PathVariable Long postId) {
    String summary = postService.summarizeCommentsForPost(postId);
    return ResponseEntity.ok(BaseResponse.success("댓글 요약 성공", summary));
  }



}
