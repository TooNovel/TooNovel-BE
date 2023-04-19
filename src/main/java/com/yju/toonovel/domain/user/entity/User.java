package com.yju.toonovel.domain.user.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.yju.toonovel.global.common.entity.BaseEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class User extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;

	private String nickname;

	private String imageUrl;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Provider provider;

	@Column(nullable = false)
	private String oauthId;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Role role;

	private String gender;

	private String birth;

	@Builder
	public User(String nickname, String imageUrl, Provider provider, String oauthId,
		Role role, String gender, String birth) {
		this.nickname = nickname;
		this.imageUrl = imageUrl;
		this.provider = provider;
		this.oauthId = oauthId;
		this.role = role;
		this.gender = gender;
		this.birth = birth;
	}

	public void register(String nickname, String gender, String birth) {
		this.nickname = nickname;
		this.gender = gender;
		this.birth = birth;
		this.role = Role.USER;
	}

}
