package com.herb.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.gson.JsonObject;
import com.herb.bean.Order;
import com.herb.dao.CommodityDao;
import com.herb.dao.OrderDao;
import com.herb.dao.UserDao;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
@Component("updateOrder")
@Scope("prototype")
public class UpdateOrderAction extends ActionSupport {

	private String orderId;
	private String status;
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
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
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	public void update() {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = null;
		JsonObject jo = new JsonObject();
		boolean success = false;
		String reason = "";
		try {
			out = response.getWriter();
		} catch(IOException e) {}
		
		Order o = od.getOrderById(orderId);
		if(o != null) {
			o.setStatus(status);
			if(od.updateOrder(o)) {
				success = true;	
			} else
				reason = "更新失败";
		} else
			reason = "订单不存在";
		
		jo.addProperty("success", success);
		jo.addProperty("reason", reason);
		
		out.print(jo.toString());
		out.flush();
		out.close();
	}
	
	
}
