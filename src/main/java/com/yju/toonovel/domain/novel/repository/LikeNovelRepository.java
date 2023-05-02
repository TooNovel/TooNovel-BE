package com.yju.toonovel.domain.novel.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yju.toonovel.domain.novel.entity.LikeNovel;
import com.yju.toonovel.domain.novel.entity.Novel;
import com.yju.toonovel.domain.user.entity.User;

public interface LikeNovelRepository extends JpaRepository<LikeNovel, Long>, LikeNovelCustomRepository {

	Optional<LikeNovel> findByUserAndNovel(User user, Novel novel);

	Optional<LikeNovel> findByUserAndNovel(Long userId, Long novelId);

}
