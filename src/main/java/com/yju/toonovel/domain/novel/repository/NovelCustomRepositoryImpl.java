package com.yju.toonovel.domain.novel.repository;

import static com.yju.toonovel.domain.novel.entity.QNovel.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yju.toonovel.domain.novel.dto.NovelPaginationRequestDto;
import com.yju.toonovel.domain.novel.entity.Genre;
import com.yju.toonovel.domain.novel.entity.Novel;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class NovelCustomRepositoryImpl implements NovelCustomRepository {

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public List<Novel> findAllNovel(NovelPaginationRequestDto requestDto) {
		return jpaQueryFactory
			.selectFrom(novel)
			.where(
				ltNovelId(requestDto.getNovelId()),
				eqGenre(requestDto.getGenre()),
				eqTitle(requestDto.getTitle()),
				eqAuthor(requestDto.getAuthor())
			)
			.limit(30)
			.orderBy(
				novel.novelId.desc()
			)
			.fetch();
	}

	private BooleanExpression ltNovelId(Long novelId) {
		if (novelId == null) {
			return null;
		}

		return novel.novelId.lt(novelId);
	}

	private Predicate eqGenre(Genre genre) {
		if (genre == null) {
			return null;
		}
		return novel.genre.eq(genre);
	}

	private Predicate eqTitle(String title) {
		if (title == null) {
			return null;
		}
		return novel.title.contains(title);
	}

	private Predicate eqAuthor(String author) {
		if (author == null) {
			return null;
		}
		return novel.author.contains(author);
	}

}
