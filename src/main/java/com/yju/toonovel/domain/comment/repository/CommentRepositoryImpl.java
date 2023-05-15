package com.yju.toonovel.domain.comment.repository;

import static com.yju.toonovel.domain.comment.entity.QComment.*;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yju.toonovel.domain.comment.dto.CommentAllResponseDto;
import com.yju.toonovel.domain.comment.dto.CommentPaginationRequestDto;
import com.yju.toonovel.domain.comment.entity.QComment;
import com.yju.toonovel.global.common.Sort;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepositoryCustom {

	private final JPAQueryFactory jpaQueryFactory;

	public Long countQuery(QComment comment) {
		return jpaQueryFactory
			.select(comment.count())
			.from(comment)
			.fetchOne();
	}

	public QBean<CommentAllResponseDto> selectComment(QComment comment) {
		return Projections.fields(
			CommentAllResponseDto.class,
			comment.user.imageUrl, comment.user.nickname, comment.commentId,
			comment.commentContent, comment.createdDate, comment.commentLike
		);
	}

	private OrderSpecifier getOrderSpecifiers(Sort sort) {
		for (Sort value : Sort.values()) {
			if (sort == value) {
				Path<Object> path = Expressions.path(Object.class, comment, value.getProperty());
				return new OrderSpecifier(value.getOrder(), path);
			}
		}
		return null;
	}

	@Override
	public Page<CommentAllResponseDto> getAllComment(
		Long pid, Pageable pageable, CommentPaginationRequestDto requestDto
	) {

		JPQLQuery<CommentAllResponseDto> results = jpaQueryFactory
			.select(selectComment(comment))
			.from(comment)
			.where(comment.post.postId.eq(pid))
			.orderBy(getOrderSpecifiers(requestDto.getSort()))
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize());

		List<CommentAllResponseDto> comments = results.fetch();

		return new PageImpl<>(comments, pageable, countQuery(comment));
	}
}
