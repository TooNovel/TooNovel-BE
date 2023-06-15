package com.yju.toonovel.domain.chatting.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.yju.toonovel.domain.chatting.dto.ChatDto;
import com.yju.toonovel.domain.chatting.dto.ChatRoomCreateRequestDto;
import com.yju.toonovel.domain.chatting.dto.ChatRoomResponseDto;
import com.yju.toonovel.domain.chatting.service.ChatRoomService;
import com.yju.toonovel.global.security.jwt.JwtAuthentication;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "채팅방 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/chat")
@Slf4j
public class ChatRoomHttpController {

	private final ChatRoomService chatRoomService;

	@Operation(summary = "채팅방 생성")
	@ApiResponse(responseCode = "201", description = "요청 성공")
	@ApiResponse(responseCode = "400", description = "이미 채팅방이 있는데, 생성 요청을 보냈을 때")
	@ApiResponse(responseCode = "403", description = "요청한 유저가 작가가 아닐 때")
	@PostMapping()
	@ResponseStatus(HttpStatus.CREATED)
	public void createChatRoom(@AuthenticationPrincipal JwtAuthentication user,
		@RequestBody ChatRoomCreateRequestDto dto) {
		chatRoomService.createChatRoom(dto, user.userId);
	}

	@Operation(summary = "채팅방 삭제")
	@ApiResponse(responseCode = "204", description = "요청 성공")
	@ApiResponse(responseCode = "403", description = "요청한 유저가 해당 채팅방의 생성자가 아닐 때")
	@ApiResponse(responseCode = "404", description = "해당 채팅방이 없을 때")
	@DeleteMapping("{rid}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteChatRoom(@AuthenticationPrincipal JwtAuthentication user, @PathVariable("rid") Long rid) {
		chatRoomService.deleteChatRoom(rid, user.userId);
	}

	@Operation(summary = "채팅방 가입")
	@ApiResponse(responseCode = "201", description = "요청 성공")
	@ApiResponse(responseCode = "400", description = "이미 가입된 채팅방일 때")
	@ApiResponse(responseCode = "404", description = "해당 채팅방이 없을 때")
	@PostMapping("{rid}")
	@ResponseStatus(HttpStatus.CREATED)
	public void joinChatRoom(@AuthenticationPrincipal JwtAuthentication user,
		@PathVariable Long rid) {
		chatRoomService.joinChatRoom(rid, user.userId);
	}

	@Operation(summary = "채팅방 탈퇴")
	@ApiResponse(responseCode = "204", description = "요청 성공")
	@ApiResponse(responseCode = "403", description = "작가가 탈퇴 요청을 했을 때")
	@ApiResponse(responseCode = "404", description = "해당 채팅방이 없을 때")
	@ApiResponse(responseCode = "409", description = "요청한 유저가 해당 채팅방에 가입되어 있지 않을 때")
	@DeleteMapping("/leave/{rid}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void leaveChatRoom(@AuthenticationPrincipal JwtAuthentication user,
		@PathVariable Long rid) {
		chatRoomService.leaveChatRoom(rid, user.userId);
	}

	@Operation(summary = "자신이 가입한 채팅방 리스트 조회")
	@ApiResponse(responseCode = "200", description = "요청 성공")
	@GetMapping()
	@ResponseStatus(HttpStatus.OK)
	public List<ChatRoomResponseDto> getAllChatRoom(@AuthenticationPrincipal JwtAuthentication user) {
		return chatRoomService.getAllChatRoom(user.userId);
	}

	@Operation(summary = "채팅 목록 조회")
	@ApiResponse(responseCode = "200", description = "요청 성공")
	@ApiResponse(responseCode = "404", description = "해당 채팅방이 없을 때")
	@ApiResponse(responseCode = "409", description = "요청한 유저가 해당 채팅방에 가입되어 있지 않을 때")
	@GetMapping("{rid}")
	@ResponseStatus(HttpStatus.OK)
	public List<ChatDto> getChatListByAuthor(
		@AuthenticationPrincipal JwtAuthentication user,
		@PathVariable("rid") Long rid,
		@RequestParam(required = false) Long chatId) {
		return chatRoomService.getChatList(user.userId, rid, chatId);
	}
}
