package com.yju.toonovel.domain.user.repository;

import static com.yju.toonovel.domain.admin.entity.QEnrollHistory.*;
import static com.yju.toonovel.domain.user.entity.QUser.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yju.toonovel.domain.user.dto.AuthorListPaginationRequestDto;
import com.yju.toonovel.domain.user.dto.AuthorListResponseDto;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class EnrollCustomRepositoryImpl implements EnrollCustomRepository {

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public List<AuthorListResponseDto> findAllAuthor(AuthorListPaginationRequestDto dto) {
		return jpaQueryFactory
			.select(Projections.fields(
				AuthorListResponseDto.class,
				enrollHistory.user.userId,
				enrollHistory.user.nickname,
				enrollHistory.user.imageUrl,
				enrollHistory.enrollId
			))
			.from(enrollHistory)
			.join(enrollHistory.user, user)
			.where(
				ltEnrollId(dto.getEnrollId()),
				titleCondition(dto.getNickname()),
				enrollHistory.isApproval.eq(true)
			)
			.orderBy(enrollHistory.enrollId.desc())
			.limit(30)
			.fetch();
	}

	private BooleanExpression ltEnrollId(Long enrollId) {
		if (enrollId == null) {
			return null;
		}

		return enrollHistory.enrollId.lt(enrollId);
	}

	private Predicate titleCondition(String nickname) {
		return (nickname == null) ? null : enrollHistory.user.nickname.contains(nickname);
	}

}
