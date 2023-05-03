package com.yju.toonovel.domain.novel.repository;

import static com.yju.toonovel.domain.novel.entity.QNovel.*;
import static com.yju.toonovel.domain.novel.entity.QNovelPlatform.*;
import static com.yju.toonovel.domain.novel.entity.QPlatform.*;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yju.toonovel.domain.novel.dto.NovelDetailResponseDto;
import com.yju.toonovel.domain.novel.entity.Novel;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class NovelCustomRepositoryImpl implements NovelCustomRepository {

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public List<Novel> findAllNovel(Long novelId) {
		return jpaQueryFactory
			.selectFrom(novel)
			.where(
				gtNovelId(novelId)
			)
			.limit(30)
			.fetch();
	}

	private BooleanExpression gtNovelId(Long novelId) {
		if (novelId == null) {
			return null;
		}

		return novel.novelId.gt(novelId);
	}

}
