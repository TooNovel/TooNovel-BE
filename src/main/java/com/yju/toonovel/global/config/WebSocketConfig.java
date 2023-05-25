package com.yju.toonovel.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import com.yju.toonovel.global.websocket.interceptor.StompHandler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
@Slf4j
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
	private final StompHandler stompHandler;

	// 메세지 브로커 설정
	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		config.enableSimpleBroker("/chat");
		config.setApplicationDestinationPrefixes("/app");
	}

	// 소켓 연결 엔드 포인트 설정. 현재 CORS 떄문에 *로 지정했지만 추후 변경 필요
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/ws")
			.setAllowedOriginPatterns("*")
			.withSockJS(); // 프론트 구성에 따라 변경될 수 있음
	}

	@Override
	public void configureClientInboundChannel(ChannelRegistration registration) {
		log.info("WebSocketConfig - configureClientInboundChannel");
		registration.interceptors(stompHandler);
	}
}
