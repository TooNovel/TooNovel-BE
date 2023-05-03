package com.yju.toonovel.domain.review.dto;

import java.time.LocalDateTime;

import com.yju.toonovel.domain.novel.entity.Genre;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewAllByUserDto {

	//user
	private String nickname;
	private String imageUrl;

	//novel
	private String title;
	private String description;
	private String author;
	private Genre genre;
	private String image;

	//review
	private String reviewContent;
	private int reviewGrade;
	private int reviewLike;
	private LocalDateTime createdDate;

	@Builder
	public ReviewAllByUserDto(String image, Genre genre, String author, String description, String title,
		String reviewContent, int reviewGrade, LocalDateTime createdDate, int reviewLike,
		String nickname, String imageUrl) {
		this.image = image;
		this.genre = genre;
		this.author = author;
		this.description = description;
		this.title = title;
		this.reviewContent = reviewContent;
		this.reviewGrade = reviewGrade;
		this.createdDate = createdDate;
		this.reviewLike = reviewLike;
		this.nickname = nickname;
		this.imageUrl = imageUrl;
	}

}
