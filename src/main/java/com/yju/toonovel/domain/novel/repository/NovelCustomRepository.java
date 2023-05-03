package com.yju.toonovel.domain.novel.repository;

import java.util.List;
import java.util.Optional;

import com.yju.toonovel.domain.novel.dto.NovelDetailResponseDto;
import com.yju.toonovel.domain.novel.entity.Novel;

public interface NovelCustomRepository {
	List<Novel> findAllNovel(Long novelId);
}
