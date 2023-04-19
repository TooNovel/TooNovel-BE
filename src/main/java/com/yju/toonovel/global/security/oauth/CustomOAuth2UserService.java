package com.yju.toonovel.global.security.oauth;

import java.util.Collections;
import java.util.Map;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.yju.toonovel.domain.user.entity.Provider;
import com.yju.toonovel.domain.user.entity.User;
import com.yju.toonovel.domain.user.repository.UserRepository;
import com.yju.toonovel.global.security.oauth.dto.OAuthAttributes;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

	private final UserRepository userRepository;

	private static final String KAKAO = "kakao";

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

		OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
		OAuth2User oAuth2User = delegate.loadUser(userRequest);

		String registrationId = userRequest.getClientRegistration().getRegistrationId();
		Provider provider = getProvider(registrationId);
		String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
			.getUserInfoEndpoint().getUserNameAttributeName();
		Map<String, Object> attributes = oAuth2User.getAttributes();

		OAuthAttributes extractAttributes = OAuthAttributes.of(provider, userNameAttributeName, attributes);

		User user = getUser(provider, extractAttributes);

		return new CustomOAuth2User(
			Collections.singleton(new SimpleGrantedAuthority(user.getRole().getKey())),
			attributes,
			extractAttributes.getNameAttributeKey(),
			user.getUserId(),
			user.getRole()
		);
	}

	private Provider getProvider(String registrationId) {
		if (KAKAO.equals(registrationId)) {
			return Provider.KAKAO;
		}
		return Provider.GOOGLE;
	}

	private User getUser(Provider provider, OAuthAttributes attributes) {
		User findUser = userRepository.findByProviderAndOauthId(provider,
			attributes.getOauth2UserInfo().getId()).orElse(null);

		if (findUser == null) {
			return saveUser(provider, attributes);
		}
		return findUser;
	}

	private User saveUser(Provider provider, OAuthAttributes attributes) {
		User user = attributes.toEntity(provider, attributes.getOauth2UserInfo());
		return userRepository.save(user);
	}

}
