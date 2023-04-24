package com.yju.toonovel.global.security.jwt;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

	private final Object principal;
	private String credentials;

	public JwtAuthenticationToken(String principal, String credentials) {
		super(null);
		super.setAuthenticated(false);

		this.principal = principal;
		this.credentials = credentials;
	}

	public JwtAuthenticationToken(Object principal, String credentials,
		Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
		super.setAuthenticated(true);
		this.principal = principal;
		this.credentials = credentials;
	}

	@Override
	public Object getCredentials() {
		return credentials;
	}

	@Override
	public Object getPrincipal() {
		return principal;
	}

	@Override
	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
		if (isAuthenticated) {
			throw new IllegalArgumentException("이미 인증이 완료된 객체입니다.");
		}
		super.setAuthenticated(false);
	}

	@Override
	public void eraseCredentials() {
		super.eraseCredentials();
		credentials = null;
	}

	@Override
	public String toString() {
		return "JwtAuthenticationToken{"
			+ "principal=" + principal
			+ ", credentials=" + (credentials == null ? "null" : "hidden")
			+ ", authorities=" + getAuthorities()
			+ ", isAuthenticated=" + isAuthenticated()
			+ "}";
	}

}
