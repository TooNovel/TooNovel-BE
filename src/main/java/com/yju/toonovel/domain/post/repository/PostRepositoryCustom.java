package com.yju.toonovel.domain.post.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.yju.toonovel.domain.post.dto.PostAllResponseDto;
import com.yju.toonovel.domain.post.dto.PostPaginationRequestDto;

public interface PostRepositoryCustom {

	Page<PostAllResponseDto> findAllPost(
		Pageable pageable, PostPaginationRequestDto requestDto
	);
}
