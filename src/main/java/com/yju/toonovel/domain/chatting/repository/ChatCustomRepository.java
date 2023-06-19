package com.yju.toonovel.domain.chatting.repository;

import java.time.LocalDate;
import java.util.List;

import com.yju.toonovel.domain.chatting.entity.Chat;
import com.yju.toonovel.domain.chatting.entity.ChatRoom;
import com.yju.toonovel.domain.user.entity.User;

public interface ChatCustomRepository {
	List<Chat> findAllByChatRoomToAuthor(ChatRoom chatRoom);

	List<Chat> findAllByChatRoomAndDateToAuthor(ChatRoom chatRoom, LocalDate date);

	List<Chat> findAllByChatRoomToUser(ChatRoom chatRoom, Long userId, User author);

	List<Chat> findAllByChatRoomAndDateToUser(ChatRoom chatRoom, Long userId, User author, LocalDate date);

	List<Chat> findRecentChatByChatRoomAndUser(ChatRoom chatRoom, Long userId, long limit);
}
