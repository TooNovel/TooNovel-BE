package com.yju.toonovel.domain.admin.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.yju.toonovel.domain.admin.entity.EnrollHistory;

public interface EnrollRepository extends JpaRepository<EnrollHistory, Long> {

	Optional<EnrollHistory> findByEnrollId(Long eid);

	@Query("select e from EnrollHistory e where e.enrollId = :eid and e.user.userId = :uid")
	Optional<EnrollHistory> findByIdByUserId(@Param("eid") Long eid, @Param("uid") Long uid);
}
