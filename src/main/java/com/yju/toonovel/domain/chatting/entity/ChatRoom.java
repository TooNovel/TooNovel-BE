package com.yju.toonovel.domain.chatting.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import com.yju.toonovel.domain.user.entity.User;
import com.yju.toonovel.global.common.entity.BaseEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class ChatRoom extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long chatRoomId;

	@Column(nullable = false)
	private String chatRoomName;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToMany(fetch = FetchType.LAZY)
	private Set<User> users = new HashSet<>();

	@Builder
	public ChatRoom(String chatRoomName, User user) {
		this.chatRoomName = chatRoomName;
		this.user = user;
	}
	public static ChatRoom of(String chatRoomName, User user) {
		return ChatRoom.builder()
			.chatRoomName(chatRoomName)
			.user(user)
			.build();
	}

	public void join(User user) {
		this.users.add(user);
	}

	public void leave(User user) {
		this.users.remove(user);
	}
}
