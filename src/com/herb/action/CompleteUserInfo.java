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
import com.herb.dao.UserDao;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
@Component("completeUserInfo")
@Scope("prototype")
public class CompleteUserInfo extends ActionSupport {

	@Override
	public String toString() {
		return "CompleteUserInfo [ud=" + ud + ", userNickName=" + userNickName + ", userIntroduce=" + userIntroduce
				+ ", userGender=" + userGender + ", userAddress=" + userAddress + ", userTel=" + userTel + "]";
	}

	private UserDao ud;

	public UserDao getUd() {
		return ud;
	}
	@Resource(name="userDao")
	public void setUd(UserDao ud) {
		this.ud = ud;
	}
	private String userId = null;
	private String userNickName = null;
	private String userIntroduce = null;//个人简介
	private String userGender = null;
	private String userAddress = null;
	private String userTel = null;//电话
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserNickName() {
		return userNickName;
	}

	public void setUserNickName(String userNickName) {
		this.userNickName = userNickName;
	}

	public String getUserIntroduce() {
		return userIntroduce;
	}

	public void setUserIntroduce(String userIntroduce) {
		this.userIntroduce = userIntroduce;
	}

	public String getUserGender() {
		return userGender;
	}

	public void setUserGender(String userGender) {
		this.userGender = userGender;
	}

	public String getUserAddress() {
		return userAddress;
	}

	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}

	public String getUserTel() {
		return userTel;
	}

	public void setUserTel(String userTel) {
		this.userTel = userTel;
	}
	private boolean checkField(String field) {
		return (field != null && field.trim().hashCode() != 0);
	}
	private boolean checkFields() {
		return checkField(userNickName) && checkField(userIntroduce) && checkField(userGender) && checkField(userAddress) && checkField(userTel); 
	}
	
	public void complete() {

		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = null;
		JsonObject jo = new JsonObject();
		boolean success = false;
		String reason = "";
		try {
			out = response.getWriter();
		} catch(IOException e) {}
		
		/**
		 * 	private String userNickName = null;
			private String userIntroduce = null;//个人简介
			private String userGender = null;
			private String userAddress = null;
			private String userTel = null;//电话
		 */
		User user = ud.getUserInId(userId);
		
		if(user != null) {
			if(checkFields()) {
				
				
				if(ud.getUserByNickName(userNickName) != null && !userNickName.equals(user.getUserNickName())) {
					reason = "该昵称已用人使用!";
				} else {
					user.setUserNickName(userNickName);
					user.setUserIntroduce(userIntroduce);
					user.setUserGender(userGender);
					user.setUserTel(userTel);
					user.setUserAddress(userAddress);
					
					if(ud.updateUser(user)) {
						success = true;
					} else
						reason = "更新失败!";
				}
					
			} else
				reason = "请将信息填写完整";
		} else 
			reason = "尚未登陆";
		
		jo.addProperty("success", success);
		jo.addProperty("reason", reason);
		
		out.print(jo.toString());
		
		out.flush();
		out.close();	
		
	}
}
