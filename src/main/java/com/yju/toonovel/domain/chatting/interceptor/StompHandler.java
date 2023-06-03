package com.yju.toonovel.domain.chatting.interceptor;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import com.yju.toonovel.global.security.jwt.JwtAuthentication;
import com.yju.toonovel.global.security.jwt.JwtAuthenticationToken;
import com.yju.toonovel.global.security.jwt.JwtTokenProvider;
import com.yju.toonovel.global.security.jwt.service.TokenService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Component
@Slf4j
public class StompHandler implements ChannelInterceptor {
	private final JwtTokenProvider jwtTokenProvider;
	private final TokenService tokenService;

	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {
		StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
		JwtAuthentication user = null;

		if (accessor.getCommand() == StompCommand.CONNECT) {
			// 유효한 토큰인지 확인. http 프로토콜이 아니므로 따로 적용해야 함
			jwtTokenProvider.validateToken(accessor.getFirstNativeHeader("Authorization"));
			JwtAuthenticationToken token =
				tokenService.getAuthentication(accessor.getFirstNativeHeader("Authorization"));
			user = (JwtAuthentication) token.getPrincipal();
		}

		try {
			log.info("-------------StompHandler.preSend userId:" + user.userId + "---------------");
		} catch (NullPointerException e) {
			log.info("-------------StompHandler.preSend---------------");
		}
		return message;
	}
}