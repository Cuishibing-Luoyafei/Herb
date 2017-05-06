package com.herb.bean;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="t_commodity")
public class Commodity {
	
@Override
	public String toString() {
		return "Commodity [commodityId=" + commodityId + ", commodityName=" + commodityName + ", commodityCategary="
				+ commodityCategary + ", isOrder=" + isOrder + ", commodityPicture=" + commodityPicture
				+ ", commodityDescribe=" + commodityDescribe + ", commodityCount=" + commodityCount
				+ ", commodityOldNewLevel=" + commodityOldNewLevel + ", commodityNewPrice=" + commodityNewPrice
				+ ", commodityOldPrice=" + commodityOldPrice + ", commodityOwnerId=" + commodityOwnerId
				 + ", commodifyCreateDate=" + commodifyCreateDate
				+ ", wantShopCount=" + wantShopCount + "]";
	}
@SuppressWarnings("unused")
private final static String COMMODITY_DEFAULT = "";

	private String commodityId = null;
	private String commodityName = "";//物品名称
	private String commodityCategary = "";//物品类别(0:出售的商品，1:预购的商品，2:二手物品交换)
	private String isOrder = "0";//是否被定(0:正在出售,1:已被订购,2:已出售)
	private String commodityPicture = "";//物品图片
	private String commodityDescribe = "";//物品描述
	private Integer commodityCount = -1;//物品数量
	private Integer commodityOldNewLevel = -1;//新旧程度
	private String commodityNewPrice = "";//原价
	private String commodityOldPrice = "";//二手价
	private String commodityOwnerId = "";//拥有者
	private Timestamp commodifyCreateDate = null;//创建时间
	private Integer wantShopCount = 0;//欲购物人数
	
	@Id
	@GenericGenerator(name="uuid", strategy="uuid")
	@GeneratedValue(generator="uuid")
	public String getCommodityId() {
		return commodityId;
	}
	public void setCommodityId(String commodityId) {
		this.commodityId = commodityId;
	}
	public String getCommodityName() {
		return commodityName;
	}
	public void setCommodityName(String commodityName) {
		this.commodityName = commodityName;
	}
	public String getIsOrder() {
		return isOrder;
	}
	public void setIsOrder(String isOrder) {
		this.isOrder = isOrder;
	}
	public String getCommodityCategary() {
		return commodityCategary;
	}
	public void setCommodityCategary(String commodityCategary) {
		this.commodityCategary = commodityCategary;
	}
	public String getCommodityPicture() {
		return commodityPicture;
	}
	public void setCommodityPicture(String commodityPicture) {
		this.commodityPicture = commodityPicture;
	}
	public String getCommodityDescribe() {
		return commodityDescribe;
	}
	public void setCommodityDescribe(String commodityDescribe) {
		this.commodityDescribe = commodityDescribe;
	}
	public Integer getCommodityCount() {
		return commodityCount;
	}
	public void setCommodityCount(Integer commodityCount) {
		this.commodityCount = commodityCount;
	}
	public Integer getCommodityOldNewLevel() {
		return commodityOldNewLevel;
	}
	public void setCommodityOldNewLevel(Integer commodityOldNewLevel) {
		this.commodityOldNewLevel = commodityOldNewLevel;
	}
	public String getCommodityNewPrice() {
		return commodityNewPrice;
	}
	public void setCommodityNewPrice(String commodityNewPrice) {
		this.commodityNewPrice = commodityNewPrice;
	}
	public String getCommodityOldPrice() {
		return commodityOldPrice;
	}
	public void setCommodityOldPrice(String commodityOldPrice) {
		this.commodityOldPrice = commodityOldPrice;
	}
	public String getCommodityOwnerId() {
		return commodityOwnerId;
	}
	public void setCommodityOwnerId(String commodityOwnerId) {
		this.commodityOwnerId = commodityOwnerId;
	}
	public Timestamp getCommodifyCreateDate() {
		return commodifyCreateDate;
	}
	public void setCommodifyCreateDate(Timestamp commodifyCreateDate) {
		this.commodifyCreateDate = commodifyCreateDate;
	}
	public Integer getWantShopCount() {
		return wantShopCount;
	}
	public void setWantShopCount(Integer wantShopCount) {
		this.wantShopCount = wantShopCount;
	}
}
