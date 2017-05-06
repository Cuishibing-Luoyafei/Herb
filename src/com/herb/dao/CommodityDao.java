package com.herb.dao;

import java.util.List;

import com.herb.bean.Commodity;

public interface CommodityDao {
	public boolean saveCommodity(Commodity cmdty);
	public boolean updateCommodity(Commodity cmdty);
	public boolean deleteCommodity(Commodity cmdty);
	public Commodity getCommodityInId(String cmdtyId);
	/**
	 * 将整个Commodity传入，提高灵活性
	 * @param cmdty
	 * @return
	 */
	public Commodity getCommodity(Commodity cmdty);
	
	public List<Commodity> getCommodityBySearch(String content);
	
	/**
	 * 通过商品类型进行批量获取商品信息
	 * @param categary 物品类别(0:出售的商品，1:预购的商品，2:二手物品交换)
	 * @param start 起始点
	 * @param length 获取个数
	 * @return
	 */
	public List<Commodity> getCommoditysByCategory(String category, int start, int length);
}
