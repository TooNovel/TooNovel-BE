package com.yju.toonovel.global.websocket.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.yju.toonovel.domain.user.entity.User;
import com.yju.toonovel.global.common.entity.BaseEntity;

import lombok.RequiredArgsConstructor;

@Entity
@RequiredArgsConstructor
public class ChatRoom extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long chatRoomId;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "creator_id")
	private User creator;
}
