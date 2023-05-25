package com.yju.toonovel.global.security.oauth.dto;

import java.util.Map;

import com.yju.toonovel.domain.user.entity.Provider;
import com.yju.toonovel.domain.user.entity.Role;
import com.yju.toonovel.domain.user.entity.User;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OAuthAttributes {

	private String nameAttributeKey; // OAuth2 로그인 진행 시 키가 되는 필드 값, PK와 같은 의미
	private OAuth2UserInfo oauth2UserInfo; // 소셜 타입별 로그인 유저 정보(닉네임, 이메일, 프로필 사진 등등)

	@Builder
	public OAuthAttributes(String nameAttributeKey, OAuth2UserInfo oauth2UserInfo) {
		this.nameAttributeKey = nameAttributeKey;
		this.oauth2UserInfo = oauth2UserInfo;
	}

	public static OAuthAttributes of(Provider provider,
		String userNameAttributeName, Map<String, Object> attributes) {

		if (provider == Provider.GOOGLE) {
			return ofGoogle(userNameAttributeName, attributes);
		}
		return ofKakao(userNameAttributeName, attributes);
	}

	private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
		return OAuthAttributes.builder()
			.nameAttributeKey(userNameAttributeName)
			.oauth2UserInfo(new KakaoOAuth2UserInfo(attributes))
			.build();
	}

	public static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
		return OAuthAttributes.builder()
			.nameAttributeKey(userNameAttributeName)
			.oauth2UserInfo(new GoogleOAuth2UserInfo(attributes))
			.build();
	}

	public User toEntity(Provider provider, OAuth2UserInfo oauth2UserInfo) {
		return User.builder()
			.provider(provider)
			.oauthId(oauth2UserInfo.getId())
			.role(Role.GUEST)
			.build();
	}
}
