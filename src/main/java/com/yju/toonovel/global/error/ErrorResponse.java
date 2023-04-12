package com.yju.toonovel.global.error;

import org.springframework.http.HttpStatus;

import com.yju.toonovel.global.error.exception.ErrorCode;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ErrorResponse {

	private HttpStatus status;
	private String code;
	private String message;

	@Builder
	public ErrorResponse(ErrorCode errorCode) {
		this.status = errorCode.getStatus();
		this.code = errorCode.getCode();
		this.message = errorCode.getMessage();
	}
}
