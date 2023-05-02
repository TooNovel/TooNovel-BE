package com.yju.toonovel.domain.novel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yju.toonovel.domain.novel.entity.Novel;
import com.yju.toonovel.domain.novel.entity.NovelPlatform;

@Repository
public interface NovelPlatformRepository extends JpaRepository<NovelPlatform, Long> {

	List<NovelPlatform> findAllByNovel(Novel novel);
}
