package com.herb.bean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用户Id与商品Id对应表
 * 用于存储购物车的数据
 */
@Entity
@Table(name="t_userId_commodityId")
public class UserId_CommodityId {

	private int upId;
	private String userId = null;
	private String commodityId = null;
	
	public UserId_CommodityId() {}
	
	public UserId_CommodityId(String userId, String commodityId) {
		this.userId = userId;
		this.commodityId = commodityId;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public int getUpId() {
		return upId;
	}
	public void setUpId(int upId) {
		this.upId = upId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getCommodityId() {
		return commodityId;
	}

	public void setCommodityId(String commodityId) {
		this.commodityId = commodityId;
	}
}
