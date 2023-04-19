package com.yju.toonovel.global.security.oauth.handler;

import static com.yju.toonovel.global.security.oauth.HttpCookieOAuth2AuthorizationRequestRepository.*;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.yju.toonovel.domain.user.entity.Role;
import com.yju.toonovel.global.security.jwt.JwtTokenProvider;
import com.yju.toonovel.global.security.oauth.CustomOAuth2User;
import com.yju.toonovel.global.security.oauth.HttpCookieOAuth2AuthorizationRequestRepository;
import com.yju.toonovel.global.util.CookieUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private final JwtTokenProvider tokenProvider;

	private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;

	@Value("${jwt.header.access-token}")
	String accessTokenHeader;

	@Value("${jwt.header.refresh-token}")
	String refreshTokenHeader;

	private final String bearerType = "Bearer";

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException {
		CustomOAuth2User oAuth2User = (CustomOAuth2User)authentication.getPrincipal();
		if (oAuth2User.getRole() == Role.GUEST) {
			String accessToken = tokenProvider.createAccessToken(oAuth2User.getUserId(), oAuth2User.getRole().name());
			response.addHeader(accessTokenHeader, bearerType + accessToken);
			response.sendRedirect(determineTargetUrl(request, response));

			tokenProvider.sendAccessAndRefreshToken(response, accessToken, null);
		} else {
			loginSuccess(response, oAuth2User);
		}
	}

	private void loginSuccess(HttpServletResponse response, CustomOAuth2User oAuth2User) throws IOException {

		String accessToken = tokenProvider.createAccessToken(oAuth2User.getUserId(), oAuth2User.getRole().name());
		String refreshToken = tokenProvider.createRefreshToken();
		response.addHeader(accessTokenHeader, "Bearer " + accessToken);
		response.addHeader(refreshTokenHeader, "Bearer " + refreshToken);

		tokenProvider.sendAccessAndRefreshToken(response, accessToken, refreshToken);
		tokenProvider.updateRefreshToken(oAuth2User.getUserId(), refreshToken);
	}

	protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response) {
		Optional<String> redirectUri = CookieUtils.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
			.map(Cookie::getValue);

		String targetUrl = redirectUri.orElse(getDefaultTargetUrl());

		return targetUrl;
	}

	private void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
		super.clearAuthenticationAttributes(request);
		httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
	}

}
