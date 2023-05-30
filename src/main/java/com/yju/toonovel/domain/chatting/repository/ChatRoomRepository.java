package com.yju.toonovel.domain.chatting.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.yju.toonovel.domain.chatting.dto.ChatRoomResponseDto;
import com.yju.toonovel.domain.chatting.entity.ChatRoom;
import com.yju.toonovel.domain.user.entity.User;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
	@Query("select r from ChatRoom r where r.user.userId = :uid")
	Optional<ChatRoom> findByUserId(Long uid);

	@Query("select r from ChatRoom r where r.chatRoomId = :rid and r.user.userId = :uid")
	Optional<ChatRoom> findByChatRoomIdAndUserId(Long rid, Long uid);

	@Query("select new com.yju.toonovel.domain.chatting.dto.ChatRoomResponseDto"
		+ "(r.chatRoomId, r.chatRoomName, u.nickname)"
		+ " from ChatRoom r join r.user u where :user member of r.users")
	List<ChatRoomResponseDto> findAllChatRoomByUser(User user);
}
