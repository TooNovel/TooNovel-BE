package com.yju.toonovel.domain.novel.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.yju.toonovel.domain.user.entity.User;
import com.yju.toonovel.global.common.entity.BaseEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class LikeNovel extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "like_novel_id", nullable = false)
	private Long likeNovelId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "novel_id")
	private Novel novel;

	private boolean isActived;

	@Builder
	private LikeNovel(User user, Novel novel) {
		this.user = user;
		this.novel = novel;
	}

	public static LikeNovel of(
		User user,
		Novel novel
	) {
		return LikeNovel
			.builder()
			.user(user)
			.novel(novel)
			.build();
	}

	public void toggleLike() {
		isActived = !isActived;
	}



}
