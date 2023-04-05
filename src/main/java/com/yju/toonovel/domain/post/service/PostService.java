package com.yju.toonovel.domain.post.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yju.toonovel.domain.post.dto.PostRequestDto;
import com.yju.toonovel.domain.post.dto.PostResponseDto;
import com.yju.toonovel.domain.post.entity.Post;
import com.yju.toonovel.domain.post.repository.PostRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

	private final PostRepository postRepository;

	public void addPost(PostRequestDto dto) {
		postRepository.save(dto.toEntity());
	}

	public PostResponseDto readPost(Long pid) {
		Optional<Post> result = postRepository.findById(pid);

		if (result.isPresent()) {
			return new PostResponseDto(result.get());
		}
		return null; // ToDo 에러처리 해야함
	}

	// ToDo 전체 목록 Read

	// ToDo Update

	// ToDo Delete
}
