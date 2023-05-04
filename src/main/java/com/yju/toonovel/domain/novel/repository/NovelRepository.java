package com.yju.toonovel.domain.novel.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.yju.toonovel.domain.novel.entity.Novel;

public interface NovelRepository extends JpaRepository<Novel, Long>, NovelCustomRepository {

	Optional<Novel> findByNovelId(Long novelId);

	// 추후 사용 예정
	// @Query("select distinct n.genre from Novel n where n.genre = :genre")
	// Optional<String> findByGenre(String genre);
}
