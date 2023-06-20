package com.yju.toonovel.domain.chatting.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yju.toonovel.domain.chatting.entity.ChatRoom;
import com.yju.toonovel.domain.chatting.entity.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
	void deleteAllByChatRoom(ChatRoom chatRoom);
}
