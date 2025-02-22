package com.tutorial.testcode.board.exception;

import java.util.UUID;

import lombok.Getter;

@Getter
public class BoardNotFondException extends RuntimeException {
	private final BoardErrorCode errorCode;

	public BoardNotFondException(BoardErrorCode errorCode, UUID id) {
		super(errorCode.getMessage().formatted(id));
		this.errorCode = errorCode;
	}
}
