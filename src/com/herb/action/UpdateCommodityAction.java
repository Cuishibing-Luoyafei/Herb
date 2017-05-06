package com.herb.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.gson.JsonObject;
import com.herb.bean.Commodity;
import com.herb.dao.CommodityDao;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
@Component("updateCommodity")
@Scope("prototype")
public class UpdateCommodityAction extends ActionSupport {

	private String commodityId;
	private CommodityDao cd;

	public CommodityDao getCd() {
		return cd;
	}
	@Resource(name="commodityDao")
	public void setCd(CommodityDao cd) {
		this.cd = cd;
	}
	public String getCommodityId() {
		return commodityId;
	}
	public void setCommodityId(String commodityId) {
		this.commodityId = commodityId;
	}
	
	public void delete() {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = null;
		JsonObject jo = new JsonObject();
		boolean success = false;
		String reason = "";
		try {
			out = response.getWriter();
		} catch(IOException e) {}
		Commodity c = cd.getCommodityInId(commodityId);
		if(c != null) {
			if(cd.deleteCommodity(c)) {
				success = true;
			} else
				reason = "删除失败";
		} else
			reason = "该商品不存在";
		
		jo.addProperty("success", success);
		jo.addProperty("reason", reason);
		
		out.print(jo.toString());
		out.flush();
		out.close();
	}
}
