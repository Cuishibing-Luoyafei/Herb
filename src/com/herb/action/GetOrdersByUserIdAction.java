package com.herb.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.herb.bean.Commodity;
import com.herb.bean.Order;
import com.herb.bean.OrderStatus;
import com.herb.bean.User;
import com.herb.bean.UserType;
import com.herb.dao.CommodityDao;
import com.herb.dao.OrderDao;
import com.herb.dao.UserDao;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
@Component("getOrdersByUserId")
@Scope("prototype")
public class GetOrdersByUserIdAction extends ActionSupport {

	private OrderDao od;
	private UserDao ud;
	private CommodityDao cd;
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
	public CommodityDao getCd() {
		return cd;
	}
	@Resource(name="commodityDao")
	public void setCd(CommodityDao cd) {
		this.cd = cd;
	}
	
	/**
	 * 用户id
	 */
	private String userId;	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public void get() {
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = null;
		Gson gson = new Gson();
		JsonObject jo = new JsonObject();
		boolean success = false;
		String reason = "";
		try {
			out = response.getWriter();
		} catch(IOException e) {}
		User user = ud.getUserInId(userId);
		if(user != null) {
			List<Order> os = od.getOrdersInUserId(user.getUserId(), OrderStatus.NONE_STATUS, UserType.CREATOR_TYPE);
			if(os != null) {
				success = true;
				User[] users = new User[os.size()];
				User[] seller = new User[os.size()];
				Commodity[] commoditys = new Commodity[os.size()];
				for(int i = 0; i < os.size(); i++) {
					users[i] = ud.getUserInId(os.get(i).getUserId());
					users[i].setUserPassword(null);
					seller[i] = ud.getUserInId(os.get(i).getSellerId());
					seller[i].setUserPassword(null);
					commoditys[i] = cd.getCommodityInId(os.get(i).getCommodityId());
				}
				jo.add("os", gson.toJsonTree(os));
				jo.add("commodities", gson.toJsonTree(commoditys));
				jo.add("users", gson.toJsonTree(users));//买家信息
				jo.add("seller", gson.toJsonTree(seller));//卖家信息
			} else
				reason = "订单为空";
		} else
			reason = "请先进行登陆";
		
		jo.addProperty("success", success);
		jo.addProperty("reason", reason);
		out.print(jo.toString());
		out.flush();
		out.close();
	}
	
	public void received() {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = null;
		Gson gson = new Gson();
		JsonObject jo = new JsonObject();
		boolean success = false;
		String reason = "";
		try {
			out = response.getWriter();
		} catch(IOException e) {}
		User user = ud.getUserInId(userId);
		if(user != null) {
			List<Order> os = od.getOrdersInUserId(user.getUserId(), OrderStatus.NONE_STATUS, UserType.SELLER_TYPE);
			if(os != null) {
				success = true;
				User[] users = new User[os.size()];
				User[] seller = new User[os.size()];
				Commodity[] commoditys = new Commodity[os.size()];
				for(int i = 0; i < os.size(); i++) {
					users[i] = ud.getUserInId(os.get(i).getUserId());
					users[i].setUserPassword(null);
					seller[i] = ud.getUserInId(os.get(i).getSellerId());
					seller[i].setUserPassword(null);
					commoditys[i] = cd.getCommodityInId(os.get(i).getCommodityId());
				}
				jo.add("os", gson.toJsonTree(os));
				jo.add("commodities", gson.toJsonTree(commoditys));
				jo.add("users", gson.toJsonTree(users));//买家信息
				jo.add("seller", gson.toJsonTree(seller));//卖家信息
			} else
				reason = "订单为空";
		} else
			reason = "请先进行登陆";
		
		jo.addProperty("success", success);
		jo.addProperty("reason", reason);
		out.print(jo.toString());
		out.flush();
		out.close();
	}
}
