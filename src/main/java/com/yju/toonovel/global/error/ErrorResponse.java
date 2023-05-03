package com.yju.toonovel.global.error;

import org.springframework.http.HttpStatus;

import com.yju.toonovel.global.error.exception.ErrorCode;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ErrorResponse {

	private HttpStatus status;
	private String code;
	private String message;

	private ErrorResponse(final ErrorCode code) {
		this.message = code.getMessage();
		this.status = code.getStatus();
		this.code = code.getCode();
	}

	public static ErrorResponse of(final ErrorCode code) {
		return new ErrorResponse(code);
	}
}
