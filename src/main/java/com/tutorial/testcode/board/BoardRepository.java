package com.tutorial.testcode.board;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tutorial.testcode.board.domain.Board;

public interface BoardRepository extends JpaRepository<Board, UUID> {
}
