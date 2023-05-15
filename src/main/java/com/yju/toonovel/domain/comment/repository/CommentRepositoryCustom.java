package com.yju.toonovel.domain.comment.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.yju.toonovel.domain.comment.dto.CommentAllResponseDto;
import com.yju.toonovel.domain.comment.dto.CommentPaginationRequestDto;

public interface CommentRepositoryCustom {

	Page<CommentAllResponseDto> getAllComment(
		Long pid, Pageable pageable, CommentPaginationRequestDto requestDto
	);
}
