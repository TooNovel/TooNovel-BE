package com.yju.toonovel.domain.comment.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.yju.toonovel.domain.post.entity.Post;
import com.yju.toonovel.domain.user.entity.User;
import com.yju.toonovel.global.common.entity.BaseEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long commentId;

	@Column(length = 500, nullable = false)
	private String commentContent;

	@ColumnDefault("0")
	private int commentLike;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Post post;

	@Builder
	public Comment(User user, Post post, String commentContent) {
		this.user = user;
		this.post = post;
		this.commentContent = commentContent;
	}

	public static Comment of(User user, Post post, String commentContent) {
		return Comment.builder()
			.user(user)
			.post(post)
			.commentContent(commentContent)
			.build();
	}

	//댓글 내용 수정
	public void updateContent(String content) {
		this.commentContent = content;
	}

	//좋아요 클릭시 좋아요 수 증가
	public void clickCommentLike() {
		commentLike += 1;
	}

	//좋아요 클릭시 좋아요 수 감소
	// public void unClickCommentLike() {
	// 	commentLike-=1;
	// }
}
