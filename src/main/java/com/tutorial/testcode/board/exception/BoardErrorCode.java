package com.tutorial.testcode.board.exception;

import lombok.Getter;

@Getter
public enum BoardErrorCode {
	NOT_EXIST("요청에 해당하는 게시글을 찾을 수 없습니다. req : [%s]");
	private final String message;

	BoardErrorCode(String message) {
		this.message = message;
	}
}
