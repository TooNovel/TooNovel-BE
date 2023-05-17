package com.yju.toonovel.domain.comment.entity;

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
public class LikeComment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long likeCommentId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "comment_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
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
