package com.yju.toonovel.domain.statistics.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.yju.toonovel.domain.novel.entity.Novel;

public interface StatisticsRepository extends JpaRepository<Novel, Long> {

	//작가가 쓴 작품인지 검증 - 추후 작가인지 권한도 검증할 예정
	@Query("select n from Novel n where n.novelId = :nid and n.user.userId = :uid")
	Optional<Novel> findNovelByUserIdAndNovelId(@Param("nid") Long nid, @Param("uid") Long uid);
}
