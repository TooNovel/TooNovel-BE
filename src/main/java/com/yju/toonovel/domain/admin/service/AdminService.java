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
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminService {

	private final EnrollRepository enrollRepository;
	private final UserRepository userRepository;
	private final AdminRepositoryImpl adminRepositoryImpl;
	private final NovelRepository novelRepository;

	public Page<EnrollListResponseDto> getEnrollList(EnrollListPaginationRequestDto dto,
		Long userId) {

		Pageable pageable = PageRequest.of(dto.getPage(), dto.getLimit());
		return adminRepositoryImpl.enrollList(dto, pageable);
	}

	@Transactional
	public void updateAuthor(Long userId, EnrollUpdateRequestDto dto) {

		User user = userRepository.findByUserId(dto.getUserId())
			.orElseThrow(() -> new UserNotFoundException());

		//작가 신청한 유저의 권한이 이미 작가인지 확인
		if (user.getRole() == Role.AUTHOR) {
			throw new AlreadyAuthorException();
		}

		//신청되지 않은 신청id가 넘어올 경우 에러 반환
		EnrollHistory enroll = enrollRepository.findByEnrollId(dto.getEnrollId())
			.orElseThrow(() -> new EnrollNotFoundException());

		//작가신청한 id와 신청한 유저id가 맞지 않을 경우의 에러 반환
		enrollRepository.findByIdByUserId(enroll.getEnrollId(), enroll.getUser().getUserId())
			.orElseThrow(() -> new EnrollNotMatchUserException());

		novelRepository.findByAuthor(dto.getNickname())
			.ifPresentOrElse(
				isAuthor -> {
					isAuthor.forEach(i -> {
						i.updateUserId(user);
					});
				},
				() -> new AuthorNotFoundException()
			);
		enroll.toggleApproval();
		user.updateRole(Role.AUTHOR);
	}
}