package com.yju.toonovel.domain.novel.entity;

import java.util.ArrayList;
import java.util.List;

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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Formula;

import com.yju.toonovel.domain.user.entity.User;
import com.yju.toonovel.global.common.entity.BaseEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Novel extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long novelId;

	@Column(name = "title", nullable = false)
	private String title;

	@Column(name = "description", nullable = false, length = 10000)
	private String description;

	@Column(name = "author", nullable = false)
	private String author;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Genre genre;

	@Basic(fetch = FetchType.LAZY)
	@Formula("(SELECT COUNT(1) FROM like_novel l WHERE l.novel_id = novel_id and l.is_actived = true)")
	private long likeCount;

	@Basic(fetch = FetchType.LAZY)
	@Formula("(SELECT FORMAT(AVG(r.review_grade), 1) FROM review r WHERE r.novel_id = novel_id)")
	private Double grade;

	@Basic(fetch = FetchType.LAZY)
	@Formula("(SELECT COUNT(1) FROM review r WHERE r.novel_id = novel_id)")
	private long reviewCount;

	@OneToMany(mappedBy = "novel")
	private List<NovelPlatform> novelPlatforms = new ArrayList<>();

	@Column(name = "image", nullable = false, length = 2000)
	private String image;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	public void updateUserId(User user) {
		this.user = user;
	}
}
