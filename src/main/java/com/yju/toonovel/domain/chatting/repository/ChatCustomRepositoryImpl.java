package com.yju.toonovel.domain.chatting.repository;

import static com.yju.toonovel.domain.chatting.entity.QChat.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yju.toonovel.domain.chatting.entity.Chat;
import com.yju.toonovel.domain.chatting.entity.ChatRoom;
import com.yju.toonovel.domain.user.entity.User;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ChatCustomRepositoryImpl implements ChatCustomRepository {

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public List<Chat> findAllByChatRoomToAuthor(ChatRoom chatRoom) {
		return jpaQueryFactory
			.selectFrom(chat)
			.where(chat.chatRoom.eq(chatRoom))
			.orderBy(chat.chatId.desc())
			.limit(30)
			.fetch();
	}

	@Override
	public List<Chat> findAllByChatRoomAndChatIdToAuthor(ChatRoom chatRoom, Long chatId) {
		return jpaQueryFactory
			.selectFrom(chat)
			.where(
				chat.chatRoom.eq(chatRoom),
				chat.chatId.lt(chatId)
			)
			.orderBy(chat.chatId.desc())
			.limit(30)
			.fetch();
	}

	@Override
	public List<Chat> findAllByChatRoomToUser(ChatRoom chatRoom, Long userId, User author) {
		return jpaQueryFactory
			.selectFrom(chat)
			.where(
				chat.chatRoom.eq(chatRoom),
				chat.user.userId.eq(userId)
					.or(chat.user.eq(author))
			)
			.orderBy(chat.chatId.desc())
			.limit(30)
			.fetch();
	}

	@Override
	public List<Chat> findAllByChatRoomAndChatIdToUser(ChatRoom chatRoom, Long userId, User author, Long chatId) {
		return jpaQueryFactory
			.selectFrom(chat)
			.where(
				chat.chatRoom.eq(chatRoom),
				chat.chatId.lt(chatId),
				chat.user.userId.eq(userId)
					.or(chat.user.eq(author))
			)
			.orderBy(chat.chatId.desc())
			.limit(30)
			.fetch();
	}

	@Override
	public List<Chat> findRecentChatByChatRoomAndUser(ChatRoom chatRoom, Long userId, long limit) {
		return jpaQueryFactory
			.selectFrom(chat)
			.where(
				chat.chatRoom.eq(chatRoom),
				chat.user.userId.eq(userId)
			)
			.orderBy(chat.chatId.desc())
			.limit(limit)
			.fetch();
	}
}
