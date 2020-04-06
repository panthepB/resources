package com.distar.dtwr.system.repository;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.distar.dtwr.system.domain.Task;

import distar.project.dao.GenericHibernateDAO;

public class TaskDAOImpl extends GenericHibernateDAO<Task, Long> implements
		TaskDAO {

	@Override
	public List<Task> findByPosition(Long positionId) {
		DetachedCriteria criteria = createDetachedCriteria();
		criteria.createAlias("positions", "positions");
		criteria.add(Restrictions.eq("positions.id", positionId));
		return findByCriteria(criteria);
	}

	@Override
	public List<Task> findByCompany(Long compId) {
		DetachedCriteria criteria = createDetachedCriteria();
		criteria.createAlias("companies", "companies");
		criteria.add(Restrictions.eq("companies.id", compId));

		criteria.createAlias("module", "module");
		criteria.createAlias("module.group", "group");
		criteria.addOrder(Order.asc("group.id"));
		criteria.addOrder(Order.asc("module.id"));
		criteria.addOrder(Order.asc("ordering"));

		return findByCriteria(criteria);
	}

}
