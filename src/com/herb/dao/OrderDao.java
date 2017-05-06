package com.herb.dao;

import java.sql.Timestamp;
import java.util.List;

import com.herb.bean.Order;
import com.herb.bean.OrderStatus;
import com.herb.bean.UserType;

public interface OrderDao {
	public boolean saveOrder(Order order);
	/**
	 * 根据订单Id，将订单删除
	 * @param orderId
	 * @return boolean
	 * @exception NullPointerException
	 */
	public boolean deleteOrder(Order order);
	/**
	 * 单个订单的更新
	 * @param order
	 * @return boolean
	 * @exception NullPointerException
	 */
	public boolean updateOrder(Order order);
	/**
	 * 提供多个订单同时进行修改，要么同时成功，要么同时失败。
	 * @param orders
	 * @return boolean
	 * @exception NullPointerException
	 */
	public boolean updateOrders(Order[] orders);

	/**
	 * 根据用户Id，订单状态，用户类型，获取该用户所需要的所有订单
	 * 当订单状态为NONE_STATUS(0)时，默认是忽略该状态字段，扫描所有的订单(不论是否成功或作废)。
	 * @param sellerId
	 * @param status 状态(0:订单未创建；1:订单成功；2:订单作废)
	 * @param userType (用户类型：0：没有类型，获取该用户创建的和接收的所有订单；1：买家；2：卖家)
	 * @return List<Order>
	 * @exception IllegalArgumentException NullPointerException
	 */
	public List<Order> getOrdersInUserId(String userId, OrderStatus status, UserType userType);
	
	/**
	 * 获取某一日期前，跟该用户相关的订单
	 * @param userId
	 * @param time
	 * @param userType (用户类型：0：没有类型，获取该用户创建的和接收的所有订单；1：买家；2：卖家)
	 * @return List<Order>
	 * @exception IllegalArgumentException NullPointerException
	 */
	public List<Order> getOrdersBeforeDate(String userId, Timestamp time, UserType userType);
	
	public Order getOrderById(String orderId);
}


