package com.yju.toonovel.domain.novel.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yju.toonovel.domain.novel.dto.NovelDetailResponseDto;
import com.yju.toonovel.domain.novel.dto.NovelPaginationResponseDto;
import com.yju.toonovel.domain.novel.dto.UserLikeCheckResponseDto;
import com.yju.toonovel.domain.novel.entity.LikeNovel;
import com.yju.toonovel.domain.novel.entity.Novel;
import com.yju.toonovel.domain.novel.entity.Platform;
import com.yju.toonovel.domain.novel.exception.NovelNotFoundException;
import com.yju.toonovel.domain.novel.exception.PlatformNotFoundException;
import com.yju.toonovel.domain.novel.repository.LikeNovelRepository;
import com.yju.toonovel.domain.novel.repository.NovelPlatformRepository;
import com.yju.toonovel.domain.novel.repository.NovelRepository;
import com.yju.toonovel.domain.novel.repository.PlatformRepository;
import com.yju.toonovel.domain.user.entity.User;
import com.yju.toonovel.domain.user.exception.UserNotFoundException;
import com.yju.toonovel.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NovelService {

	private final LikeNovelRepository likeNovelRepository;
	private final NovelRepository novelRepository;
	private final UserRepository userRepository;
	private final NovelPlatformRepository novelPlatformRepository;
	private final PlatformRepository platformRepository;

	@Transactional
	public List<NovelPaginationResponseDto> findAll(Long novelId) {
		return novelRepository
			.findAllNovel(novelId)
			.stream()
			.map(novel -> NovelPaginationResponseDto.from(novel))
			.collect(Collectors.toList());
	}

	public NovelDetailResponseDto findById(Long novelId) {
		Novel novel = novelRepository.findById(novelId)
			.orElseThrow(() -> new NovelNotFoundException());

		List<Platform> platforms = new ArrayList<>();

		novelPlatformRepository.findAllByNovel(novel).forEach(
			platform -> platforms.add(
				platformRepository
					.findById(platform.getId())
					.orElseThrow(() -> new PlatformNotFoundException()))
		);

		return new NovelDetailResponseDto(novel, platforms);
	}

	@Transactional
	public void toggleNovelLike(Long userId, Long novelId) {

		User user = userRepository.findByUserId(userId)
			.orElseThrow(() -> new UserNotFoundException());

		Novel novel = novelRepository.findById(novelId)
			.orElseThrow(() -> new NovelNotFoundException());

		LikeNovel likeNovel = findUserLikeNovel(user, novel);

		likeNovel.toggleLike();
	}

	@Transactional
	public LikeNovel findUserLikeNovel(User user, Novel novel) {

		return likeNovelRepository
			.findByUserAndNovel(user, novel)
			.orElseGet(() -> likeNovelRepository.save(LikeNovel.of(user, novel)));
	}

	@Transactional
	public UserLikeCheckResponseDto isUserLikes(
		long userId,
		long novelId
	) {

		novelRepository
			.findById(novelId)
			.orElseThrow(() -> new NovelNotFoundException());

		return likeNovelRepository
			.findByUserAndNovel(userId, novelId)
			.map(likeNovel -> likeNovel.isActived())
			.map(isActived -> UserLikeCheckResponseDto.from(isActived))
			.orElseGet(() -> UserLikeCheckResponseDto.from(false));
	}

}