package com.yju.toonovel.domain.post.entity;

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
public class LikePost {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long likePostId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Post post;

	private boolean isActived;

	@Builder
	private LikePost(User user, Post post) {
		this.user = user;
		this.post = post;
	}

	public static LikePost of(User user, Post post) {
		return LikePost.builder()
			.user(user)
			.post(post)
			.build();
	}

	public void toggleLike() {
		isActived = !isActived;
	}
}
