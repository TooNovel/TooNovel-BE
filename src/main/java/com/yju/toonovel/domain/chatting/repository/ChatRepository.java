package com.yju.toonovel.domain.chatting.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yju.toonovel.domain.chatting.entity.Chat;

public interface ChatRepository extends JpaRepository<Chat, Long> {

}
