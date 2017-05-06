package com.herb.bean;

import java.util.Vector;

/**
 * 购物车
 * 装userId和其对应的所有产品Id
 */
public class ShopCar {
	
	private String userId = null;
	private Vector<String> commodityIds = null;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Vector<String> getCommodityIds() {
		return commodityIds;
	}
	public void setProductIds(Vector<String> commodityIds) {
		this.commodityIds = commodityIds;
	}
}