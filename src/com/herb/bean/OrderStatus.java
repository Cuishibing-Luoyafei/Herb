package com.herb.bean;

/**
 * 订单状态:0:未创建;1:订单成功;2:订单作废;
 * @author Diamond
 *
 */
public enum OrderStatus {
	NONE_STATUS(0),
	SUCCESS_STATUS(1),
	CANCEL_STATUS(2);
	private int utContent;
	private OrderStatus(int utContent) {
		this.utContent = utContent;
	}
	@Override
    public String toString() {
        return String.valueOf(this.utContent);
    } 
}
