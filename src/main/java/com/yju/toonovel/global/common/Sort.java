package com.yju.toonovel.global.common;

import com.querydsl.core.types.Order;

public enum Sort {
	CREATED_DATE_DESC("createdDate", Order.DESC),
	CREATED_DATE_ASC("createdDate", Order.ASC),
	REVIEW_LIKE_DESC("reviewLike", Order.DESC);

	private final String property;
	private final Order order;

	Sort(String property, Order order) {
		this.property = property;
		this.order = order;
	}

	public String getProperty() {
		return property;
	}

	public Order getOrder() {
		return order;
	}

}
