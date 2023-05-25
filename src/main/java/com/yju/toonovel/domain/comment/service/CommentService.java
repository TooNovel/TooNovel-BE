package com.yju.toonovel.domain.comment.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yju.toonovel.domain.comment.dto.CommentAllResponseDto;
import com.yju.toonovel.domain.comment.dto.CommentPaginationRequestDto;
import com.yju.toonovel.domain.comment.dto.CommentRegisterRequestDto;
import com.yju.toonovel.domain.comment.dto.CommentUpdateRequestDto;
import com.yju.toonovel.domain.comment.entity.Comment;
import com.yju.toonovel.domain.comment.exception.CommentNotFoundException;
import com.yju.toonovel.domain.comment.exception.CommentNotMatchUserException;
import com.yju.toonovel.domain.comment.exception.DuplicateCommentException;
import com.yju.toonovel.domain.comment.repository.CommentRepository;
import com.yju.toonovel.domain.comment.repository.CommentRepositoryImpl;
import com.yju.toonovel.domain.post.entity.Post;
import com.yju.toonovel.domain.post.exception.PostNotFoundException;
import com.yju.toonovel.domain.post.repository.PostRepository;
import com.yju.toonovel.domain.user.entity.User;
import com.yju.toonovel.domain.user.exception.UserNotFoundException;
import com.yju.toonovel.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {

	private final CommentRepository commentRepository;
	private final CommentRepositoryImpl commentRepositoryImpl;
	private final UserRepository userRepository;
	private final PostRepository postRepository;

	//댓글 작성
	@Transactional
	public void commentRegister(CommentRegisterRequestDto dto, Long postId, Long userId) {
		User user = userRepository.findByUserId(userId)
			.orElseThrow(() -> new UserNotFoundException());

		Post post = postRepository.findByPostId(postId)
			.orElseThrow(() -> new PostNotFoundException());

		commentRepository.findCommentByPostIdAndUserId(user.getUserId(), post.getPostId())
			.ifPresentOrElse(
				review1 -> new DuplicateCommentException(),
				() -> {
					commentRepository.save(Comment.of(user, post, dto.getCommentContent()));
				});
	}

	//댓글 삭제
	@Transactional
	public void deleteComment(Long cid, Long uid) {

		User user = userRepository.findByUserId(uid)
			.orElseThrow(() -> new UserNotFoundException());

		Comment comment = commentRepository.findByCommentId(cid)
			.orElseThrow(() -> new CommentNotFoundException());

		commentRepository.deleteById(
			validationComment(user.getUserId(), comment.getCommentId()).getCommentId()
		);
	}

	//댓글 수정(내용만 수정 가능)
	@Transactional
	public void updateComment(CommentUpdateRequestDto dto, Long commentId, Long userId) {

		userRepository.findByUserId(userId)
			.orElseThrow(() -> new UserNotFoundException());

		Comment comment = commentRepository.findByCommentId(commentId)
				.orElseThrow(() -> new CommentNotFoundException());

		validationComment(userId, commentId);

		comment.updateContent(dto.getCommentContent());
	}

	//댓글 수정 api 요청을 한 유저가 작성한 댓글이 맞는지 검증
	public Comment validationComment(Long uid, Long cid) {
		return commentRepository.findByCommentIdAndUserId(uid, cid)
			.orElseThrow(() -> new CommentNotMatchUserException());
	}

	//한 게시글에 대한 댓글 조회
	public Page<CommentAllResponseDto> getAllCommentByPost(
		Long pid, CommentPaginationRequestDto requestDto) {

		postRepository.findByPostId(pid)
			.orElseThrow(() -> new PostNotFoundException());

		Pageable pageable = PageRequest.of(requestDto.getPage(), requestDto.getLimit());
		return commentRepositoryImpl.getAllComment(pid, pageable, requestDto);
	}
}
