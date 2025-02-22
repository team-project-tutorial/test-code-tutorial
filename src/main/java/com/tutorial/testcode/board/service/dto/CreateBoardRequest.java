package com.tutorial.testcode.board.service.dto;

import com.tutorial.testcode.board.domain.Board;

public record CreateBoardRequest(
	String title,
	String content
) {
	public Board toEntity() {
		return Board.of(title, content);
	}
}
