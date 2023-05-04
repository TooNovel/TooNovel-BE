package com.yju.toonovel.domain.review.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.yju.toonovel.domain.review.dto.AllReviewInWorkResponseDto;
import com.yju.toonovel.domain.review.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {

	Optional<Review> findByReviewId(Long rid);

	@Modifying
	@Query("delete from Review r where r.reviewId = :reviewId")
	void deleteByReviewId(@Param("reviewId") Long reviewId);

	//한 작품에 대한 전체 리뷰 + 로그인 한 유저가 좋아요 한 부분까지 조회
	@Query(
		value =
			"SELECT u.nickname as nickname, u.imageUrl as imageUrl, r.createdDate as createdDate,"
				+ "r.reviewContent as reviewContent, r.reviewGrade as reviewGrade , r.reviewLike as reviewLike, "
				+ "l.user.userId as userId,  l.isActived as isActived  "
				+ " FROM Review r left outer join r.writer u on u.userId = r.writer.userId"
				+ " left outer join LikeReview l on r.reviewId = l.review.reviewId "
				+ " where r.novel.novelId = :nid "
				+ "order by r.createdDate desc")
	Page<AllReviewInWorkResponseDto> getReviewAndReviewLikeByNovelOrderByReviewIdPaging(
		@Param("nid") Long nid, Pageable pageable);


	@Query("select r from Review r where r.reviewId = :reviewId")
	Optional<Review> deleteReviewByReviewIdAndUserId(@Param("reviewId") Long reviewId);
}
