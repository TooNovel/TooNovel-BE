package com.yju.toonovel.domain.comment.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.yju.toonovel.domain.comment.entity.Comment;
import com.yju.toonovel.domain.review.entity.Review;

public interface CommentRepository extends JpaRepository<Comment, Long> {

	Optional<Comment> findByCommentId(Long cid);

	@Query("select c from Comment c where c.user.userId = :userId and c.post.postId = :postId")
	Optional<Review> findCommentByPostIdAndUserId(@Param("userId") Long userId, @Param("postId") Long postId);

	@Query("select c from Comment c where c.commentId = :commentId and c.user.userId = :userId")
	Optional<Comment> findByCommentIdAndUserId(@Param("userId") Long userId, @Param("commentId") Long commentId);
}
