package com.yju.toonovel.domain.chatting.repository;

import java.time.LocalDate;
import java.util.List;

import com.yju.toonovel.domain.chatting.dto.ReplyDto;
import com.yju.toonovel.domain.chatting.entity.ChatRoom;

public interface ReplyCustomRepository {
	List<ReplyDto> findAllByChatRoomAndDate(ChatRoom chatRoom, LocalDate date);
}
