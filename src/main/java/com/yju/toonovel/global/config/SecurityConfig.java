package com.yju.toonovel.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter;
import org.springframework.security.web.SecurityFilterChain;

import com.yju.toonovel.global.security.jwt.filter.ExceptionHandlerFilter;
import com.yju.toonovel.global.security.jwt.filter.JwtAuthenticationEntryPoint;
import com.yju.toonovel.global.security.jwt.filter.JwtAuthenticationFilter;
import com.yju.toonovel.global.security.oauth.CustomOAuth2UserService;
import com.yju.toonovel.global.security.oauth.HttpCookieOAuth2AuthorizationRequestRepository;
import com.yju.toonovel.global.security.oauth.handler.OAuth2AuthenticationFailureHandler;
import com.yju.toonovel.global.security.oauth.handler.OAuth2AuthenticationSuccessHandler;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	private final CustomOAuth2UserService customOAuth2UserService;
	private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
	private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;
	private final ExceptionHandlerFilter exceptionHandlerFilter;

	@Bean
	public HttpCookieOAuth2AuthorizationRequestRepository cookieOAuth2AuthorizationRequestRepository() {
		return new HttpCookieOAuth2AuthorizationRequestRepository();
	}

	@Bean
	public SecurityFilterChain httpSecurity(HttpSecurity http) throws Exception {
		return http
			.csrf().disable()
			.cors()
			.and()
			.authorizeHttpRequests()
			.antMatchers("/").permitAll()
			.antMatchers("/token", "/api/**", "/login/**", "/oauth2/**").permitAll()
			.antMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html", "/swagger-resources/**",
				"/favicon.ico").permitAll()
			.anyRequest().authenticated()
			.and()
			.httpBasic().disable()
			.rememberMe().disable()
			.formLogin().disable()
			.logout().disable()
			.requestCache().disable()
			.headers().disable()
			.oauth2Login()
			.authorizationEndpoint().baseUri("/oauth2/authorize")
			.authorizationRequestRepository(cookieOAuth2AuthorizationRequestRepository())
			.and()
			.redirectionEndpoint().baseUri("/login/oauth2/code/**")
			.and()
			.userInfoEndpoint().userService(customOAuth2UserService)
			.and()
			.successHandler(oAuth2AuthenticationSuccessHandler)
			.failureHandler(oAuth2AuthenticationFailureHandler)
			.and()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.exceptionHandling()
			.authenticationEntryPoint(jwtAuthenticationEntryPoint)
			.and()
			.addFilterBefore(jwtAuthenticationFilter, OAuth2AuthorizationRequestRedirectFilter.class)
			.addFilterBefore(exceptionHandlerFilter, JwtAuthenticationFilter.class)
			.build();
	}
}
