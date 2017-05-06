
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
import com.herb.bean.User;
import com.herb.dao.OrderDao;
import com.herb.dao.UserDao;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
@Component("deleteOrder")
@Scope("prototype")
public class DeleteOrderAction extends ActionSupport {

	private String userId;
	private String orderId;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
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
	
	public void delete() {
System.out.println(userId + ", " + orderId);
		
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
			
			Order o = od.getOrderById(orderId);
			if(o != null) {
				if(od.deleteOrder(o)) {
					success = true;
				} else
					reason = "删除失败";
			} else
				reason = "暂无订单";
			
		} else 
			reason = "尚未登陆";

		jo.addProperty("success", success);
		jo.addProperty("reason", reason);
		out.print(jo.toString());
		out.flush();
		out.close();
	}
	
}
