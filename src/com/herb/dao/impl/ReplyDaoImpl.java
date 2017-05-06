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

import com.herb.bean.Reply;
import com.herb.dao.ReplyDao;

@Component("replyDao")
public class ReplyDaoImpl implements ReplyDao {

	private HibernateTemplate hibernateTemplate;
	@Resource(name = "hibernateTemplate")
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	@Override
	public boolean saveReply(Reply reply) {
		// TODO Auto-generated method stub
		try {
			hibernateTemplate.save(reply);
			return true;
		} catch(DataAccessException e) {
			return false;
		}
	}

	@Override
	public boolean deleteReply(final int replyId) {
		// TODO Auto-generated method stub
		return hibernateTemplate.execute(new HibernateCallback<Boolean>() {
			@Override
			public Boolean doInHibernate(Session session) throws HibernateException, SQLException {
				int result = session.createQuery("delete from Reply r where r.replyId = :replyId").setInteger("replyId", replyId).executeUpdate();
				if(result > 0)
					return true;
				else
					return false;
			}
		});
	}

	@Override
	public List<Reply> getReplysByUserId(final String userId) {
		// TODO Auto-generated method stub
		return hibernateTemplate.execute(new HibernateCallback<List<Reply>>() {
			@SuppressWarnings("unchecked")
			@Override
			public List<Reply> doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from Reply r where r.replyUserId = :userId";
				Query q = session.createQuery(hql);
				q.setString("userId", userId);
				return (List<Reply>)q.list();
			}
		});
	}

	@Override
	public List<Reply> getReplysBuCommodityId(final String commodityId) {
		// TODO Auto-generated method stub
		return hibernateTemplate.execute(new HibernateCallback<List<Reply>>() {
			@SuppressWarnings("unchecked")
			@Override
			public List<Reply> doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from Reply r where r.commodityId = :commodityId";
				Query q = session.createQuery(hql);
				q.setString("commodityId", commodityId);
				return (List<Reply>)q.list();
			}
		});
	}

}
