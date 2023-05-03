package com.yju.toonovel.domain.novel.repository;

import static com.yju.toonovel.domain.novel.entity.QLikeNovel.*;
import static com.yju.toonovel.domain.novel.entity.QNovel.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yju.toonovel.domain.novel.entity.LikeNovel;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class LikeNovelCustomRepositoryImpl implements LikeNovelCustomRepository {

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public List<LikeNovel> findAllUserLikeNovel(Long userId, Long likeNovelId) {
		return jpaQueryFactory
			.selectFrom(likeNovel)
			.join(likeNovel.novel, novel)
			.fetchJoin()
			.where(likeNovel.user.userId.eq(userId)
				.and(likeNovel.isActived.eq(true)))
			.where(
				gtLikeNovelId(likeNovelId)
			)
			.limit(10)
			.fetch();
	}

	private BooleanExpression gtLikeNovelId(Long likeNovelId) {
		if (likeNovelId == null) {
			return null;
		}

		return likeNovel.likeNovelId.gt(likeNovelId);
	}
}
