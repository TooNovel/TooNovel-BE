package com.yju.toonovel.domain.comment.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.yju.toonovel.domain.comment.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

	Optional<Comment> findByCommentId(Long cid);

	@Query("select c from Comment c where c.commentId = :commentId and c.user.userId = :userId")
	Optional<Comment> findByCommentIdAndUserId(@Param("userId") Long userId, @Param("commentId") Long commentId);
}
