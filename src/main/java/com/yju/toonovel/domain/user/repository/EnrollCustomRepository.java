package com.yju.toonovel.domain.user.repository;

import java.util.List;

import com.yju.toonovel.domain.user.dto.AuthorListPaginationRequestDto;
import com.yju.toonovel.domain.user.dto.AuthorListResponseDto;

public interface EnrollCustomRepository {
	List<AuthorListResponseDto> findAllAuthor(AuthorListPaginationRequestDto dto);
}
