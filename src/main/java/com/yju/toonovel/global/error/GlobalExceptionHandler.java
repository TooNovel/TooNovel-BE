package com.yju.toonovel.global.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestValueException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.yju.toonovel.global.error.exception.BusinessException;
import com.yju.toonovel.global.error.exception.ErrorCode;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
@RestController
public class GlobalExceptionHandler {

	//비즈니스 에러
	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ErrorResponse> handleBusinessException(final BusinessException ex) {
		log.error("handleEntityNotFoundException", ex);
		final ErrorCode errorCode = ex.getErrorCode();
		final ErrorResponse response = ErrorResponse.of(errorCode);
		return new ResponseEntity<>(response, errorCode.getStatus());
	}

	//바인딩 에러 - @RequestBody, @RequestPart 입력 값 의심
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
		log.info("MethodArgumentNotValidException : {}", ex.getMessage());
		final ErrorResponse response = ErrorResponse.of(ErrorCode.INVALID_INPUT_VALUE);
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	//바인딩 에러 - @RequestParam Enum Type 의심
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(
		MethodArgumentTypeMismatchException ex) {
		log.info("MethodArgumentTypeMismatchException : {}", ex.getMessage());
		final ErrorResponse response = ErrorResponse.of(ErrorCode.INVALID_TYPE_VALUE);
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	//입력 인자 수 부족
	@ExceptionHandler(MissingRequestValueException.class)
	public ResponseEntity<ErrorResponse> handleMissingRequestValueException(MissingRequestValueException ex) {
		log.info("MissingRequestValueException : {}", ex.getMessage());
		final ErrorResponse response = ErrorResponse.of(ErrorCode.MISSING_INPUT_VALUE);
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	//없는 API 요청
	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<ErrorResponse> handleNoHandlerFoundException(NoHandlerFoundException ex) {
		log.info("NoHandlerFoundException : {}", ex.getMessage());
		final ErrorResponse response = ErrorResponse.of(ErrorCode.NOT_EXIST_API);
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	//잘못된 HttpMethod 요청
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(
		HttpRequestMethodNotSupportedException ex) {
		log.info("HttpRequestMethodNotSupportedException : {}", ex.getMessage());
		final ErrorResponse response = ErrorResponse.of(ErrorCode.METHOD_NOT_ALLOWED);
		return new ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED);
	}

	//권한 미보유
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException ex) {
		log.info("AccessDeniedException : {}", ex.getMessage());
		final ErrorResponse response = ErrorResponse.of(ErrorCode.HANDLE_ACCESS_DENIED);
		return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
	}

	//정의되지 않은 모든 에러
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleInternalServerException(Exception ex) {
		log.info("handleInternalServerException : {}", ex.getMessage());
		final ErrorResponse response = ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR);
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
