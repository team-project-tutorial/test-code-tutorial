package com.tutorial.testcode.board;

import static org.assertj.core.api.Assertions.*;

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
}
