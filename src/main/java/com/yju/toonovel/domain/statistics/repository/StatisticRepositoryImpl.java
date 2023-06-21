package com.yju.toonovel.domain.statistics.repository;

import static com.yju.toonovel.domain.novel.entity.QNovel.*;
import static com.yju.toonovel.domain.review.entity.QReview.*;
import static com.yju.toonovel.domain.user.entity.QUser.*;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.JPQLQueryFactory;
import com.yju.toonovel.domain.admin.dto.AdminStatisticsRequestDto;
import com.yju.toonovel.domain.statistics.dto.AdminStatisticsResponseDto;
import com.yju.toonovel.domain.statistics.dto.StatisticsResultResponseDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@RequiredArgsConstructor
@Slf4j
public class StatisticRepositoryImpl implements StatisticRepositoryCustom {
	private final JPQLQueryFactory queryFactory;

	//작가 - 성별통계제공
	@Override
	public List<StatisticsResultResponseDto> getGenderStatistic(Long nid) {
		StringPath gender = user.gender;

		return queryFactory
			.select(
				Projections.fields(
					StatisticsResultResponseDto.class,
					novel.count().as("count"), user.gender
				)
			)
			.from(novel)
			.innerJoin(review).on(novel.novelId.eq(review.novel.novelId))
			.innerJoin(user).on(review.writer.userId.eq(user.userId))
			.fetchJoin()
			.where(novel.novelId.eq(nid))
			.groupBy(gender).fetch();

	}

	public String year(int year) {
		return String.valueOf(year);
	}

	public StringExpression ageGroup(StringExpression birthYear, int year) {
		return new CaseBuilder()
			.when(birthYear.goe(year(year))) //2014보다 크다면
			.then("0~10")
			.when(birthYear.goe(year(year - 10)).and(birthYear.lt(year(year))))
			.then("10~20")
			.when(birthYear.goe(year(year - 20)).and(birthYear.lt(year(year - 10))))
			.then("20~30")
			.when(birthYear.goe(year(year - 30)).and(birthYear.lt(year(year - 20))))
			.then("30~40")
			.when(birthYear.goe(year(year - 40)).and(birthYear.lt(year(year - 30))))
			.then("40~50")
			.otherwise("50~");
	}

	//작가 - 나이통계제공
	@Override
	public List<StatisticsResultResponseDto> getAgeStatistic(Long nid) {

		StringExpression birthYear = Expressions.stringTemplate("SUBSTRING({0}, 1, 4)", user.birth);
		int year =  LocalDate.now().getYear() - 9; //2023-9 = 2014 = 10살 - 10, 20, 30 기준으로 계산하기 위함

		return queryFactory
			.select(
				Projections.fields(
					StatisticsResultResponseDto.class,
					novel.count().as("count"),
					ageGroup(birthYear, year).as("age")
				)
			)
			.from(novel)
			.innerJoin(review).on(novel.novelId.eq(review.novel.novelId))
			.innerJoin(user).on(review.writer.userId.eq(user.userId))
			.where(novel.novelId.eq(nid))
			.groupBy(ageGroup(birthYear, year)).fetch();
	}

	public List<AdminStatisticsResponseDto> getReviewStatistic(AdminStatisticsRequestDto dto) {

		StringExpression createdDate = Expressions.stringTemplate("SUBSTRING({0}, 1, 10)", review.createdDate);

		return queryFactory
			.select(
				Projections.fields(
					AdminStatisticsResponseDto.class,
					review.count().as("count"),
					createdDate.as("createdDate")
				)
			)
			.from(review)
			.where(createdDate.between(dto.getStartDate(), dto.getEndDate()))
			.groupBy(createdDate)
			.orderBy(review.createdDate.asc())
			.fetch();
	}

	public List<AdminStatisticsResponseDto> getNovelStatistic(AdminStatisticsRequestDto dto) {

		StringExpression createdDate = Expressions.stringTemplate("SUBSTRING({0}, 1, 10)", novel.createdDate);

		return queryFactory
			.select(
				Projections.fields(
					AdminStatisticsResponseDto.class,
					novel.count().as("count"),
					createdDate.as("createdDate")
				)
			)
			.from(novel)
			.where(createdDate.between(dto.getStartDate(), dto.getEndDate()))
			.groupBy(createdDate)
			.orderBy(novel.createdDate.asc())
			.fetch();
	}
}
