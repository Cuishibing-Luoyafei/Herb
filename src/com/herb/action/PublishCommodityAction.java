package com.herb.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.gson.JsonObject;
import com.herb.bean.Commodity;
import com.herb.dao.CommodityDao;
import com.herb.dao.UserDao;
import com.herb.tools.UploadFileUtil;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
@Component("publishCommodity")
@Scope("prototype")
public class PublishCommodityAction extends ActionSupport {

	@Override
	public String toString() {
		return "PublishCommodityAction [commodityName=" + commodityName + ", commodityCategary=" + commodityCategary
				+ ", isOrder=" + isOrder + ", commodityDescribe=" + commodityDescribe + ", commodityCount="
				+ commodityCount + ", commodityNewPrice=" + commodityNewPrice + ", commodifyCreateDate="
				+ commodifyCreateDate + ", commodityOwner=" + commodityOwner + ", pictureFileName=" + pictureFileName
				+ ", pictureContentType=" + pictureContentType + "]";
	}

	/**
	 * 图片存入数据库的地址s
	 */
	private static final String PICTURE_DB = ServletActionContext.getServletContext().getInitParameter("pictureDB");
	/**
	 * 图片存入硬盘的地址
	 */
	private static final String PICTURE_DISK = ServletActionContext.getServletContext().getInitParameter("pictureDisk");
	
	private String commodityName = null;//物品名称
	private String commodityCategary = null;//物品类别(0:出售的商品，1:预购的商品，2:二手物品交换)
	private String isOrder = null;//是否被定(0:正在出售,1:已被订购,2:已出售)
	private String commodityDescribe = null;//物品描述
	private Integer commodityCount = -1;//物品数量
	private String commodityNewPrice = null;//原价
	private Timestamp commodifyCreateDate = null;//创建时间
	private String commodityOwner = null;//拥有者Id
	
	private File picture;//接收的上传图片
	private String pictureFileName;
	private String pictureContentType;
	
	private CommodityDao cd;
	private UserDao ud;
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
		
	public File getPicture() {
		return picture;
	}
	public void setPicture(File picture) {
		this.picture = picture;
	}
	public String getPictureFileName() {
		return pictureFileName;
	}
	public void setPictureFileName(String pictureFileName) {
		this.pictureFileName = pictureFileName;
	}
	public String getPictureContentType() {
		return pictureContentType;
	}
	public void setPictureContentType(String pictureContentType) {
		this.pictureContentType = pictureContentType;
	}
	public String getCommodityName() {
		return commodityName;
	}
	public void setCommodityName(String commodityName) {
		this.commodityName = commodityName;
	}
	public String getCommodityCategary() {
		return commodityCategary;
	}
	public void setCommodityCategary(String commodityCategary) {
		this.commodityCategary = commodityCategary;
	}
	public String getIsOrder() {
		return isOrder;
	}
	public void setIsOrder(String isOrder) {
		this.isOrder = isOrder;
	}
	public String getCommodityDescribe() {
		return commodityDescribe;
	}
	public void setCommodityDescribe(String commodityDescribe) {
		this.commodityDescribe = commodityDescribe;
	}
	public Integer getCommodityCount() {
		return commodityCount;
	}
	public void setCommodityCount(Integer commodityCount) {
		this.commodityCount = commodityCount;
	}
	public String getCommodityNewPrice() {
		return commodityNewPrice;
	}
	public void setCommodityNewPrice(String commodityNewPrice) {
		this.commodityNewPrice = commodityNewPrice;
	}
	public Timestamp getCommodifyCreateDate() {
		return commodifyCreateDate;
	}
	public void setCommodifyCreateDate(Timestamp commodifyCreateDate) {
		this.commodifyCreateDate = commodifyCreateDate;
	}
	public String getCommodityOwner() {
		return commodityOwner;
	}
	public void setCommodityOwner(String commodityOwner) {
		this.commodityOwner = commodityOwner;
	}
	
	public void publishCommodity() {
System.out.println(toString());
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
			 * 自定义上传的图像名
			 */
			pictureFileName = UUID.randomUUID().toString().replace("-", "") + ".jpg";
			
			/**
			 * 获取存入硬盘的具体地址
			 */
			String url = PICTURE_DISK + pictureFileName;
			/**
			 * 根据全路径，将文件创建出来。
			 */
			File file = new File(url);
			
			/**
			 * 标识，创建文件是否成功
			 * 使用上传文件工具类
			 */
			boolean create = UploadFileUtil.justDoIt(picture, file);

			/**
			 * 如果创建成功，则进行往数据库用户表中更新
			 */
			if(create) {
				
				Commodity cmdy = new Commodity();
				cmdy.setCommodityName(commodityName);
				cmdy.setCommodityPicture(PICTURE_DB + pictureFileName);
				cmdy.setCommodityCategary(commodityCategary);
				cmdy.setCommodityDescribe(commodityDescribe);
				cmdy.setCommodityCount(commodityCount);
				cmdy.setCommodifyCreateDate(new Timestamp(System.currentTimeMillis()));	
				cmdy.setCommodityNewPrice(commodityNewPrice);
				cmdy.setCommodityOwnerId(commodityOwner);
				
				if(cd.saveCommodity(cmdy)) {
					success = true;
				} else
					reason = "存储失败";
			} else
				reason = "图片写入失败";
			
		
		jo.addProperty("success", success);
		jo.addProperty("reason", reason);
		
		out.print(jo.toString());
		out.flush();
		out.close();
	}
}
