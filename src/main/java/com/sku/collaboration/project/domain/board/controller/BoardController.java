package com.sku.collaboration.project.domain.board.controller;

import com.sku.collaboration.project.domain.board.dto.request.BoardResponseDto;
import com.sku.collaboration.project.domain.board.service.BoardService;
import com.sku.collaboration.project.domain.post.dto.response.PostResponse;
import com.sku.collaboration.project.domain.post.service.PostService;
import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Board", description = "게시판 관련 API")
@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final PostService postService;

    @Operation(summary = "전체 게시판 목록 조회", description = "등록된 모든 게시판을 조회합니다.")
    @GetMapping
    public ResponseEntity<List<BoardResponseDto>> getAllBoards() {
        return ResponseEntity.ok(boardService.getAllBoards());
    }

    @Operation(summary = "특정 게시판 게시글 전체 조회", description = "boardId에 해당하는 게시판의 게시글을 모두 조회합니다.")
    @GetMapping("/{boardId}/posts")
    public ResponseEntity<List<PostResponse>> getPostsByBoard(@PathVariable Long boardId) {
        return ResponseEntity.ok(postService.getPostsByBoard(boardId));
    }
}