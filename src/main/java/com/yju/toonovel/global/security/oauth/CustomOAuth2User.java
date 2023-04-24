package com.yju.toonovel.global.security.oauth;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import com.yju.toonovel.domain.user.entity.Role;

import lombok.Getter;

@Getter
public class CustomOAuth2User extends DefaultOAuth2User {

	private Role role;
	private Long userId;

	public CustomOAuth2User(Collection<? extends GrantedAuthority> authorities, Map<String, Object> attributes,
		String nameAttributeKey, Long userId, Role role) {
		super(authorities, attributes, nameAttributeKey);
		this.role = role;
		this.userId = userId;
	}
}
