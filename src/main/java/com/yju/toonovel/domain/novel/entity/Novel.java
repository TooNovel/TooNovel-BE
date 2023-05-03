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
import javax.persistence.OneToMany;

import org.hibernate.annotations.Formula;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

	@Column(name = "description", nullable = false)
	private String description;

	@Column(name = "author", nullable = false)
	private String author;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Genre genre;

	@Basic(fetch = FetchType.LAZY)
	@Formula("(select count(1) from like_novel l where l.novel_id = novel_id and l.is_actived = true)")
	private long likeCount;

	@OneToMany(mappedBy = "novel")
	private List<NovelPlatform> novelPlatforms = new ArrayList<>();

	@Column(name = "image", nullable = false)
	private String image;

}