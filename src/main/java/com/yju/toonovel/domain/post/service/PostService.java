package com.yju.toonovel.domain.post.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yju.toonovel.domain.post.dto.PostAllResponseDto;
import com.yju.toonovel.domain.post.dto.PostPaginationRequestDto;
import com.yju.toonovel.domain.post.dto.PostRegisterRequestDto;
import com.yju.toonovel.domain.post.dto.PostResponseDto;
import com.yju.toonovel.domain.post.dto.PostUpdateRequestDto;
import com.yju.toonovel.domain.post.entity.Post;
import com.yju.toonovel.domain.post.exception.PostNotFoundException;
import com.yju.toonovel.domain.post.exception.PostNotMatchUserException;
import com.yju.toonovel.domain.post.repository.PostRepository;
import com.yju.toonovel.domain.post.repository.PostRepositoryImpl;
import com.yju.toonovel.domain.user.entity.User;
import com.yju.toonovel.domain.user.exception.UserNotFoundException;
import com.yju.toonovel.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {

	private final PostRepository postRepository;
	private final PostRepositoryImpl postRepositoryImpl;
	private final UserRepository userRepository;

	// 게시글 등록
	@Transactional
	public void postRegister(PostRegisterRequestDto dto, Long uid) {
		User user = userRepository.findByUserId(uid)
			.orElseThrow(() -> new UserNotFoundException());

		postRepository.save(Post.of(user, dto.getCategory(), dto.getTitle(), dto.getContent()));
	}

	// 게시글 부분 조회
	@Transactional
	public PostResponseDto getPost(Long pid) {
		Post post = postRepository.findByPostId(pid)
			.orElseThrow(() -> new PostNotFoundException());
		post.increaseViewCount(post.getViewCount());
		return new PostResponseDto(post);
	}

	// 게시글 전체 조회
	@Transactional
	public Page<PostAllResponseDto> getAllPost(PostPaginationRequestDto requestDto) {
		Pageable pageable = PageRequest.of(requestDto.getPage(), requestDto.getLimit());
		return postRepositoryImpl.getAllPost(pageable, requestDto);
	}

	// 게시글 수정
	@Transactional
	public void updatePost(PostUpdateRequestDto dto, Long uid, Long pid) {

		userRepository.findByUserId(uid)
			.orElseThrow(() -> new UserNotFoundException());

		Post post = postRepository.findByPostId(pid)
			.orElseThrow(() -> new PostNotFoundException());

		post.updatePost(dto.getTitle(), dto.getContent(), dto.getCategory());
	}

	// 게시글 삭제
	@Transactional
	public void deletePost(Long pid, Long uid) {

		User user = userRepository.findByUserId(uid)
			.orElseThrow(() -> new UserNotFoundException());

		Post post = postRepository.findByPostId(pid)
			.orElseThrow(() -> new PostNotFoundException());

		postRepository.deleteById(
			valiationPost(user.getUserId(), post.getPostId()).getPostId()
		);
	}

	// 게시글 검증
	public Post valiationPost(Long uid, Long pid) {
		return postRepository.findByPostIdAndUserId(uid, pid)
			.orElseThrow(() -> new PostNotMatchUserException());
	}

}
