package com.yju.toonovel.domain.novel.dto;

import com.yju.toonovel.domain.novel.entity.Genre;
import com.yju.toonovel.domain.novel.entity.LikeNovel;
import com.yju.toonovel.domain.novel.entity.Novel;

import lombok.Builder;
import lombok.Getter;

@Getter
public class NovelPaginationResponseDto {

	private Long novelId;
	private String title;
	private String author;
	private Genre genre;
	private String image;

	@Builder
	public NovelPaginationResponseDto(Long novelId, String title, String author, Genre genre, String image) {
		this.novelId = novelId;
		this.title = title;
		this.author = author;
		this.genre = genre;
		this.image = image;
	}

	public static NovelPaginationResponseDto from(Novel novel) {
		return new NovelPaginationResponseDto(
			novel.getNovelId(),
			novel.getTitle(),
			novel.getAuthor(),
			novel.getGenre(),
			novel.getImage()
		);
	}

	public static NovelPaginationResponseDto from(LikeNovel novel) {
		return new NovelPaginationResponseDto(
			novel.getNovel().getNovelId(),
			novel.getNovel().getTitle(),
			novel.getNovel().getAuthor(),
			novel.getNovel().getGenre(),
			novel.getNovel().getImage()
		);
	}
}
