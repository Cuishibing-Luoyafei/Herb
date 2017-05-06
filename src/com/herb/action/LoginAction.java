package com.herb.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.gson.JsonObject;
import com.herb.bean.User;
import com.herb.dao.UserDao;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
@Component("loginAction")
@Scope("prototype")
public class LoginAction extends ActionSupport {

	private UserDao ud;
	private String userNickName;
	private String password;
	public UserDao getUd() {
		return ud;
	}
	@Resource(name="userDao")
	public void setUd(UserDao ud) {
		this.ud = ud;
	}
	public String getUserNickName() {
		return userNickName;
	}
	public void setUserNickName(String userNickName) {
		this.userNickName = userNickName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * 使用struts2的动态方法调用，进行登陆操作
	 */
	public void login() {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = null;
		JsonObject jo = new JsonObject();
		boolean success = false;
		String reason = "";
		try {
			out = response.getWriter();
		} catch(IOException e) {}
		
		if(userNickName != null && userNickName.trim().hashCode() != 0 && password != null && password.trim().hashCode() != 0) {
			User user = ud.getUserByNickName(userNickName);
			if(user != null && user.getUserPassword().equals(password)) {
				success = true;
				jo.addProperty("userId", user.getUserId());
//				ServletActionContext.getRequest().getSession().setAttribute("user", user);
				if(user.getIsAdmin().equals("1")) 
					jo.addProperty("isAdmin", true);
				else
					jo.addProperty("isAdmin", false);
			} else {
				reason = "密码错误";
			}
		} else
			reason = "用户名或密码为空！";
		jo.addProperty("success", success);
		jo.addProperty("reason", reason);
		
		out.print(jo.toString());
		
		out.flush();
		out.close();
	}
	/**
	 * 使用struts2的动态方法调用，进行注册操作
	 */
	public void regist() {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = null;
		JsonObject jo = new JsonObject();
		boolean success = false;
		String reason = "";
		try {
			out = response.getWriter();
		} catch(IOException e) {}
		

		if(userNickName != null && userNickName.trim().hashCode() != 0 && password != null && password.trim().hashCode() != 0) {
			if(ud.getUserByNickName(userNickName) != null) {
				reason = "该用户已存在！";
			} else {
				User userSave = new User();
				userSave.setUserNickName(userNickName);
				userSave.setUserPassword(password);
				userSave.setUserCreateDate(new Timestamp(System.currentTimeMillis()));
				if(ud.saveUser(userSave)) {
					success = true;
					jo.addProperty("userId", userSave.getUserId());
					jo.addProperty("isAdmin", false);
//					ServletActionContext.getRequest().getSession().setAttribute("user", userSave);
				} else
					reason = "注册失败！";
			}
		} else
			reason = "用户名或密码为空！";
		jo.addProperty("success", success);
		jo.addProperty("reason", reason);
		
		out.print(jo.toString());
		
		out.flush();
		out.close();
	}
}
