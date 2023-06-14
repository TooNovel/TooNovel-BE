package com.yju.toonovel.domain.user.repository;

import java.util.List;

import com.yju.toonovel.domain.user.dto.AuthorListResponseDto;
import com.yju.toonovel.global.common.Sort;

public interface EnrollCustomRepository {
	List<AuthorListResponseDto> findAllAuthor(Long enrollId, Integer limit, Sort sort);
}
