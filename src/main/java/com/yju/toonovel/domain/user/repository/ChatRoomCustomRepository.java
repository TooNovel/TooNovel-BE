package com.yju.toonovel.domain.user.repository;

import java.util.List;

import com.yju.toonovel.domain.chatting.entity.ChatRoom;

public interface ChatRoomCustomRepository {
	List<ChatRoom> findByChatRoomMemberCount();
}
