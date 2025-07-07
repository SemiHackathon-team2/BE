package com.sku.collaboration.project.domain.comment.controller;

import com.sku.collaboration.project.domain.comment.dto.request.CreateCommentRequest;
import com.sku.collaboration.project.domain.comment.dto.response.CommentResponse;
import com.sku.collaboration.project.domain.comment.service.CommentService;
import com.sku.collaboration.project.global.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts/{postId}/comments")
@RequiredArgsConstructor
@Tag(name = "Comment", description = "댓글 관련 API")
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "댓글 등록")
    @PostMapping
    public ResponseEntity<BaseResponse<CommentResponse>> createComment(
            @PathVariable Long postId,
            @RequestBody CreateCommentRequest request) {
        return ResponseEntity.ok(
                BaseResponse.success("댓글 등록 성공", commentService.createComment(postId, request)));
    }

    @Operation(summary = "댓글 전체 조회")
    @GetMapping
    public ResponseEntity<BaseResponse<List<CommentResponse>>> getComments(@PathVariable Long postId) {
        return ResponseEntity.ok(
                BaseResponse.success("댓글 조회 성공", commentService.getCommentsByPost(postId)));
    }

    @Operation(summary = "댓글 삭제")
    @DeleteMapping("/{commentId}")
    public ResponseEntity<BaseResponse<Void>> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.ok(BaseResponse.success("댓글 삭제 성공", null));
    }
}