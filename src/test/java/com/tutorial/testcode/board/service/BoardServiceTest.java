package com.tutorial.testcode.board.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tutorial.testcode.board.domain.Board;
import com.tutorial.testcode.board.domain.BoardRepository;
import com.tutorial.testcode.board.exception.BoardErrorCode;
import com.tutorial.testcode.board.exception.BoardNotFondException;
import com.tutorial.testcode.board.service.dto.BoardResponse;
import com.tutorial.testcode.board.service.dto.CreateBoardRequest;

@DisplayName("Service:Board")
@ExtendWith(MockitoExtension.class)
class BoardServiceTest {

	@Mock
	private BoardRepository boardRepository;

	@InjectMocks
	private BoardService boardService;

	@Test
	@DisplayName("게시글 생성")
	void createBoard() {
		// Given
		CreateBoardRequest createBoardRequest = new CreateBoardRequest("제목", "내용");
		Board board = createBoardRequest.toEntity();

		BDDMockito.given(boardRepository.save(board))
			.willAnswer(AdditionalAnswers.returnsFirstArg());

		// When
		BoardResponse boardResponse = boardService.createBoard(createBoardRequest);

		// Then
		assertThat(boardResponse)
			.usingRecursiveComparison()
			.ignoringFields("id", "createdAt")
			.isEqualTo(board);

		verify(boardRepository, times(1)).save(board);
	}

	@Test
	@DisplayName("게시글 조회")
	void findById() {
		// Given
		UUID id = UUID.randomUUID();
		Board board = Board.of("제목", "내용");
		BDDMockito.given(boardRepository.findById(id)).willReturn(Optional.of(board));

		// When
		BoardResponse boardResponse = boardService.getBoardById(id);

		// Then
		assertThat(boardResponse).isNotNull();
		verify(boardRepository, times(1)).findById(any(UUID.class));
	}

	@Test
	@DisplayName("[예외]게시글이 존재하지 않는 경우")
	void throwException_whenBoardIsNotExist() {
		// Given
		UUID id = UUID.randomUUID();
		BDDMockito.given(boardRepository.findById(id))
			.willThrow(new BoardNotFondException(BoardErrorCode.NOT_EXIST, id));

		// When & Then
		assertThatExceptionOfType(BoardNotFondException.class)
			.isThrownBy(() -> boardService.getBoardById(id))
			.extracting(BoardNotFondException::getErrorCode)
			.isEqualTo(BoardErrorCode.NOT_EXIST);
		verify(boardRepository, times(1)).findById(any(UUID.class));
	}
}

