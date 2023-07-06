package com.yju.toonovel.domain.novel.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yju.toonovel.domain.novel.entity.Novel;

public interface NovelRepository extends JpaRepository<Novel, Long>, NovelCustomRepository {

	Optional<Novel> findByNovelId(Long novelId);

	Optional<List<Novel>> findByAuthor(String author);
}
