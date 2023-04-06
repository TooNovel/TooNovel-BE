package com.yju.toonovel.domain.post.service;

import java.util.List;

import org.jetbrains.annotations.NotNull;
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

	public void addPost(@NotNull PostRequestDto dto) {
		postRepository.save(dto.toEntity());
	}

	public PostResponseDto readPost(Long pid) {
		// ToDo Optional 처리 방식 맞추기
		// Optional<Post> result = postRepository.findById(pid);
		//
		// if (result.isPresent()) {
		// 	return new PostResponseDto(result.get());
		// }
		// return null; // ToDo 에러처리 해야함
		return new PostResponseDto(postRepository.findById(pid).get());
	}

	public List<Post> readPostAll() {
		List<Post> result = postRepository.findAll();
		System.out.println(result);
		return result;
	}

	@Transactional
	public void updatePost(PostRequestDto dto) {
		// ToDo userId로 인증하는 과정 추가할 것
		// ToDo Optional 처리 방식 맞추기
		Post post = postRepository.findById(dto.getPostId()).get();
		post.setTitle(dto.getTitle());
		post.setContent(dto.getContent());
		post.setCategoryId(dto.getCategory());

		postRepository.save(post);
	}

	// ToDo Delete
}
