package com.yju.toonovel.domain.comment.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.yju.toonovel.domain.comment.entity.Comment;
import com.yju.toonovel.domain.comment.entity.LikeComment;
import com.yju.toonovel.domain.user.entity.User;

public interface LikeCommentRepository extends JpaRepository<LikeComment, Long> {

	@Query("select l from LikeComment l where l.user = :user and l.comment = :comment")
	Optional<LikeComment> findLikeCommentIdByUserIdAndCommentId(
		@Param("user") User user, @Param("comment") Comment comment
	);
}
