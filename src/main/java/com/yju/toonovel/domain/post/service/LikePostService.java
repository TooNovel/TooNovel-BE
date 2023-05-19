package com.yju.toonovel.domain.post.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yju.toonovel.domain.post.entity.LikePost;
import com.yju.toonovel.domain.post.entity.Post;
import com.yju.toonovel.domain.post.exception.PostAlreadyLikedException;
import com.yju.toonovel.domain.post.exception.PostNotFoundException;
import com.yju.toonovel.domain.post.repository.LikePostRepository;
import com.yju.toonovel.domain.post.repository.PostRepository;
import com.yju.toonovel.domain.user.entity.User;
import com.yju.toonovel.domain.user.exception.UserNotFoundException;
import com.yju.toonovel.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class LikePostService {

	private final LikePostRepository likePostRepository;
	private final UserRepository userRepository;
	private final PostRepository postRepository;

	@Transactional
	public void postLikeRegister(Long uid, Long pid) {
		User user = userRepository.findByUserId(uid)
			.orElseThrow(() -> new UserNotFoundException());

		Post post = postRepository.findByPostId(pid)
			.orElseThrow(() -> new PostNotFoundException());

		LikePost likePost = findUserIdAndPostId(user, post);

		if (likePost.isActived()) {
			throw new PostAlreadyLikedException();
		}
		likePost.toggleLike();

		post.clickPostLike();
	}

	public LikePost findUserIdAndPostId(User user, Post post) {
		return likePostRepository.findLikePostIdByUserIdAndPostId(user, post)
			.orElseGet(
				() -> likePostRepository.save(LikePost.of(user, post))
			);
	}
}
