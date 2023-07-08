package com.yju.toonovel.domain.novel.exception;

import com.yju.toonovel.global.error.exception.BusinessException;
import com.yju.toonovel.global.error.exception.ErrorCode;

public class NovelRecommendationNotExistException extends BusinessException {

	public NovelRecommendationNotExistException() {
		super(ErrorCode.NOVEL_RECOMMENDATION_NOT_EXIST);
	}

}
