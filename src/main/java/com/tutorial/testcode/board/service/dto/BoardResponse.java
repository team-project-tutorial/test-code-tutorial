package com.tutorial.testcode.board.service.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.tutorial.testcode.board.domain.Board;

public record BoardResponse(
	UUID id,
	String title,
	String content,
	LocalDateTime createdAt,
	LocalDateTime updatedAt
) {
	public static BoardResponse of(Board board) {
		return new BoardResponse(
			board.getId(),
			board.getTitle(),
			board.getContent(),
			board.getCreatedAt(),
			board.getUpdatedAt()
		);
	}
}
