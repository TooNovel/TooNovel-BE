package com.yju.toonovel.domain.statistics.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.yju.toonovel.domain.novel.entity.Novel;

public interface StatisticsRepository extends JpaRepository<Novel, Long> {

	@Query("select n from Novel n where n.novelId = :nid and n.user.userId = :uid")
	Optional<Novel> findNovelByUserIdAndNovelId(@Param("nid") Long nid, @Param("uid") Long uid);
}
