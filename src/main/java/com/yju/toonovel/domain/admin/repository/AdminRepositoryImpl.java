package com.yju.toonovel.domain.admin.repository;

import static com.yju.toonovel.domain.admin.entity.QEnrollHistory.*;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yju.toonovel.domain.admin.dto.EnrollListPaginationRequestDto;
import com.yju.toonovel.domain.admin.dto.EnrollListResponseDto;
import com.yju.toonovel.domain.admin.entity.QEnrollHistory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AdminRepositoryImpl implements AdminRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	public Long countQuery(QEnrollHistory enroll) {
		return queryFactory
			.select(enroll.count())
			.from(enroll)
			.fetchOne();
	}

	public BooleanExpression eqIsApproval(Boolean isApproval) {
		if (isApproval == null) {
			return null;
		}
		return enrollHistory.isApproval.eq(isApproval);
	}

	@Override
	public Page<EnrollListResponseDto> findAllEnrollList(EnrollListPaginationRequestDto dto, Pageable pageable) {

		JPAQuery<EnrollListResponseDto> results = queryFactory
			.select(
				Projections.fields(
					EnrollListResponseDto.class,
					enrollHistory.user.userId, enrollHistory.enrollId,
					enrollHistory.nickname, enrollHistory.isApproval,
					enrollHistory.createdDate
				)
			)
			.from(enrollHistory)
			.where(eqIsApproval(dto.getIsApproval()))
			.orderBy(enrollHistory.createdDate.asc())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize());

		List<EnrollListResponseDto> list = results.fetch();

		return new PageImpl<>(list, pageable, countQuery(enrollHistory));
	}
}
