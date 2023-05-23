package com.yju.toonovel.global.websocket;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import com.yju.toonovel.global.security.jwt.JwtTokenProvider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Component
@Slf4j
public class StompHandler implements ChannelInterceptor {
	private final JwtTokenProvider jwtTokenProvider;

	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {
		log.info("StompHandler : It's work!");
		ChannelInterceptor.super.preSend(message, channel);

		StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

		// 연결 시 인증. Filter에서 유효한 토큰인지 확인 하는게 WebSocket에서도 적용되는지 확인 필요
		if (accessor.getCommand() == StompCommand.CONNECT) {
			// 유효한 토큰인지 확인
			jwtTokenProvider.validateToken(accessor.getFirstNativeHeader("Authorization"));
		}

		log.info("StompHandler : It's done!");
		return message;
	}
}
