package com.herb.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Component;

import com.herb.bean.User;
import com.herb.dao.UserDao;

@Component("userDao")
public class UserDaoImpl implements UserDao {

	private HibernateTemplate hibernateTemplate;

	@Override
	public boolean saveUser(User user) {
		try {
			hibernateTemplate.save(user);
			return true;
		} catch (DataAccessException e) {
System.out.println("在UserDaoImpl中，保存用户信息时出现异常！");
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean updateUser(User user) {
		try {
			hibernateTemplate.update(user);
			return true;
		} catch (DataAccessException e) {
System.out.println("在UserDaoImpl中，更新用户信息时出现异常！");
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean deleteUser(final String userId) {
		try {
			return hibernateTemplate.execute(new HibernateCallback<Boolean>() {
				@Override
				public Boolean doInHibernate(Session session) throws HibernateException, SQLException {
					String hql = "delete User where userId=?";
					Query q = session.createQuery(hql);
					q.setParameter("userId", userId);
					q.executeUpdate();
					return false;
				}

			});
		} catch (Exception e) {
System.out.println("在UserDaoImpl中，删除用户信息时出现异常！");
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public User getUserInId(String userId) {
		
		return	hibernateTemplate.get(User.class, userId);
		/*try {
			return hibernateTemplate.execute(new HibernateCallback<User>() {

				@Override
				public User doInHibernate(Session session) throws HibernateException, SQLException {
					String hql = "from User where userId=?";
					Query q = session.createQuery(hql);
					q.setParameter("userId", userId);
					List<User> users = q.list();
					if(users!=null&&users.size()==1)
						return users.get(0);
					return null;
				}
				
			});
		} catch (Exception e) {
			return null;
		}*/
	}

	@Override
	public User getUserByNickName(final String userNickName) {
		try {
			return hibernateTemplate.execute(new HibernateCallback<User>() {

				@Override
				public User doInHibernate(Session session) throws HibernateException, SQLException {
					String hql = "from User where userNickName = :userNickName";
					@SuppressWarnings("unchecked")
					List<User> users = session.createQuery(hql).setString("userNickName", userNickName).list();
					if(users != null && users.size() == 1)
						return users.get(0);
					else
						return null;
				}
			});
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public User getUser(final User user) {
		
		if(user != null) {
			/**
			 * 根据id获取user对象
			 */
			if(user.getUserId() != null) {
				return hibernateTemplate.get(User.class, user.getUserId());
			}
			
			/**
			 * 根据昵称获取user对象
			 */
			if(user.getUserNickName() != null) {
				return getUserByNickName(user.getUserNickName());
			}
		} else
			return null;
		return user;
	}

	@Resource(name = "hibernateTemplate")
	public void setHihernateTemplate(HibernateTemplate hihernateTemplate) {
		this.hibernateTemplate = hihernateTemplate;
	}

}
