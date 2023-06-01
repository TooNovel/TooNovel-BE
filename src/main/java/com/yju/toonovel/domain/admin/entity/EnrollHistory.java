package com.yju.toonovel.domain.admin.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.yju.toonovel.domain.user.entity.User;
import com.yju.toonovel.global.common.entity.BaseEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class EnrollHistory extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long enrollId;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	private boolean isApproval;


	@Builder
	public EnrollHistory(User user, boolean isApproval) {
		this.user = user;
		this.isApproval = isApproval;
	}

	public static EnrollHistory of(User user) {
		return EnrollHistory.builder()
			.user(user)
			.build();
	}

	public void toggleApproval() {
		isApproval = !isApproval;
	}
}
