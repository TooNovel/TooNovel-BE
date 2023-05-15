package com.yju.toonovel.domain.review.repository;

import static com.yju.toonovel.domain.novel.entity.QNovel.*;
import static com.yju.toonovel.domain.review.entity.QReview.*;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yju.toonovel.domain.novel.entity.Genre;
import com.yju.toonovel.domain.review.dto.ReviewAllByUserDto;
import com.yju.toonovel.domain.review.dto.ReviewPaginationRequestDto;
import com.yju.toonovel.domain.review.entity.QReview;
import com.yju.toonovel.global.common.Sort;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ReviewRepositoryImpl implements ReviewRepositoryCustom {
	private final JPAQueryFactory queryFactory;

	//리뷰 조회 카운트 쿼리 메서드
	public Long countQuery(QReview review) {
		return queryFactory
			.select(review.count())
			.from(review)
			.fetchOne();
	}

	//조회에 필요한 데이터들만 조회에 dto에 매핑
	public QBean<ReviewAllByUserDto> reviewSelect(QReview review) {
		return Projections.fields(
			ReviewAllByUserDto.class,
			review.novel.image, review.novel.genre, review.novel.author, review.novel.description,
			review.novel.title,
			review.reviewContent, review.reviewGrade, review.createdDate, review.reviewLike,
			review.writer.nickname, review.writer.imageUrl
		);
	}

	//정렬 기준 지정
	private OrderSpecifier getOrderSpecifiers(Sort sort) {
		for (Sort value : Sort.values()) {
			if (sort == value) {
				Path<Object> path = Expressions.path(Object.class, review, value.getProperty());
				return new OrderSpecifier(value.getOrder(), path);
			}
		}
		return null;
	}

	private Predicate eqGenre(Genre genre) {
		if (genre == null) {
			return null;
		}
		return novel.genre.eq(genre);
	}

	//유저가 작성한 리뷰조회
	@Override
	public Page<ReviewAllByUserDto> findAllReviewByUser(Long uid, Pageable pageable,
		ReviewPaginationRequestDto requestDto) {
		QReview review = QReview.review;

		JPAQuery<ReviewAllByUserDto> results = queryFactory
			.select(reviewSelect(review))
			.from(review)
			.where(review.writer.userId.eq(uid))
			.orderBy(getOrderSpecifiers(requestDto.getSort()))
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize());

		List<ReviewAllByUserDto> reviews = results.fetch();

		return new PageImpl<>(reviews, pageable, countQuery(review));
	}

	//전체리뷰조회
	@Override
	public Page<ReviewAllByUserDto> getAllReview(Pageable pageable, ReviewPaginationRequestDto requestDto) {
		QReview review = QReview.review;

		JPQLQuery<ReviewAllByUserDto> results = queryFactory
			.select(reviewSelect(review))
			.from(review)
			.where(eqGenre(requestDto.getGenre()))
			.orderBy(getOrderSpecifiers(requestDto.getSort()))
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize());

		List<ReviewAllByUserDto> reviews = results.fetch();

		return new PageImpl<>(reviews, pageable, countQuery(review));
	}
}
