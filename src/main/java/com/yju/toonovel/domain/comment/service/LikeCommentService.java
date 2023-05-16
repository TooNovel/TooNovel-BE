package com.yju.toonovel.domain.comment.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yju.toonovel.domain.comment.entity.Comment;
import com.yju.toonovel.domain.comment.entity.LikeComment;
import com.yju.toonovel.domain.comment.exception.CommentAlreadyLikedException;
import com.yju.toonovel.domain.comment.exception.CommentNotFoundException;
import com.yju.toonovel.domain.comment.repository.CommentRepository;
import com.yju.toonovel.domain.comment.repository.LikeCommentRepository;
import com.yju.toonovel.domain.user.entity.User;
import com.yju.toonovel.domain.user.exception.UserNotFoundException;
import com.yju.toonovel.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class LikeCommentService {

	private final LikeCommentRepository likeCommentRepository;
	private final UserRepository userRepository;
	private final CommentRepository commentRepository;

	//좋아요 등록 및 좋아요 카운트 증가
	@Transactional
	public void commentLikeRegister(Long uid, Long cid) {
		User user = userRepository.findByUserId(uid)
			.orElseThrow(() -> new UserNotFoundException());

		Comment comment = commentRepository.findByCommentId(cid)
			.orElseThrow(() -> new CommentNotFoundException());

		LikeComment likeComment = findUserIdAndCommentId(user, comment);

		if (likeComment.isActived()) {
			throw new CommentAlreadyLikedException();
		}
		likeComment.toggleLike();

		comment.clickCommentLike();
	}

	public LikeComment findUserIdAndCommentId(User user, Comment comment) {
		return likeCommentRepository.findLikeCommentIdByUserIdAndCommentId(user, comment)
			.orElseGet(
				() -> likeCommentRepository.save(LikeComment.of(user, comment))
			);
	}
}
