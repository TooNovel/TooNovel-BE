package com.yju.toonovel.domain.novel.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.yju.toonovel.domain.novel.entity.Genre;
import com.yju.toonovel.domain.novel.entity.Novel;
import com.yju.toonovel.domain.novel.entity.NovelPlatform;
import com.yju.toonovel.domain.novel.entity.Platform;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NovelDetailResponseDto {

	private Long novelId;
	private String title;
	private String description;
	private String author;
	private Genre genre;
	private long likeCount;
	private List<PlatformResponseDto> platforms = new ArrayList<>();
	private String image;

	public NovelDetailResponseDto(Novel novel, List<Platform> platform) {
		this.novelId = novel.getNovelId();
		this.title = novel.getTitle();
		this.description = novel.getDescription();
		this.author = novel.getAuthor();
		this.genre = novel.getGenre();
		this.likeCount = novel.getLikeCount();
		platform.forEach(
			idx -> this.platforms.add(new PlatformResponseDto(idx))
		);
		this.image = novel.getImage();
	}

}
