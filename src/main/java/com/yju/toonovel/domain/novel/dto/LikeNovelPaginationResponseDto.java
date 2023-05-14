package com.yju.toonovel.domain.novel.dto;

import com.yju.toonovel.domain.novel.entity.Genre;
import com.yju.toonovel.domain.novel.entity.LikeNovel;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LikeNovelPaginationResponseDto {

	private Long likeNovelId;
	private Long novelId;
	private String title;
	private String author;
	private Genre genre;
	private Double grade;
	private String image;

	@Builder
	public LikeNovelPaginationResponseDto(Long likeNovelId, Long novelId, String title, String author, Genre genre,
		Double grade, String image) {
		this.likeNovelId = likeNovelId;
		this.novelId = novelId;
		this.title = title;
		this.author = author;
		this.genre = genre;
		this.grade = grade;
		this.image = image;
	}

	public static LikeNovelPaginationResponseDto from(LikeNovel novel) {
		return new LikeNovelPaginationResponseDto(
			novel.getLikeNovelId(),
			novel.getNovel().getNovelId(),
			novel.getNovel().getTitle(),
			novel.getNovel().getAuthor(),
			novel.getNovel().getGenre(),
			novel.getNovel().getGrade(),
			novel.getNovel().getImage()
		);
	}
}
