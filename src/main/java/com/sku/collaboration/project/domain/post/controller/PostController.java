package com.sku.collaboration.project.domain.post.controller;

import com.sku.collaboration.project.domain.post.dto.request.CreatePostRequest;
import com.sku.collaboration.project.domain.post.dto.response.PostResponse;
import com.sku.collaboration.project.domain.post.service.PostService;
import com.sku.collaboration.project.global.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//사용자의 요청을 받아서, 그에 맞는 서비스 로직을 호출하고, 결과를 응답으로 반환하는 역할!!
@RestController  //API요청을 처리하는 컨트롤러임을 명시!
@RequiredArgsConstructor
@RequestMapping("/api")  //이 클래스에서 모든 API경로 앞에 공통적으로 붙을 주소 설정!!
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

}
