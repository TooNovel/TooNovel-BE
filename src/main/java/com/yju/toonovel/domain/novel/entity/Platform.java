package com.yju.toonovel.domain.novel.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Platform {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long platformId;

	@Column(nullable = false, unique = true)
	private String platformName;

}
