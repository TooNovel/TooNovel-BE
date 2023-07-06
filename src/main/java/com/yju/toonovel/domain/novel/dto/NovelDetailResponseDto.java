package com.yju.toonovel.domain.novel.dto;

import java.util.List;

import com.yju.toonovel.domain.novel.entity.Genre;
import com.yju.toonovel.domain.novel.entity.Novel;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "소설 상세 정보 조회 응답 DTO")
@Getter
public class NovelDetailResponseDto {

	@Schema(description = "소설 ID")
	private final Long novelId;
	@Schema(description = "소설 제목")
	private final String title;
	@Schema(description = "소설 설명")
	private final String description;
	@Schema(description = "작가 이름")
	private final String author;
	@Schema(description = "장르")
	private final Genre genre;
	@Schema(description = "좋아요 수")
	private final long likeCount;
	@Schema(description = "리뷰 수")
	private final long reviewCount;
	@Schema(description = "소설 평점")
	private final double grade;
	@Schema(description = "서비스 중인 플랫폼 리스트")
	private final List<PlatformResponseDto> platforms;
	@Schema(description = "소설 표지")
	private final String image;

	private NovelDetailResponseDto(Novel novel, List<PlatformResponseDto> platform) {
		this.novelId = novel.getNovelId();
		this.title = novel.getTitle();
		this.description = novel.getDescription();
		this.author = novel.getAuthor();
		this.genre = novel.getGenre();
		this.likeCount = novel.getLikeCount();
		this.reviewCount = novel.getReviewCount();
		this.grade = novel.getGrade();
		this.platforms = platform;
		this.image = novel.getImage();
	}

	public static NovelDetailResponseDto from(Novel novel, List<PlatformResponseDto> platform) {
		return new NovelDetailResponseDto(novel, platform);
	}
}
