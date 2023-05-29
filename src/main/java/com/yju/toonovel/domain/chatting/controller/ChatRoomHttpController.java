package com.yju.toonovel.domain.chatting.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.yju.toonovel.domain.chatting.dto.ChatRoomAllRequestDto;
import com.yju.toonovel.domain.chatting.service.ChatRoomService;
import com.yju.toonovel.global.security.jwt.JwtAuthentication;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "채팅방 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/chat")
public class ChatRoomHttpController {

	private final ChatRoomService chatRoomService;

	@Operation(summary = "채팅방 생성")
	@ApiResponse(responseCode = "201", description = "요청 성공")
	@ApiResponse(responseCode = "403", description = "요청한 유저가 작가가 아닐 때")
	@PostMapping()
	@ResponseStatus(HttpStatus.CREATED)
	public void chatRoomRegister(@AuthenticationPrincipal JwtAuthentication user,
		@RequestBody ChatRoomAllRequestDto dto) {
		chatRoomService.registerChatRoom(dto, user.userId);
	}

	@Operation(summary = "채팅방 삭제")
	@ApiResponse(responseCode = "204", description = "요청 성공")
	@ApiResponse(responseCode = "403", description = "요청한 유저가 해당 채팅방의 생성자가 아닐 때")
	@ApiResponse(responseCode = "404", description = "해당 유저가 생성한 채팅방이 없을 때")
	@DeleteMapping("{rid}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteChatRoom(@AuthenticationPrincipal JwtAuthentication user, @PathVariable("rid") Long rid) {
		chatRoomService.deleteChatRoom(rid, user.userId);
	}
}
