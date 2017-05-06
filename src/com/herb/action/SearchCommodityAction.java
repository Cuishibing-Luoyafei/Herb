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
@Component("searchCommodity")
@Scope("prototype")
public class SearchCommodityAction extends ActionSupport {

	private String content;
	private CommodityDao cd;
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public CommodityDao getCd() {
		return cd;
	}
	@Resource(name="commodityDao")
	public void setCd(CommodityDao cd) {
		this.cd = cd;
	}
	
	/**
	 * 进行产品模糊查询的函数
	 */
	public void search() {
		
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
		
		if(content != null && content.trim().hashCode() != 0) {
			List<Commodity> cmdys = cd.getCommodityBySearch(content);
			if(cmdys != null && cmdys.size() != 0) {
				success = true;
				jo.add("commoditys", gson.toJsonTree(cmdys));
			} else
				reason = "暂无该商品";
		} else 
			reason = "请输入您要搜索的关键字！";
		
		jo.addProperty("success", success);
		jo.addProperty("reason", reason);
		
		out.print(jo.toString());
		out.flush();
		out.close();
	}
	
}
