package com.yju.toonovel.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import com.yju.toonovel.domain.chatting.interceptor.CustomChannelInterceptor;
import com.yju.toonovel.domain.chatting.interceptor.CustomHandshakeInterceptor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
@Slf4j
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
	@Value("${server.frontEnd}")
	private String frontEndServer;
	private final CustomChannelInterceptor channelInterceptor;
	private final CustomHandshakeInterceptor handshakeInterceptor;

	// 메세지 브로커 설정
	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		config.enableSimpleBroker("/chat", "/error", "/reply");
		config.setApplicationDestinationPrefixes("/app");
	}

	// 소켓 연결 엔드 포인트 설정
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/ws")
			.setAllowedOriginPatterns("http://localhost:3000/", frontEndServer)
			.addInterceptors(handshakeInterceptor)
			.withSockJS(); // 프론트 구성에 따라 변경될 수 있음
	}

	@Override
	public void configureClientInboundChannel(ChannelRegistration registration) {
		log.info("WebSocketConfig - configureClientInboundChannel");
		registration.interceptors(channelInterceptor);
	}
}
