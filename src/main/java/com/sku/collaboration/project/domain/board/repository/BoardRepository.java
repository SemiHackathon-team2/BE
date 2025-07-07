package com.sku.collaboration.project.domain.board.repository;

import com.sku.collaboration.project.domain.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}