package com.yju.toonovel.domain.review.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.yju.toonovel.domain.user.entity.User;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class LikeReview {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "likeReview_id")
	private Long likeReviewId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "review_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Review review;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	private boolean isActived;

	@Builder
	public LikeReview(Review review, User user, boolean isActived) {
		this.review = review;
		this.user = user;
	}

	public static LikeReview likeReview(User user, Review review) {
		return LikeReview
			.builder()
			.user(user)
			.review(review)
			.build();
	}

	public void toggleLike() {
		isActived = !isActived;
	}
}
