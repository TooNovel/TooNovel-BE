package com.yju.toonovel.domain.user.repository;

import static com.yju.toonovel.domain.chatting.entity.QChatRoom.*;
import static com.yju.toonovel.domain.user.entity.QUser.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yju.toonovel.domain.chatting.entity.ChatRoom;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ChatRoomCustomRepositoryImpl implements ChatRoomCustomRepository {

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public List<ChatRoom> findByChatRoomMemberCount() {
		return jpaQueryFactory
			.selectFrom(chatRoom)
			.join(chatRoom.user, user).fetchJoin() // n+1 방지
			.orderBy(chatRoom.users.size().desc())
			.limit(10)
			.fetch();
	}
}
