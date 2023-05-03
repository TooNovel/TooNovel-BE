package com.yju.toonovel.global.error;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestValueException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.yju.toonovel.global.error.exception.ErrorCode;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
@RestController
public class GlobalExceptionHandler {

	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
		log.info("MethodArgumentNotValidException : {}", ex.getMessage());
		return new ErrorResponse(ErrorCode.INVALID_INPUT_VALUE);
	}

	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ErrorResponse handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
		log.info("MethodArgumentTypeMismatchException : {}", ex.getMessage());
		return new ErrorResponse(ErrorCode.INVALID_TYPE_VALUE);
	}

	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MissingRequestValueException.class)
	public ErrorResponse handleMissingRequestValueException(MissingRequestValueException ex) {
		log.info("MissingRequestValueException : {}", ex.getMessage());
		return new ErrorResponse(ErrorCode.MISSING_INPUT_VALUE);
	}

	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(NoHandlerFoundException.class)
	public ErrorResponse handleNoHandlerFoundException(NoHandlerFoundException ex) {
		log.info("NoHandlerFoundException : {}", ex.getMessage());
		return new ErrorResponse(ErrorCode.NOT_EXIST_API);
	}

	@ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED)
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ErrorResponse handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
		log.info("HttpRequestMethodNotSupportedException : {}", ex.getMessage());
		return new ErrorResponse(ErrorCode.METHOD_NOT_ALLOWED);
	}

	@ResponseStatus(value = HttpStatus.FORBIDDEN)
	@ExceptionHandler(AccessDeniedException.class)
	public ErrorResponse handleAccessDeniedException(AccessDeniedException ex) {
		log.info("AccessDeniedException : {}", ex.getMessage());
		return new ErrorResponse(ErrorCode.HANDLE_ACCESS_DENIED);
	}

	// @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	// @ExceptionHandler(Exception.class)
	// public ErrorResponse handleInternalServerException(Exception ex) {
	// 	log.info("handleInternalServerException : {}", ex.getMessage());
	// 	return new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR);
	// }

}
