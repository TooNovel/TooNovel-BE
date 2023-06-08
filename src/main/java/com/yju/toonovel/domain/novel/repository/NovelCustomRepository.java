package com.yju.toonovel.domain.novel.repository;

import java.util.List;

import com.yju.toonovel.domain.novel.dto.NovelPaginationRequestDto;
import com.yju.toonovel.domain.novel.entity.Novel;

public interface NovelCustomRepository {
	List<Novel> findAllNovel(NovelPaginationRequestDto requestDto);

	List<Novel> findNovelsByNovelIdList(List<Long> novelIds);

	List<Novel> findNovelByAuthor(NovelPaginationRequestDto requestDto, Long userId);
}
