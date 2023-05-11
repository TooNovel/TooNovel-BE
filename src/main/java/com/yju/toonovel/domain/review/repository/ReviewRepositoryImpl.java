package com.yju.toonovel.domain.review.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yju.toonovel.domain.review.dto.ReviewAllByUserDto;
import com.yju.toonovel.domain.review.entity.QReview;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepositoryCustom {
	private final JPAQueryFactory queryFactory;

	//리뷰 조회 카운트 쿼리 메서드
	public Long countQuery(QReview review) {
		return queryFactory
			.select(review.count())
			.from(review)
			.fetchOne();
	}

	public QBean<ReviewAllByUserDto> reviewSelect(QReview review) {
		return Projections.fields(
			ReviewAllByUserDto.class,
			review.novel.image, review.novel.genre, review.novel.author, review.novel.description,
			review.novel.title,
			review.reviewContent, review.reviewGrade, review.createdDate, review.reviewLike,
			review.writer.nickname, review.writer.imageUrl
		);
	}

	//유저가 작성한 리뷰조회
	@Override
	public Page<ReviewAllByUserDto> findAllReviewByUser(Long uid, Pageable pageable) {
		QReview review = QReview.review;

		JPAQuery<ReviewAllByUserDto> results = queryFactory
			.select(reviewSelect(review))
			.from(review)
			.where(review.writer.userId.eq(uid))
			.orderBy(review.createdDate.desc())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize());

		List<ReviewAllByUserDto> reviews = results.fetch();

		return new PageImpl<>(reviews, pageable, countQuery(review));
	}

	//전체리뷰조회
	@Override
	public Page<ReviewAllByUserDto> getAllReview(Pageable pageable) {
		QReview review = QReview.review;

		JPQLQuery<ReviewAllByUserDto> results = queryFactory
			.select(reviewSelect(review))
			.from(review)
			.orderBy(review.createdDate.desc())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize());

		List<ReviewAllByUserDto> reviews = results.fetch();

		return new PageImpl<>(reviews, pageable, countQuery(review));
	}

	//장르별 전체리뷰조회
	// @Override
	// public Page<ReviewAllByUserDto> getAllReviewWhereGenre(String genre, Pageable pageable) {
	// 	QReview review = QReview.review;
	//
	// 	JPQLQuery<ReviewAllByUserDto> results = queryFactory
	// 		.select(reviewSelect(review))
	// 		.from(review);
	//
	// 	if(genre!=null) {
	// 		results
	// 			.where(review.novel.genre.eq(Genre.valueOf(genre.toUpperCase())))
	// 			.orderBy(review.createdDate.desc())
	// 			.offset(pageable.getOffset())
	// 			.limit(pageable.getPageSize());
	// 	} else {
	// 		results
	// 			.orderBy(review.createdDate.desc())
	// 			.offset(pageable.getOffset())
	// 			.limit(pageable.getPageSize());
	// 	}
	//
	// 	List<ReviewAllByUserDto> reviews = results.fetch();
	//
	// 	return new PageImpl<>(reviews, pageable, countQuery(review));
	// }

	//전체리뷰조회 - 좋아요순 정렬
	@Override
	public Page<ReviewAllByUserDto> getAllReviewOrderByLike(Pageable pageable) {
		QReview review = QReview.review;

		JPQLQuery<ReviewAllByUserDto> results = queryFactory
			.select(reviewSelect(review))
			.from(review)
			.orderBy(review.reviewLike.desc())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize());

		List<ReviewAllByUserDto> reviews = results.fetch();

		return new PageImpl<>(reviews, pageable, countQuery(review));
	}

	//장르별 전체리뷰조회 - 좋아요순 정렬
	// @Override
	// public Page<ReviewAllByUserDto> getAllReviewOrderByLikeWhereGenre(String genre, Pageable pageable) {
	//
	// 	QReview review = QReview.review;
	//
	// 	JPQLQuery<ReviewAllByUserDto> results = queryFactory
	// 		.select(reviewSelect(review))
	// 		.from(review)
	// 		.fetchJoin()
	// 		.where(review.novel.genre.eq(genre == null ? Genre.valueOf("") : Genre.valueOf(genre.toUpperCase())))
	// 		.orderBy(review.reviewLike.desc())
	// 		.offset(pageable.getOffset())
	// 		.limit(pageable.getPageSize());
	//
	// 	List<ReviewAllByUserDto> reviews = results.fetch();
	//
	// 	return new PageImpl<>(reviews, pageable, results.fetchCount());
	// }
}
