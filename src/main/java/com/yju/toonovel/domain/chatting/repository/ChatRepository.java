package com.yju.toonovel.domain.chatting.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yju.toonovel.domain.chatting.entity.Chat;
import com.yju.toonovel.domain.chatting.entity.ChatRoom;

public interface ChatRepository extends JpaRepository<Chat, Long> {
	void deleteAllInBatchByChatRoom(ChatRoom chatRoom);
}
