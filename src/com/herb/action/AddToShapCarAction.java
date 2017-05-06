package com.herb.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.gson.JsonObject;
import com.herb.bean.User;
import com.herb.dao.ShopCarDao;
import com.herb.dao.UserDao;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
@Component("addToShopCar")
@Scope("prototype")
public class AddToShapCarAction extends ActionSupport {

	private String userId;
	private String commodityId;
	
	private ShopCarDao scd;
	private UserDao ud;
	
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

	public ShopCarDao getScd() {
		return scd;
	}
	@Resource(name="shopCarDao")
	public void setScd(ShopCarDao scd) {
		this.scd = scd;
	}
	public UserDao getUd() {
		return ud;
	}
	@Resource(name="userDao")
	public void setUd(UserDao ud) {
		this.ud = ud;
	}

	public void add() {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = null;
		JsonObject jo = new JsonObject();
		boolean success = false;
		String reason = "";
		try {
			out = response.getWriter();
		} catch(IOException e) {}
		User u = ud.getUserInId(userId);
		if(u != null) {
			if(scd.saveUserIdCommodityId(userId, commodityId)) {
				success = true;
			} else
				reason = "存入失败";
		} else
			reason = "请先进行登陆";
		

		jo.addProperty("success", success);
		jo.addProperty("reason", reason);
		out.print(jo.toString());
		out.flush();
		out.close();
	}
}
