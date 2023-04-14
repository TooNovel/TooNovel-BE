package com.yju.toonovel.domain.work.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class Work {

	@Id
	@Column(name = "work_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long workId;

	@Column(name = "title", nullable = false)
	private String title;

	@Column(name = "description", nullable = false)
	private String description;

	@Column(name = "author", nullable = false)
	private String author;

	@Column(name = "genre", nullable = false)
	private String genre;

	@Column(name = "image", nullable = false)
	private String image;

}
