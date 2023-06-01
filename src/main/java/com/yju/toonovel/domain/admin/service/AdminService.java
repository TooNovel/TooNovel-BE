package com.yju.toonovel.domain.admin.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yju.toonovel.domain.admin.dto.WriterRegisterRequestDto;
import com.yju.toonovel.domain.admin.entity.EnrollHistory;
import com.yju.toonovel.domain.admin.repository.AdminRepository;
import com.yju.toonovel.domain.novel.exception.WriterNotFoundException;
import com.yju.toonovel.domain.novel.repository.NovelRepository;
import com.yju.toonovel.domain.user.entity.User;
import com.yju.toonovel.domain.user.exception.AlreadyWriterException;
import com.yju.toonovel.domain.user.exception.UserNotFoundException;
import com.yju.toonovel.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService {

	private final AdminRepository adminRepository;
	private final UserRepository userRepository;
	private final NovelRepository novelRepository;

	@Transactional
	public void writerRegister(Long userId, WriterRegisterRequestDto dto) {
		//작가 신청한 유저가 존재하는지 확인
		User user = userRepository.findByUserId(userId)
			.orElseThrow(() -> new UserNotFoundException());

		//작가 신청한 유저의 권한이 이미 작가인지 확인
		userRepository.findByRoleByUserId(user.getUserId())
			.ifPresent(role -> {
				if (role.equals("WRITER")) {
					throw new AlreadyWriterException();
				}
			});

		//작가 신청한 유저의 닉네임이 노벨 테이블에 존재하는지 확인
		novelRepository.findByAuthor(dto.getNickname())
			.ifPresentOrElse(
				//존재 한다면 신청내역에 저장, 노벨 테이블 포린키에 해당 유저 id저장
				isWriter -> {
					adminRepository.save(EnrollHistory.of(user));
					isWriter.updateUserId(user);
				},
				() -> new WriterNotFoundException()
			);
	}
}
