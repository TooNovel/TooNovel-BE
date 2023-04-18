package com.yju.toonovel.domain.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.yju.toonovel.domain.user.entity.Provider;
import com.yju.toonovel.domain.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByProviderAndOauthId(Provider provider, String oauthId);

	@Query("select u.gender from User u where u.oauthId = :oauthId")
	Optional<User> findByGender(@Param("oauthId") String oauthId);

	Optional<User> findByOauthId(String oauthId);

	@Query("select u.userId from User u where u.oauthId = :oauthId")
	Long findByUserIdWhereOauthId(@Param("oauthId") String oauthId);
}
