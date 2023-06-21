package com.yju.toonovel.domain.user.repository;

import static com.yju.toonovel.domain.admin.entity.QEnrollHistory.*;
import static com.yju.toonovel.domain.user.entity.QUser.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yju.toonovel.domain.user.dto.AuthorListPaginationRequestDto;
import com.yju.toonovel.domain.user.dto.AuthorListResponseDto;
import com.yju.toonovel.global.common.Sort;

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
				enrollIdCondition(dto.getEnrollId(), dto.getSort()),
				titleCondition(dto.getNickname()),
				enrollHistory.isApproval.eq(true)
				)
			.orderBy(makeOrderByCondition(dto.getSort()))
			.limit(dto.getLimit())
			.fetch();
	}

	private Predicate enrollIdCondition(Long enrollId, Sort sort) {
		if (enrollId == null) {
			return null;
		} else if (sort == Sort.CREATED_DATE_DESC) {
			return enrollHistory.enrollId.lt(enrollId);
		} else if (sort == Sort.CREATED_DATE_ASC) {
			return enrollHistory.enrollId.gt(enrollId);
		}
		return enrollHistory.enrollId.lt(enrollId);
	}

	private Predicate titleCondition(String nickname) {
		return (nickname == null) ? null : enrollHistory.user.nickname.contains(nickname);
	}

	private OrderSpecifier<?> makeOrderByCondition(Sort sort) {
		if (sort == Sort.CREATED_DATE_DESC) {
			return enrollHistory.enrollId.desc();
		} else if (sort == Sort.CREATED_DATE_ASC) {
			return enrollHistory.enrollId.asc();
		}

		return enrollHistory.enrollId.desc();
	}


}
