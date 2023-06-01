package com.yju.toonovel.domain.post.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.yju.toonovel.domain.post.entity.LikePost;
import com.yju.toonovel.domain.post.entity.Post;
import com.yju.toonovel.domain.user.entity.User;

public interface LikePostRepository extends JpaRepository<LikePost, Long> {

	@Query("select l from LikePost l where l.user = :user and l.post = :post")
	Optional<LikePost> findLikePostIdByUserIdAndPostId(
		@Param("user") User user, @Param("post") Post post
	);

	Optional<LikePost> findByUserUserIdAndPostPostId(Long uid, Long pid);
}
