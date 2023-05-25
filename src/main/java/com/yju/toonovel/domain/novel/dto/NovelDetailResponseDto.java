package com.yju.toonovel.domain.novel.dto;

import java.util.ArrayList;
import java.util.List;

import com.yju.toonovel.domain.novel.entity.Genre;
import com.yju.toonovel.domain.novel.entity.Novel;
import com.yju.toonovel.domain.novel.entity.Platform;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "소설 상세 정보 조회 응답 DTO")
@Getter
@NoArgsConstructor
public class NovelDetailResponseDto {

	@Schema(description = "소설 ID")
	private Long novelId;
	@Schema(description = "소설 제목")
	private String title;
	@Schema(description = "소설 설명")
	private String description;
	@Schema(description = "작가 이름")
	private String author;
	@Schema(description = "장르")
	private Genre genre;
	@Schema(description = "좋아요 수")
	private long likeCount;
	@Schema(description = "소설 평점")
	private Double grade;
	@Schema(description = "서비스 중인 플랫폼 리스트")
	private List<PlatformResponseDto> platforms = new ArrayList<>();
	@Schema(description = "소설 표지")
	private String image;

	public NovelDetailResponseDto(Novel novel, List<Platform> platform) {
		this.novelId = novel.getNovelId();
		this.title = novel.getTitle();
		this.description = novel.getDescription();
		this.author = novel.getAuthor();
		this.genre = novel.getGenre();
		this.likeCount = novel.getLikeCount();
		this.grade = novel.getGrade();
		platform.forEach(
			idx -> this.platforms.add(new PlatformResponseDto(idx))
		);
		this.image = novel.getImage();
	}

}
