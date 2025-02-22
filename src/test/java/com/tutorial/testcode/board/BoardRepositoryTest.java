package com.tutorial.testcode.board;

import static org.assertj.core.api.Assertions.*;

import java.util.NoSuchElementException;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import com.tutorial.testcode.board.config.JpaConfig;
import com.tutorial.testcode.board.domain.Board;

@Import(JpaConfig.class)
@DataJpaTest
@ActiveProfiles("test")
class BoardRepositoryTest {
	@Autowired
	private BoardRepository boardRepository;

	@Test
	@DisplayName("게시글 Entity 저장")
	void save() {
		// Given
		Board board = Board.of("제목", "내용");

		// When
		boardRepository.save(board);

		// Then
		assertThat(board.getId()).isNotNull();
		assertThat(board.getCreatedAt()).isNotNull();
		assertThat(board.getUpdatedAt()).isNotNull();
	}

	@Test
	@DisplayName("특정 게시글 조회 TC 작성")
	void findById() {
		// Given
		Board board = Board.of("제목", "내용");
		boardRepository.save(board);

		// When
		Board findBoard = boardRepository.findById(board.getId()).orElseThrow();

		// Then
		assertThat(findBoard.getId()).isEqualTo(board.getId());
	}

	@Test
	@DisplayName("[예외]존재 하지 않는 게시글 조회")
	void throwException_whenBoardIsNotExist() {
		// Given
		UUID notExistBoardId = UUID.randomUUID();

		// When & Then
		assertThatExceptionOfType(NoSuchElementException.class)
			.isThrownBy(() -> boardRepository.findById(notExistBoardId)
				.orElseThrow()
			);
	}
}
