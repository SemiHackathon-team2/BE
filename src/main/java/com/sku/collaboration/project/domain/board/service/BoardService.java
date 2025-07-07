package com.sku.collaboration.project.domain.board.service;

import com.sku.collaboration.project.domain.board.repository.BoardRepository;
import com.sku.collaboration.project.domain.board.dto.request.BoardResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public List<BoardResponseDto> getAllBoards() {
        return boardRepository.findAll().stream()
                .map(BoardResponseDto::from)
                .collect(Collectors.toList());
    }
}