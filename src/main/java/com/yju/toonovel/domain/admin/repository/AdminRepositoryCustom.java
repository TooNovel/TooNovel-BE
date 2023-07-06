package com.yju.toonovel.domain.admin.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.yju.toonovel.domain.admin.dto.EnrollListPaginationRequestDto;
import com.yju.toonovel.domain.admin.dto.EnrollListResponseDto;

public interface AdminRepositoryCustom {

	Page<EnrollListResponseDto> findAllEnrollList(EnrollListPaginationRequestDto dto, Pageable pageable);
}
