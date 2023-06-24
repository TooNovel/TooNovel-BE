package com.yju.toonovel.domain.novel.dto;

import com.yju.toonovel.domain.novel.entity.Genre;
import com.yju.toonovel.domain.novel.entity.LikeNovel;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "좋아요한 소설 조회 응답 DTO")
@Getter
public class LikeNovelPaginationResponseDto {

	@Schema(description = "좋아요 목록 ID")
	private Long likeNovelId;
	@Schema(description = "소설 ID")
	private Long novelId;
	@Schema(description = "소설 제목")
	private String title;
	@Schema(description = "작가 이름")
	private String author;
	@Schema(description = "장르")
	private Genre genre;
	@Schema(description = "소설 평점")
	private Double grade;
	@Schema(description = "리뷰 수")
	private Long reviewCount;
	@Schema(description = "좋아요 수")
	private Long likeCount;
	@Schema(description = "소설 표지")
	private String image;

	@Builder
	public LikeNovelPaginationResponseDto(Long likeNovelId, Long novelId, String title, String author, Genre genre,
		Double grade, String image, Long reviewCount, Long likeCount) {
		this.likeNovelId = likeNovelId;
		this.novelId = novelId;
		this.title = title;
		this.author = author;
		this.genre = genre;
		this.grade = grade;
		this.image = image;
		this.reviewCount = reviewCount;
		this.likeCount = likeCount;
	}

	public static LikeNovelPaginationResponseDto from(LikeNovel novel) {
		return new LikeNovelPaginationResponseDto(
			novel.getLikeNovelId(),
			novel.getNovel().getNovelId(),
			novel.getNovel().getTitle(),
			novel.getNovel().getAuthor(),
			novel.getNovel().getGenre(),
			novel.getNovel().getGrade(),
			novel.getNovel().getImage(),
			novel.getNovel().getReviewCount(),
			novel.getNovel().getLikeCount()
		);
	}
}
