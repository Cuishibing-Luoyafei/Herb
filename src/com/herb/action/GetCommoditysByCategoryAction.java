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
import com.herb.bean.Commodity;
import com.herb.dao.CommodityDao;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
@Component("getCommoditysByCategory")
@Scope("prototype")
public class GetCommoditysByCategoryAction extends ActionSupport {

	private String category = "0";//物品类别(0:出售的商品，1:预购的商品，2:二手物品交换)
	private int start = 0;//获取的起始点
	private int length = 12;//获取的数量
	
	private CommodityDao cd;
	public CommodityDao getCd() {
		return cd;
	}
	@Resource(name="commodityDao")
	public void setCd(CommodityDao cd) {
		this.cd = cd;
	}
	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	
	public void get() {

System.out.println("商品展示分页：" + start + ", " + length);

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
		
		List<Commodity> cmdtys = cd.getCommoditysByCategory(category, start, length);
		if(cmdtys != null) {
			success = true;
			jo.add("cs", gson.toJsonTree(cmdtys));
		} else 
			reason = "商品为空";
		
		jo.addProperty("success", success);
		jo.addProperty("reason", reason);
		
		out.print(jo.toString());
		out.flush();
		out.close();
	}
}
