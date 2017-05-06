package com.herb.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.herb.bean.Reply;
import com.herb.bean.User;
import com.herb.dao.ReplyDao;
import com.herb.dao.UserDao;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
@Component("getReplys")
@Scope("prototype")
public class GetReplysAction extends ActionSupport {

	private String userId;
	private String commodityId;
	
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
	public ReplyDao getRd() {
		return rd;
	}
	@Resource(name="replyDao")
	public void setRd(ReplyDao rd) {
		this.rd = rd;
	}
	
	/**
	 * 通过用户Id获取该用户评论过的所有评论
	 */
	public void byUserId() {}
	/**
	 * 通过商品获得所有评论
	 */
	public void byCommodityId() {
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
			List<Reply> rs = rd.getReplysBuCommodityId(commodityId);
			if(rs != null) {
				success = true;
				User[] users = new User[rs.size()];
				for(int i = 0; i < rs.size(); i++) {
					users[i] = ud.getUserInId(rs.get(i).getReplyUserId());
				}
				jo.add("users", gson.toJsonTree(users));
				jo.add("rs", gson.toJsonTree(rs));
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
