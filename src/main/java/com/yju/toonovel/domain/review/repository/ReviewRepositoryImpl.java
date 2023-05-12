package com.yju.toonovel.domain.review.repository;

import static com.yju.toonovel.domain.novel.entity.QNovel.*;
import static com.yju.toonovel.domain.review.entity.QReview.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yju.toonovel.domain.novel.entity.Genre;
import com.yju.toonovel.domain.review.dto.ReviewAllByUserDto;
import com.yju.toonovel.domain.review.entity.QReview;
import com.yju.toonovel.domain.review.entity.Review;

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

	public QBean<ReviewAllByUserDto> reviewSelect(QReview review) {
		return Projections.fields(
			ReviewAllByUserDto.class,
			review.novel.image, review.novel.genre, review.novel.author, review.novel.description,
			review.novel.title,
			review.reviewContent, review.reviewGrade, review.createdDate, review.reviewLike,
			review.writer.nickname, review.writer.imageUrl
		);
	}

	private OrderSpecifier<?> getOrderSpecifiers(Pageable pageable) {
												//sort sort

		List<OrderSpecifier> orders = new ArrayList<>();

		for (Sort.Order order : pageable.getSort()) {
			Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;
			String prop = order.getProperty();
			PathBuilder orderByExpression = new PathBuilder(Review.class, "review");

			orders.add(new OrderSpecifier(direction, orderByExpression.get(prop)));
		}
		return orders.get(0);

		// List<OrderSpecifier> orders = new ArrayList<>();
		//
		// for (Sort.Order order : pageable.getSort()) {
		// 	Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;
		// 	String prop = order.getProperty();
		// 	PathBuilder orderByExpression = new PathBuilder(Review.class, "review");
		//
		// 	orders.add(new OrderSpecifier(direction, orderByExpression.get(prop)));
		// }
		// return orders;
	}

	private Predicate eqGenre(Genre genre) {
		if (genre == null) {
			return null;
		}
		return novel.genre.eq(genre);
	}

	private Predicate eqUserId(Long userId) {
		if (userId == null) {
			return null;
		}
		return review.writer.userId.eq(userId);
	}

	//유저가 작성한 리뷰조회
	@Override
	public Page<ReviewAllByUserDto> findAllReviewByUser(Long uid, Pageable pageable) {
		QReview review = QReview.review;

		JPAQuery<ReviewAllByUserDto> results = queryFactory
			.select(reviewSelect(review))
			.from(review)
			.where(eqUserId(uid))
			.orderBy(review.createdDate.desc())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize());

		List<ReviewAllByUserDto> reviews = results.fetch();

		return new PageImpl<>(reviews, pageable, countQuery(review));
	}

	//전체리뷰조회
	@Override
	public Page<ReviewAllByUserDto> getAllReview(Pageable pageable) {
		log.info("test : " + pageable.getSort());
		QReview review = QReview.review;

		JPQLQuery<ReviewAllByUserDto> results = queryFactory
			.select(reviewSelect(review))
			.from(review)
			.orderBy(getOrderSpecifiers(pageable))
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
