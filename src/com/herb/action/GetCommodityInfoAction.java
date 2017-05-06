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
import com.herb.bean.Commodity;
import com.herb.bean.User;
import com.herb.dao.CommodityDao;
import com.herb.dao.UserDao;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
@Component("getCommodityInfo")
@Scope("prototype")
public class GetCommodityInfoAction extends ActionSupport {

	private CommodityDao cd;
	private UserDao ud;
	private String id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public CommodityDao getCd() {
		return cd;
	}
	@Resource(name="commodityDao")
	public void setCd(CommodityDao cd) {
		this.cd = cd;
	}
	public UserDao getUd() {
		return ud;
	}
	@Resource(name="userDao")
	public void setUd(UserDao ud) {
		this.ud = ud;
	}
	
	/**
	 * 通过商品Id获取商品信息
	 */
	public void getCommodityInfoById() {
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
		Commodity c = cd.getCommodityInId(id);
		if(c != null) {
			User u = ud.getUserInId(c.getCommodityOwnerId());
			success = true;
			jo.add("commodity", gson.toJsonTree(c));
			jo.add("u", gson.toJsonTree(u));
		} else
			reason = "该商品不存在";
		
		jo.addProperty("success", success);
		jo.addProperty("reason", reason);
		
		out.print(jo.toString());
		out.flush();
		out.close();
	}
}
