package com.yju.toonovel.domain.post.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.Formula;

import com.yju.toonovel.domain.user.entity.User;
import com.yju.toonovel.global.common.entity.BaseEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@DynamicInsert
public class Post extends BaseEntity {

	@Id
	@Column(name = "post_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long postId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@Enumerated(EnumType.STRING)
	@Column(name = "category", nullable = false)
	private Category category;

	@Column(name = "title", length = 50, nullable = false)
	private String title;

	@Lob
	@Column(name = "content", nullable = false)
	private String content;

	@Column(name = "likes")
	@ColumnDefault("0")
	private Long like;

	@Column(name = "view_count")
	@ColumnDefault("0")
	private Long viewCount;

	@Basic(fetch = FetchType.LAZY)
	@Formula("(SELECT COUNT(1) FROM comment c WHERE c.post_id = post_id)")
	private long commentCount;

	@Builder
	public Post(User user, Category category, String title, String content) {
		this.user = user;
		this.category = category;
		this.title = title;
		this.content = content;
	}

	public static Post of(User user, Category category, String title, String content) {
		return Post.builder()
			.user(user)
			.title(title)
			.category(category)
			.content(content)
			.build();
	}

	public void updatePost(String title, String content, Category category) {
		this.title = title;
		this.content = content;
		this.category = category;
	}

	public void increaseViewCount(Long viewCount) {
		this.viewCount += 1;
	}

	public void clickPostLike() {
		like += 1;
	}

}
