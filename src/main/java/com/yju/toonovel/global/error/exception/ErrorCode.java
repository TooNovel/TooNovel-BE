package com.yju.toonovel.global.error.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ErrorCode {

	// Common
	INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "C001", "잘못된 입력 값입니다."),
	INVALID_TYPE_VALUE(HttpStatus.BAD_REQUEST, "C002", "잘못된 타입입니다."),
	MISSING_INPUT_VALUE(HttpStatus.BAD_REQUEST, "C003", "입력된 인자의 수가 부족합니다."),
	NOT_EXIST_API(HttpStatus.BAD_REQUEST, "C004", "요청 API 주소가 올바르지 않습니다."),
	METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "C005", "지원되지 않는 메서드입니다."),
	HANDLE_ACCESS_DENIED(HttpStatus.FORBIDDEN, "C006", "접근 권한이 없습니다."),
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "C007", "서버 에러입니다."),

	// User
	USER_NOT_FOUND(HttpStatus.NOT_FOUND, "U001", "존재하지 않는 사용자입니다."),
	USER_INVALID(HttpStatus.BAD_REQUEST, "U002", "권한이 없는 사용자입니다."),
	ALREADY_AUTHOR(HttpStatus.BAD_REQUEST, "U003", "이 유저는 이미 작가입니다."),
	ADMIN_AUTHENTICATION_FAIL(HttpStatus.BAD_REQUEST, "U004", "관리자만 가능한 요청입니다."),

	// Token
	REFRESH_TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "A001", "존재하지 않는 리프레쉬 토큰입니다."),
	TOKEN_INVALID(HttpStatus.UNAUTHORIZED, "A002", "유효하지 않은 토큰입니다."),
	TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "A003", "만료된 토큰입니다."),

	//Novel
	NOVEL_NOT_FOUND(HttpStatus.NOT_FOUND, "N001", "존재하지 않는 작품입니다."),
	PLATFORM_NOT_FOUND(HttpStatus.NOT_FOUND, "N002", "존재하지 않는 플랫폼입니다."),
	NOVEL_GENRE_NOT_FOUND(HttpStatus.NOT_FOUND, "N003", "존재하지 않는 장르입니다."),
	AUTHOR_NOT_FOUND(HttpStatus.NOT_FOUND, "N004", "존재하지 않는 작가입니다."),

	//Review
	REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND, "R001", "존재하지 않는 리뷰입니다."),
	REVIEW_DUPLICATE(HttpStatus.BAD_REQUEST, "R002", "이미 리뷰를 작성한 작품입니다."),
	REVIEW_NOT_MATCH_USER(HttpStatus.FORBIDDEN, "R003", "유저가 작성한 리뷰가 아닙니다."),
	REVIEW_ALREADY_LIKED(HttpStatus.BAD_REQUEST, "R004", "이미 좋아요가 되어 있습니다."),

	//Post
	POST_NOT_FOUND(HttpStatus.NOT_FOUND, "P001", "존재하지 않는 글입니다."),
	POST_NOT_MATCH_USER(HttpStatus.FORBIDDEN, "P002", "유저가 작성한 게시글이 아닙니다."),
	POST_ALREADY_LIKED(HttpStatus.BAD_REQUEST, "P003", "이미 좋아요가 되어 있습니다."),

	//Comment
	COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "M001", "존재하지 않는 댓글입니다."),
	COMMENT_NOT_MATCH_USER(HttpStatus.FORBIDDEN, "M002", "유저가 작성한 댓글이 아닙니다."),
	COMMENT_ALREADY_LIKED(HttpStatus.BAD_REQUEST, "M003", "이미 좋아요가 되어 있습니다."),

	//Enroll
	ENROLL_NOT_FOUND(HttpStatus.NOT_FOUND, "E001", "유효하지 않은 작가신청 입니다."),
	ENROLL_NOT_MATCH_USER(HttpStatus.FORBIDDEN, "E002", "해당 유저가 보낸 작가 신청이 아닙니다."),

	//Chat
	CHAT_ROOM_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "H001", "이미 채팅방이 존재합니다."),
	CHAT_ROOM_NOT_FOUNT(HttpStatus.NOT_FOUND, "H002", "존재하지 않는 채팅방입니다."),
	CHAT_ROOM_NOT_MATCH_USER(HttpStatus.FORBIDDEN, "H003", "유저가 생성한 채팅방이 아닙니다."),
	CHAT_ROOM_ALREADY_JOIN(HttpStatus.BAD_REQUEST, "H004", "이미 가입된 채팅방입니다."),
	CHAT_ROOM_NOT_JOIN(HttpStatus.FORBIDDEN, "H005", "가입되어 있지 않은 채팅방입니다."),
	CHAT_COUNT_LIMIT(HttpStatus.TOO_MANY_REQUESTS, "H006", "일일 채팅 횟수를 초과했습니다"),
	CHAT_NOT_FOUNT(HttpStatus.NOT_FOUND, "H007", "존재하지 않는 채팅입니다."),
	AUTHOR_CANNOT_LEAVE_CHATROOM(HttpStatus.FORBIDDEN, "H008", "작가는 채팅방을 탈퇴할 수 없습니다.");

	private final HttpStatus status;
	private final String code;
	private final String message;

	ErrorCode(HttpStatus status, String code, String message) {
		this.status = status;
		this.code = code;
		this.message = message;
	}
}
