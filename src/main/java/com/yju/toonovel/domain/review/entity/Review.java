package com.yju.toonovel.domain.review.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.ColumnDefault;

import com.yju.toonovel.domain.novel.entity.Novel;
import com.yju.toonovel.domain.user.entity.User;
import com.yju.toonovel.global.common.entity.BaseEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Review extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long reviewId;

	private String reviewContent;

	@ColumnDefault(value = "0")
	private int reviewLike;

	@Column(nullable = false)
	private int reviewGrade;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User writer;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "novel_id")
	private Novel novel;

	@Builder
	public Review(String reviewContent,
		int reviewLike, int reviewGrade, User writer, Novel novel) {
		this.reviewContent = reviewContent;
		this.reviewLike = reviewLike;
		this.reviewGrade = reviewGrade;
		this.writer = writer;
		this.novel = novel;
	}

	public void clickReviewLike() {
		reviewLike += 1;
	}

	public void unClickReviewLike() {
		reviewLike -= 1;
	}
}
