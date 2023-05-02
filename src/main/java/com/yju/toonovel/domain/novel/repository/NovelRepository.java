package com.yju.toonovel.domain.novel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yju.toonovel.domain.novel.entity.Novel;

public interface NovelRepository extends JpaRepository<Novel, Long>, NovelCustomRepository {
}
