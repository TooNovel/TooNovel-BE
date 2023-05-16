package com.yju.toonovel.domain.comment.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.yju.toonovel.domain.user.entity.User;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class LikeComment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long likeCommentId;

	@ManyToOne
	private User user;

	@ManyToOne
	private Comment comment;

	private boolean isActived;

	@Builder
	public LikeComment(User user, Comment comment) {
		this.user = user;
		this.comment = comment;
	}

	public static LikeComment of(User user, Comment comment) {
		return LikeComment.builder()
			.user(user)
			.comment(comment)
			.build();
	}

	public void toggleLike() {
		isActived = !isActived;
	}
}
