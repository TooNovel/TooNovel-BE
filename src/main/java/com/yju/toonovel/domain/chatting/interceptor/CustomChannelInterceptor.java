package com.yju.toonovel.domain.chatting.interceptor;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Component
@Slf4j
public class CustomChannelInterceptor implements ChannelInterceptor {

	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {
		// 현재 리팩토링을 통해 기능을 전부 CustomHandshakeInterceptor에 넘겨주었으나 추후 추가 가능성을 생각해서 삭제하지 않았습니다.
		return message;
	}
}
