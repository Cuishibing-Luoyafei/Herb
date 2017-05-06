package com.herb.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.herb.bean.Commodity;
import com.herb.bean.ShopCar;
import com.herb.bean.User;
import com.herb.dao.CommodityDao;
import com.herb.dao.ShopCarDao;
import com.herb.dao.UserDao;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
@Component("getShopCarInfo")
@Scope("prototype")
public class GetShopCarInfoAction extends ActionSupport {

	private String userId;
	private ShopCarDao scd;
	private UserDao ud;
	private CommodityDao cd;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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
	public CommodityDao getCd() {
		return cd;
	}
	@Resource(name="commodityDao")
	public void setCd(CommodityDao cd) {
		this.cd = cd;
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
		User u = ud.getUserInId(userId);
		if(u != null) {
			ShopCar sc = scd.getShopCar(userId);
			if(sc != null) {
				success = true;
				List<Commodity> cs = new ArrayList<>();
				for(String s : sc.getCommodityIds()) {
					cs.add(cd.getCommodityInId(s));
				}
				jo.add("commodities", gson.toJsonTree(cs));
				jo.add("shopCar", gson.toJsonTree(sc));
			} else
				reason = "获取失败";
		} else
			reason = "请先进行登陆";
		
		jo.addProperty("success", success);
		jo.addProperty("reason", reason);
		out.print(jo.toString());
		out.flush();
		out.close();
	}
	
}
