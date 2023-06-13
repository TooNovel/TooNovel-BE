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
public class Chat extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long chatId;

	@Column(nullable = false, length = 300)
	private String message;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "chat_room")
	private ChatRoom chatRoom;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sender_id")
	private User user;

	private boolean isFiltered;

	@Builder
	public Chat(String message, ChatRoom chatRoom, User user, boolean isFiltered) {
		this.message = message;
		this.chatRoom = chatRoom;
		this.user = user;
		this.isFiltered = isFiltered;
	}

	public static Chat of(String message, ChatRoom chatRoom, User user, boolean isFiltered) {
		return Chat.builder()
			.message(message)
			.chatRoom(chatRoom)
			.user(user)
			.isFiltered(isFiltered)
			.build();
	}

	public void filteringChat() {
		isFiltered = !isFiltered;
	}
}
