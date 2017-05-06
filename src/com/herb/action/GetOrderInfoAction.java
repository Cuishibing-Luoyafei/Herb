package com.herb.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.herb.bean.Order;
import com.herb.bean.User;
import com.herb.dao.OrderDao;
import com.herb.dao.UserDao;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
@Component("getOrderInfo")
@Scope("prototype")
public class GetOrderInfoAction extends ActionSupport {

	private String id;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	private OrderDao od;
	private UserDao ud;
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
		Order o = od.getOrderById(id);
		if(o != null) {
			success = true;
			User u = ud.getUserInId(o.getUserId());
			u.setUserPassword(null);
			User seller = ud.getUserInId(o.getSellerId());
			seller.setUserPassword(null);
			
			jo.add("o", gson.toJsonTree(o));//订单信息
			jo.add("users", gson.toJsonTree(u));//买家信息
			jo.add("seller", gson.toJsonTree(seller));//卖家信息
			
		} else
			reason = "该订单不存在";
		jo.addProperty("success", success);
		jo.addProperty("reason", reason);
		out.print(jo.toString());
		out.flush();
		out.close();
	}
}
