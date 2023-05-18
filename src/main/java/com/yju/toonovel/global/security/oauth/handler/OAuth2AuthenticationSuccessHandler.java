package com.yju.toonovel.global.security.oauth.handler;

import static com.yju.toonovel.global.security.oauth.HttpCookieOAuth2AuthorizationRequestRepository.*;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
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

	@Value("${GUEST_REDIRECT_URL}")
	String guestUrl;

	@Value("${USER_REDIRECT_URL}")
	String userUrl;

	@Value("${jwt.expire-seconds.access-token}")
	long accessTokenExpireSeconds;

	@Value("${jwt.expire-seconds.refresh-token}")
	long refreshTokenExpireSeconds;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException {
		CustomOAuth2User oAuth2User = (CustomOAuth2User)authentication.getPrincipal();
		if (oAuth2User.getRole() == Role.GUEST) {
			String accessToken = tokenProvider.createAccessToken(oAuth2User.getUserId(), oAuth2User.getRole().name());
			String refreshToken = tokenProvider.createRefreshToken();

			setAccessTokenInCookie(response, accessToken);
			setRefreshTokenInCookie(response, refreshToken);

			tokenProvider.updateRefreshToken(oAuth2User.getUserId(), refreshToken);
			response.sendRedirect(guestUrl);

		} else {
			loginSuccess(response, oAuth2User);
		}
	}

	private void setAccessTokenInCookie(HttpServletResponse response, String accessToken) {
		ResponseCookie token = ResponseCookie.from("accessTokenCookie", accessToken)
			.path(getDefaultTargetUrl())
			.sameSite("None")
			.secure(true)
			.maxAge(accessTokenExpireSeconds)
			.build();

		response.addHeader("Set-Cookie", token.toString());
	}

	private void setRefreshTokenInCookie(HttpServletResponse response, String refreshToken) {
		ResponseCookie token = ResponseCookie.from("refreshTokenCookie", refreshToken)
			.path(getDefaultTargetUrl())
			.sameSite("None")
			.secure(true)
			.maxAge(refreshTokenExpireSeconds)
			.build();

		response.addHeader("Set-Cookie", token.toString());
	}

	private void loginSuccess(HttpServletResponse response, CustomOAuth2User oAuth2User) throws IOException {

		String accessToken = tokenProvider.createAccessToken(oAuth2User.getUserId(), oAuth2User.getRole().name());
		String refreshToken = tokenProvider.createRefreshToken();

		setAccessTokenInCookie(response, accessToken);
		setRefreshTokenInCookie(response, refreshToken);
		response.sendRedirect(userUrl);

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
