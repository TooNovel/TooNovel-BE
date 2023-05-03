package com.yju.toonovel.domain.novel.repository;

import java.util.List;

import com.yju.toonovel.domain.novel.entity.LikeNovel;

public interface LikeNovelCustomRepository {
	List<LikeNovel> findAllUserLikeNovel(Long userId, Long likeNovelId);
}
