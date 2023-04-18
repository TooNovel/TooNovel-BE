package com.yju.toonovel.global.security.oauth;

import static com.yju.toonovel.global.security.oauth.HttpCookieOAuth2AuthorizationRequestRepository.*;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.yju.toonovel.domain.user.repository.UserRepository;
import com.yju.toonovel.global.security.jwt.JwtTokenProvider;
import com.yju.toonovel.global.security.oauth.util.CookieUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private final JwtTokenProvider tokenProvider;

	private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;

	private final UserRepository userRepository;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
												Authentication authentication) throws IOException {
		String targetUrl = determineTargetUrl(request, response, authentication);

		clearAuthenticationAttributes(request, response);

		getRedirectStrategy().sendRedirect(request, response, targetUrl);
	}

	protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response,
											Authentication authentication) {
		Optional<String> redirectUri = CookieUtils.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
			.map(Cookie::getValue);

		String targetUrl = redirectUri.orElse(getDefaultTargetUrl());

		Long userId = userRepository.findByUserIdWhereOauthId(authentication.getName());
		String token = tokenProvider.createAccessToken(userId, authentication.getAuthorities().toString());
		int result = checkUser(authentication.getName());

		return UriComponentsBuilder.fromUriString(targetUrl)
			.queryParam("token", token)
			.queryParam("result", result)
			.queryParam("name", authentication.getName())
			.build().toUriString();
	}

	protected int checkUser(String oauthId) {
		return userRepository.findByGender(oauthId).isEmpty() ? 1 : 0;
	}

	protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
		super.clearAuthenticationAttributes(request);
		httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
	}

}
