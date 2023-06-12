package com.yju.toonovel.domain.chatting.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.yju.toonovel.domain.user.entity.User;
import com.yju.toonovel.global.common.entity.BaseEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Reply extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long replyId;

	@Column(nullable = false)
	private String message;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "chat_room")
	private ChatRoom chatRoom;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sender_id")
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	private Chat chat;

	@Builder
	public Reply(String message, ChatRoom chatRoom, User user, Chat chat) {
		this.message = message;
		this.chatRoom = chatRoom;
		this.user = user;
		this.chat = chat;
	}

	public static Reply of(String message, ChatRoom chatRoom, User user, Chat chat) {
		return Reply.builder()
			.message(message)
			.chatRoom(chatRoom)
			.user(user)
			.chat(chat)
			.build();
	}
}
