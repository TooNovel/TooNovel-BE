package com.yju.toonovel.domain.work.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yju.toonovel.domain.work.entity.Work;

public interface WorkRepository extends JpaRepository<Work, Long> {
}
