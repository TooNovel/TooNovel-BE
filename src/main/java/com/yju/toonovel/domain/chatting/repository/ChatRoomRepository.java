package com.yju.toonovel.domain.chatting.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.yju.toonovel.domain.chatting.entity.ChatRoom;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
	@Query("select r from ChatRoom r where r.user.userId = :uid")
	Optional<ChatRoom> findByUserId(Long uid);

	@Query("select r from ChatRoom r where r.chatRoomId = :rid and r.user.userId = :uid")
	Optional<ChatRoom> findByChatRoomIdAndUserId(Long rid, Long uid);
}
