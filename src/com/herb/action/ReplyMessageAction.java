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
import com.herb.bean.Reply;
import com.herb.bean.User;
import com.herb.dao.ReplyDao;
import com.herb.dao.UserDao;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
@Component("replyMessage")
@Scope("prototype")
public class ReplyMessageAction extends ActionSupport {

	/**
	 * 用户id
	 */
	private String userId;
	private String commodityId;
	private String replyContent;
	
	private ReplyDao rd;
	private UserDao ud;
	public UserDao getUd() {
		return ud;
	}
	@Resource(name="userDao")
	public void setUd(UserDao ud) {
		this.ud = ud;
	}
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
	public String getReplyContent() {
		return replyContent;
	}
	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}
	public ReplyDao getRd() {
		return rd;
	}
	@Resource(name="replyDao")
	public void setRd(ReplyDao rd) {
		this.rd = rd;
	}
	
	public void reply() {
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
			Reply r = new Reply();
			r.setReplyUserId(userId);
			r.setCommodityId(commodityId);
			r.setReplyContent(replyContent);
			r.setReplyTime(new Timestamp(System.currentTimeMillis()));
			if(rd.saveReply(r))
				success = true;
			else
				reason = "回复失败";
		} else
			reason = "请先进行登陆";
		

		jo.addProperty("success", success);
		jo.addProperty("reason", reason);
		out.print(jo.toString());
		out.flush();
		out.close();
	}
}
