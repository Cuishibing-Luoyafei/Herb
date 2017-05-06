package com.herb.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.gson.JsonObject;
import com.herb.bean.Commodity;
import com.herb.bean.Order;
import com.herb.bean.User;
import com.herb.dao.CommodityDao;
import com.herb.dao.OrderDao;
import com.herb.dao.UserDao;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
@Component("buyCommodity")
@Scope("prototype")
public class BuyCommodityAction extends ActionSupport {

	
	public List<String> getCommodityId() {
		return commodityId;
	}
	public void setCommodityId(List<String> commodityId) {
		this.commodityId = commodityId;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}

	/**
	 * 用户id
	 */
	private String userId;
	private List<String> commodityId = null;
	private String addr = null;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	private CommodityDao cd;
	private OrderDao od;
	private UserDao ud;
	public CommodityDao getCd() {
		return cd;
	}
	@Resource(name="commodityDao")
	public void setCd(CommodityDao cd) {
		this.cd = cd;
	}
	public OrderDao getOd() {
		return od;
	}
	@Resource(name="orderDao")
	public void setOd(OrderDao od) {
		this.od = od;
	}
	public UserDao getUd() {
		return ud;
	}
	@Resource(name="userDao")
	public void setUd(UserDao ud) {
		this.ud = ud;
	}
	
	/**
	 * 直接购买
	 */
	public void buy() {
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = null;
		JsonObject jo = new JsonObject();
		boolean success = false;
		String reason = "";
		try {
			out = response.getWriter();
		} catch(IOException e) {}
		
		User user = ud.getUserInId(userId);
		if(user != null) {
			
			if(commodityId.size() > 0) {
				
				for(int i = 0; i < commodityId.size(); i++) {
					Order o = new Order();
					Commodity c = cd.getCommodityInId(commodityId.get(i));
					o.setCommodityId(commodityId.get(i));
					o.setSellerId(c.getCommodityOwnerId());
					o.setAddr(addr);
					o.setUnitPrice(c.getCommodityNewPrice());
					o.setCommodityCount(c.getCommodityCount());
					o.setUserId(user.getUserId());
					o.setStatus("1");
					
					o.setOrderDate(new Timestamp(System.currentTimeMillis()));
					
					c.setIsOrder("2");
					cd.updateCommodity(c);
					
					if(!od.saveOrder(o)) {
						success = false;
						reason = "订单创建失败";
						break;
					} else
						success = true;
				}
			} else
				reason = "请选择商品";
		} else
			reason = "请先进行登陆";
		
		jo.addProperty("success", success);
		jo.addProperty("reason", reason);
		out.print(jo.toString());
		out.flush();
		out.close();
	}
	/**
	 * 加入购物车
	 */
	public void addToCar() {}
	
	/**
	 * 线下支付调用函数
	 */
	public void downLine() {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = null;
		JsonObject jo = new JsonObject();
		boolean success = false;
		String reason = "";
		try {
			out = response.getWriter();
		} catch(IOException e) {}
		
		User user = ud.getUserInId(userId);
		if(user != null) {
			
			if(commodityId.size() > 0) {
				
				for(int i = 0; i < commodityId.size(); i++) {
					Order o = new Order();
					Commodity c = cd.getCommodityInId(commodityId.get(i));
					o.setCommodityId(commodityId.get(i));
					o.setSellerId(c.getCommodityOwnerId());
					o.setAddr(addr);
					o.setUnitPrice(c.getCommodityNewPrice());
					o.setCommodityCount(c.getCommodityCount());
					o.setUserId(user.getUserId());
					o.setStatus("3");//更改为线下支付中
						
					o.setOrderDate(new Timestamp(System.currentTimeMillis()));
					
					c.setIsOrder("2");
					cd.updateCommodity(c);
					
					if(!od.saveOrder(o)) {
						success = false;
						reason = "订单创建失败";
						break;
					} else
						success = true;
				}
			} else
				reason = "请选择商品";
		} else
			reason = "请先进行登陆";
		
		jo.addProperty("success", success);
		jo.addProperty("reason", reason);
		out.print(jo.toString());
		out.flush();
		out.close();
	}
}
