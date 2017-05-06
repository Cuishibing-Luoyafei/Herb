package com.herb.bean;
/**
 * 用户类型：0:二者都是;1:买家；2:卖家
 * @author Diamond
 *
 */
public enum UserType {
	BOTH_TYPE(0),
	CREATOR_TYPE(1),
	SELLER_TYPE(2);
	private int utContent;
	private UserType(int utContent) {
		this.utContent = utContent;
	}
	@Override
    public String toString() {
        return String.valueOf(this.utContent);
    } 
}