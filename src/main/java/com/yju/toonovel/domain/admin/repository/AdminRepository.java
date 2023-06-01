package com.yju.toonovel.domain.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yju.toonovel.domain.admin.entity.EnrollHistory;

public interface AdminRepository extends JpaRepository<EnrollHistory, Long> {
}
