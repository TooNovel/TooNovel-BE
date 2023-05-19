package com.yju.toonovel.domain.post.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.yju.toonovel.domain.post.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
	Optional<Post> findByPostId(Long postId);

	@Query("select p from Post p where p.postId = :postId and p.user.userId = :userId")
	Optional<Post> findByPostIdAndUserId(@Param("userId") Long userId, @Param("postId") Long postId);
}
