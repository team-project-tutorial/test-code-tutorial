package com.tutorial.testcode.board.service;

import static com.tutorial.testcode.board.exception.BoardErrorCode.*;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tutorial.testcode.board.domain.Board;
import com.tutorial.testcode.board.domain.BoardRepository;
import com.tutorial.testcode.board.exception.BoardNotFondException;
import com.tutorial.testcode.board.service.dto.BoardResponse;
import com.tutorial.testcode.board.service.dto.CreateBoardRequest;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService {
	private final BoardRepository boardRepository;

	@Transactional
	public BoardResponse createBoard(CreateBoardRequest createdBoardRequest) {
		Board board = createdBoardRequest.toEntity();
		boardRepository.save(board);

		return BoardResponse.of(board);
	}

	public BoardResponse getBoardById(UUID id) {
		return boardRepository.findById(id)
			.map(BoardResponse::of)
			.orElseThrow(() -> new BoardNotFondException(NOT_EXIST, id));
	}
}
