package com.yju.toonovel.domain.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yju.toonovel.domain.user.entity.Provider;
import com.yju.toonovel.domain.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByProviderAndOauthId(Provider provider, String oauthId);

	Optional<User> findByUserId(Long userId);
}
