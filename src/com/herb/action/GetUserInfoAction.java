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
import com.herb.bean.User;
import com.herb.dao.UserDao;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
@Component("getUserInfo")
@Scope("prototype")
public class GetUserInfoAction extends ActionSupport {

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public UserDao getUd() {
		return ud;
	}
	@Resource(name="userDao")
	public void setUd(UserDao ud) {
		this.ud = ud;
	}
	private String userId;
	private UserDao ud;
	
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
			success = true;
System.out.println(user.toString());
			jo.add("user", gson.toJsonTree(user));//卖家信息
		} else
			reason = "该用户不存在";
		jo.addProperty("success", success);
		jo.addProperty("reason", reason);
		out.print(jo.toString());
		out.flush();
		out.close();
	}
}
