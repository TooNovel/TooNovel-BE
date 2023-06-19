package com.yju.toonovel.domain.chatting.repository;

import static com.yju.toonovel.domain.chatting.entity.QChat.*;

import java.time.LocalDate;
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
			.fetch();
	}

	@Override
	public List<Chat> findAllByChatRoomAndDateToAuthor(ChatRoom chatRoom, LocalDate date) {
		return jpaQueryFactory
			.selectFrom(chat)
			.where(
				chat.chatRoom.eq(chatRoom),
				chat.createdDate.between(date.atStartOfDay(), date.plusDays(1).atStartOfDay())
			)
			.orderBy(chat.chatId.desc())
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
			.fetch();
	}

	@Override
	public List<Chat> findAllByChatRoomAndDateToUser(ChatRoom chatRoom, Long userId, User author, LocalDate date) {
		return jpaQueryFactory
			.selectFrom(chat)
			.where(
				chat.chatRoom.eq(chatRoom),
				chat.createdDate.between(date.atStartOfDay(), date.plusDays(1).atStartOfDay()),
				chat.user.userId.eq(userId)
					.or(chat.user.eq(author))
			)
			.orderBy(chat.chatId.desc())
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
