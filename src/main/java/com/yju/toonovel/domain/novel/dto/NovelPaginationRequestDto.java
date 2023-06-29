package com.yju.toonovel.domain.novel.dto;

import com.yju.toonovel.domain.novel.entity.Genre;
import com.yju.toonovel.global.common.Sort;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "소설 조회 요청 DTO")
@Getter
public class NovelPaginationRequestDto {

	@Schema(description = "이전에 받은 마지막 소설 ID")
	private Long novelId;
	@Schema(description = "소설 제목")
	private String title;
	@Schema(description = "작가 이름")
	private String author;
	@Schema(description = "장르")
	private Genre genre;
	@Schema(description = "좋아요 수")
	private Long likeCount;
	@Schema(description = "리뷰 수")
	private Long reviewCount;
	@Schema(description = "소설 평점")
	private Double grade;
	@Schema(description = "소설 추가 일자")
	private String createdDate;
	@Schema(description = "정렬 기준")
	private Sort sort;

	public NovelPaginationRequestDto(Long novelId, String title, String author, Genre genre, Long likeCount,
		Long reviewCount, Double grade, String createdDate, Sort sort) {
		this.novelId = novelId;
		this.title = title;
		this.author = author;
		this.genre = genre;
		this.likeCount = likeCount;
		this.reviewCount = reviewCount;
		this.grade = grade;
		this.createdDate = createdDate;
		this.sort = sort == null ? Sort.CREATED_DATE_DESC : sort;
	}
}
