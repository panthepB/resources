package com.distar.dtwr.company.repository;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.distar.dtwr.company.domain.Notice;

import distar.project.dao.GenericHibernateDAO;

public class NoticeDAOImpl extends GenericHibernateDAO<Notice, Long> implements
		NoticeDAO {

	@Override
	public List<Notice> getLastNews(Long compId, int n) {
		DetachedCriteria criteria = createDetachedCriteria();
		criteria.add(Restrictions.eq("company.id", compId));
		criteria.addOrder(Order.desc("createDate"));
		criteria.addOrder(Order.desc("createTime"));
		return findByCriteria(criteria, 0, n);
	}

}
