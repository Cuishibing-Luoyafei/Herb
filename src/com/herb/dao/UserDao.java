package com.herb.dao;

import com.herb.bean.User;

public interface UserDao {
	public boolean saveUser(User user);
	public boolean updateUser(User user);
	public boolean deleteUser(String userId);
	public User getUserInId(String userId);
	/**
	 * 将整个User传入，提高灵活性
	 * @param user
	 * @return
	 */
	public User getUser(final User user);
	public User getUserByNickName(final String nickName);
}
