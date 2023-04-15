package com.yju.toonovel.domain.post.service;

import java.util.List;

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

	@Transactional
	public PostResponseDto readPost(Long pid) {
		Post post = postRepository.findById(pid).orElseThrow(() -> new RuntimeException());
		post.setViewCount(post.getViewCount() + 1);
		postRepository.save(post);
		return new PostResponseDto(post);
	}

	public List<Post> readPostAll() {
		List<Post> result = postRepository.findAll();
		System.out.println(result);
		return result;
	}

	@Transactional
	public void updatePost(PostRequestDto dto) {
		Post post = postRepository.findById(dto.getPostId()).orElseThrow(() -> new RuntimeException());
		post.setTitle(dto.getTitle());
		post.setContent(dto.getContent());
		post.setCategoryId(dto.getCategory());

		postRepository.save(post);
	}

	public void deletePost(Long pid) {
		postRepository.deleteById(pid);
	}
}
