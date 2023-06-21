package com.yju.toonovel.domain.chatting.repository;

import static com.yju.toonovel.domain.chatting.entity.QChat.*;
import static com.yju.toonovel.domain.chatting.entity.QReply.*;
import static com.yju.toonovel.domain.user.entity.QUser.*;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yju.toonovel.domain.chatting.dto.ReplyDto;
import com.yju.toonovel.domain.chatting.entity.ChatRoom;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ReplyCustomRepositoryImpl implements ReplyCustomRepository {

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public List<ReplyDto> findAllByChatRoomAndDate(ChatRoom chatRoom, LocalDate date) {
		return jpaQueryFactory
			.select(
				Projections.fields(
					ReplyDto.class,
					reply.replyId,
					reply.user.userId.as("senderId"),
					reply.user.nickname.as("senderName"),
					reply.message.as("replyMessage"),
					reply.createdDate,
					reply.chat.chatId,
					reply.chat.message.as("userMessage"),
					reply.chat.user.nickname.as("userName")
				)
			)
			.from(reply)
			.join(reply.chat, chat)
			.join(reply.user, user)
			.join(reply.chat.user, user)
			.where(
				reply.chatRoom.eq(chatRoom),
				makeWhereCondition(date)
				)
			.fetch();
	}

	private Predicate makeWhereCondition(LocalDate date) {
		return (date == null)
			? reply.createdDate.between(
				LocalDate.now().minusDays(7).atStartOfDay(), LocalDate.now().plusDays(1).atStartOfDay()
		)
			: reply.createdDate.between(date.atStartOfDay(), date.plusDays(1).atStartOfDay());
	}
}
