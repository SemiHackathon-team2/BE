package com.sku.collaboration.project.domain.board.dto.request;

import com.sku.collaboration.project.domain.board.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BoardResponseDto {
    private Long id;
    private String name;
    private String description;

    public static BoardResponseDto from(Board board) {
        return new BoardResponseDto(board.getId(), board.getName(), board.getDescription());
    }
}