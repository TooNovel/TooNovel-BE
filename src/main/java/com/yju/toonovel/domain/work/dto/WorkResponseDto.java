package com.yju.toonovel.domain.work.dto;

import com.yju.toonovel.domain.work.entity.Work;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkResponseDto {

	private Long workId;
	private String title;
	private String description;
	private String author;
	private String genre;
	private String image;

	public WorkResponseDto(Work entity) {
		this.workId = entity.getWorkId();
		this.title = entity.getTitle();
		this.description = entity.getDescription();
		this.author = entity.getAuthor();
		this.genre = entity.getGenre();
		this.image = entity.getImage();
	}
}
