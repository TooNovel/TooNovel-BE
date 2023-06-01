package com.yju.toonovel.domain.post.repository;

import static com.yju.toonovel.domain.post.entity.QPost.*;

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
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yju.toonovel.domain.post.dto.PostAllResponseDto;
import com.yju.toonovel.domain.post.dto.PostPaginationRequestDto;
import com.yju.toonovel.domain.post.entity.Category;
import com.yju.toonovel.domain.post.entity.QPost;
import com.yju.toonovel.domain.user.entity.QUser;
import com.yju.toonovel.global.common.Sort;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {

	private final JPAQueryFactory jpaQueryFactory;

	public Long countQuery(QPost post, PostPaginationRequestDto requestDto) {
		return jpaQueryFactory
			.select(post.count())
			.from(post)
			.where(eqCategory(requestDto.getCategory()))
			.fetchOne();
	}

	public QBean<PostAllResponseDto> selectPost(QPost post, QUser user) {
		return Projections.fields(
			PostAllResponseDto.class,
			post.postId, post.category, post.title, post.createdDate,
			post.like, post.viewCount, user.nickname
		);
	}

	private Predicate eqCategory(Category category) {
		if (category == null) {
			return null;
		}
		return post.category.eq(category);
	}

	private OrderSpecifier getOrderSpecifiers(Sort sort) {
		for (Sort value : Sort.values()) {
			if (sort == value) {
				Path<Object> path = Expressions.path(Object.class, post, value.getProperty());
				return new OrderSpecifier(value.getOrder(), path);
			}
		}
		return null;
	}

	@Override
	public Page<PostAllResponseDto> getAllPost(
		Pageable pageable, PostPaginationRequestDto requestDto
	) {

		JPQLQuery<PostAllResponseDto> results = jpaQueryFactory
			.select(selectPost(post, post.user))
			.from(post)
			.where(eqCategory(requestDto.getCategory()))
			.orderBy(getOrderSpecifiers(requestDto.getSort()))
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.join(post.user);

		List<PostAllResponseDto> posts = results.fetch();

		return new PageImpl<>(posts, pageable, countQuery(post, requestDto));
	}
}
