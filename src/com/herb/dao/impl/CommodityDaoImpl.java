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

import com.herb.bean.Commodity;
import com.herb.dao.CommodityDao;

@Component("commodityDao")
public class CommodityDaoImpl implements CommodityDao {
	private HibernateTemplate hibernateTemplate;

	@Override
	public boolean saveCommodity(Commodity cmdty) {
		try {
			hibernateTemplate.save(cmdty);
			return true;
		} catch (DataAccessException e) {
			return false;
		}
	}

	@Override
	public boolean updateCommodity(Commodity cmdty){
		try{
				hibernateTemplate.update(cmdty);
				return true;
		}catch(DataAccessException e){
				System.out.println("在Commodityimpl中,更新commodity时出现异常");
				return false;
		}
	}

	@Override
	public boolean deleteCommodity(Commodity cmdty) {
		try{
			hibernateTemplate.delete(cmdty);
			return true;
		}catch(DataAccessException e){
			return false;
		}
	}

	@Override
	public Commodity getCommodityInId(String cmdtyId) {
		return hibernateTemplate.get(Commodity.class, cmdtyId);
	}

	@Override
	public Commodity getCommodity(Commodity cmty){
			//调用getCommodityInId(String);
			return getCommodityInId(cmty.getCommodityId());
	}

	@Resource(name = "hibernateTemplate")
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	@Override
	public List<Commodity> getCommodityBySearch(final String content) {
		// TODO Auto-generated method stub
		try {
			return hibernateTemplate.execute(new HibernateCallback<List<Commodity>>() {
				@SuppressWarnings("unchecked")
				@Override
				public List<Commodity> doInHibernate(Session session) throws HibernateException, SQLException {
					String hql = "from Commodity c where c.commodityName like :commodityName";
					Query q = session.createQuery(hql);
					q.setString("commodityName", "%" + content + "%");
					return (List<Commodity>)q.list();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Commodity> getCommoditysByCategory(final String category, final int start, final int length) {
		// TODO Auto-generated method stub
		try {
			return hibernateTemplate.execute(new HibernateCallback<List<Commodity>>() {
				@SuppressWarnings("unchecked")
				@Override
				public List<Commodity> doInHibernate(Session session) throws HibernateException, SQLException {
					String hql = "from Commodity c where c.commodityCategary = :commodityCategary";
					Query q = session.createQuery(hql);
					q.setString("commodityCategary", category).setFirstResult(start).setMaxResults(length);
					return (List<Commodity>)q.list();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
