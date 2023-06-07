package com.yju.toonovel.domain.chatting.repository;

import java.util.List;

import com.yju.toonovel.domain.chatting.entity.Chat;
import com.yju.toonovel.domain.chatting.entity.ChatRoom;
import com.yju.toonovel.domain.user.entity.User;

public interface ChatCustomRepository {
	List<Chat> findAllByChatRoomToAuthor(ChatRoom chatRoom);

	List<Chat> findAllByChatRoomAndChatIdToAuthor(ChatRoom chatRoom, Long chatId);

	List<Chat> findAllByChatRoomToUser(ChatRoom chatRoom, Long userId, User author);

	List<Chat> findAllByChatRoomAndChatIdToUser(ChatRoom chatRoom, Long userId, User author, Long chatId);

	List<Chat> findRecentChatByChatRoomAndUser(ChatRoom chatRoom, Long userId, long limit);
}
