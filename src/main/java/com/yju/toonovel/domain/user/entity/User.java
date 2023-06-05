package com.yju.toonovel.domain.user.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.yju.toonovel.global.common.entity.BaseEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Where(clause = "deleted = false")
@SQLDelete(sql = "UPDATE user SET deleted = true where user_id=?")
public class User extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;

	@Column(length = 15)
	private String nickname;

	@Column(length = 1024)
	private String imageUrl;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Provider provider;

	@Column(nullable = false)
	private String oauthId;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Role role;

	@Column(length = 5)
	private String gender;

	@Column(length = 10)
	private String birth;

	@Column(nullable = false)
	private boolean deleted;

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

	public void update(String nickname, String gender, String birth) {
		this.nickname = nickname;
		this.gender = gender;
		this.birth = birth;
		this.role = Role.USER;
	}

	public void updateRole(Role role) {
		this.role = role;
	}

}
