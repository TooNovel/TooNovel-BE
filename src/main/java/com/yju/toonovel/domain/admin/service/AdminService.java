package com.yju.toonovel.domain.admin.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yju.toonovel.domain.admin.dto.EnrollListPaginationRequestDto;
import com.yju.toonovel.domain.admin.dto.EnrollListResponseDto;
import com.yju.toonovel.domain.admin.dto.EnrollUpdateRequestDto;
import com.yju.toonovel.domain.admin.entity.EnrollHistory;
import com.yju.toonovel.domain.admin.exception.EnrollNotFoundException;
import com.yju.toonovel.domain.admin.exception.EnrollNotMatchUserException;
import com.yju.toonovel.domain.admin.repository.AdminRepositoryImpl;
import com.yju.toonovel.domain.admin.repository.EnrollRepository;
import com.yju.toonovel.domain.novel.exception.AuthorNotFoundException;
import com.yju.toonovel.domain.novel.repository.NovelRepository;
import com.yju.toonovel.domain.user.entity.Role;
import com.yju.toonovel.domain.user.entity.User;
import com.yju.toonovel.domain.user.exception.AlreadyAuthorException;
import com.yju.toonovel.domain.user.exception.UserNotFoundException;
import com.yju.toonovel.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService {

	private final EnrollRepository enrollRepository;
	private final UserRepository userRepository;
	private final AdminRepositoryImpl adminRepositoryImpl;
	private final NovelRepository novelRepository;

	public Page<EnrollListResponseDto> findAllEnrollList(EnrollListPaginationRequestDto dto,
		Long userId) {

		Pageable pageable = PageRequest.of(dto.getPage(), dto.getLimit());
		return adminRepositoryImpl.findAllEnrollList(dto, pageable);
	}

	@Transactional
	public void updateAuthor(Long userId, EnrollUpdateRequestDto dto) {

		User user = userRepository.findByUserId(dto.getUserId())
			.orElseThrow(() -> new UserNotFoundException());

		if (user.getRole() == Role.AUTHOR) {
			throw new AlreadyAuthorException();
		}

		EnrollHistory enroll = enrollRepository.findByEnrollId(dto.getEnrollId())
			.orElseThrow(() -> new EnrollNotFoundException());

		enrollRepository.findByEnrollIdAndUserId(enroll.getEnrollId(), enroll.getUser().getUserId())
			.orElseThrow(() -> new EnrollNotMatchUserException());

		novelRepository.findByAuthor(dto.getNickname())
			.ifPresentOrElse(
				isAuthor -> {
					isAuthor.forEach(i -> {
						i.updateUserId(user);
					});
				},
				() -> {
					throw new AuthorNotFoundException();
				}
			);
		enroll.toggleApproval();
		user.updateRole(Role.AUTHOR);
	}
}
