package com.herb.dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Component;

import com.herb.bean.ShopCar;
import com.herb.bean.UserId_CommodityId;
import com.herb.dao.ShopCarDao;

@Component("shopCarDao")
public class ShopCarDaoImpl implements ShopCarDao {

	private HibernateTemplate hibernateTemplate;
	@Resource(name = "hibernateTemplate")
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	@Override
	public boolean deleteCommodityInShopCar(final String userId, final String[] commodityIds) {
		// TODO Auto-generated method stub
		boolean flag = true;
		for(final String commodityId : commodityIds) {
			flag = hibernateTemplate.execute(new HibernateCallback<Boolean>() {
				@Override
				public Boolean doInHibernate(Session session) throws HibernateException, SQLException {
					int result = session.createQuery("delete from UserId_CommodityId uc where uc.userId = :userId and uc.commodityId = :commodityId").setString("userId", userId).setString("commodityId", commodityId).executeUpdate();
					if(result > 0)
						return true;
					else
						return false;
				}
			});
		}
		return flag;
	}

	@Override
	public ShopCar getShopCar(final String userId) {
		// TODO Auto-generated method stub
		ShopCar sc = new ShopCar();
		@SuppressWarnings("unchecked")
		Vector<String> cstr = (Vector<String>) hibernateTemplate.executeFind(new HibernateCallback<Vector<String>>() {
			@Override
			public Vector<String> doInHibernate(Session session) throws HibernateException, SQLException {
				Vector<String> strs = new Vector<>();
				List<String> listStrs = session.createQuery("select commodityId from UserId_CommodityId uc where uc.userId = :userId").setString("userId", userId).list();
				for(String s : listStrs)
					strs.add(s);
				return strs;
			}
		});
		sc.setUserId(userId);
		sc.setProductIds(cstr);
		return sc;
	}

	@Override
	public boolean saveUserIdCommodityId(String userId, String commodityId) {
		// TODO Auto-generated method stub
		UserId_CommodityId uc = new UserId_CommodityId(userId, commodityId);
		try {
			hibernateTemplate.save(uc);
			return true;
		} catch(DataAccessException e) {
			return false;
		}
	}
}
