package com.herb.bean;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 给商品留言的
 */
@Entity
@Table(name="t_reply")
public class Reply {

	private int replyId;
	private String commodityId;
	private String replyUserId;
	private String replyContent = "";
	private Timestamp replyTime;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public int getReplyId() {
		return replyId;
	}
	public void setReplyId(int replyId) {
		this.replyId = replyId;
	}
	public String getCommodityId() {
		return commodityId;
	}
	public void setCommodityId(String commodityId) {
		this.commodityId = commodityId;
	}
	public String getReplyUserId() {
		return replyUserId;
	}
	public void setReplyUserId(String replyUserId) {
		this.replyUserId = replyUserId;
	}
	public String getReplyContent() {
		return replyContent;
	}
	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}
	public Timestamp getReplyTime() {
		return replyTime;
	}
	public void setReplyTime(Timestamp replyTime) {
		this.replyTime = replyTime;
	}
	
}
