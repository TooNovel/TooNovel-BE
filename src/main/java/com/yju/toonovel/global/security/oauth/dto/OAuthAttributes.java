package com.yju.toonovel.global.security.oauth.dto;

import java.util.Map;

import com.yju.toonovel.domain.user.entity.Provider;
import com.yju.toonovel.domain.user.entity.Role;
import com.yju.toonovel.domain.user.entity.User;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OAuthAttributes {

	private Map<String, Object> attributes;
	private String nameAttributeKey;
	private String oauthId;
	private String provider;

	public static OAuthAttributes of(String registrationId, String uesrNameAttributeName,
										Map<String, Object> attributes) {
		if ("kakao".equals(registrationId)) {
			return ofKaKao("id", attributes);
		}
		return ofGoogle(uesrNameAttributeName, attributes);
	}

	private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
		return OAuthAttributes.builder()
			.oauthId((String)attributes.get("sub"))
			.provider(String.valueOf(Provider.GOOGLE))
			.attributes(attributes)
			.nameAttributeKey(userNameAttributeName)
			.build();
	}

	private static OAuthAttributes ofKaKao(String userNameAttributeName, Map<String, Object> attributes) {

		return OAuthAttributes.builder()
			.oauthId(attributes.get("id").toString())
			.provider(String.valueOf(Provider.KAKAO))
			.attributes(attributes)
			.nameAttributeKey(userNameAttributeName)
			.build();
	}

	public User toEntity() {
		return  User.builder()
			.oauthId(oauthId)
			.provider(Provider.valueOf(provider))
			.role(Role.GUEST)
			.build();
	}
}
