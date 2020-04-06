package com.distar.dtwr.company.repository;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.distar.dtwr.company.domain.Position;
import com.distar.dtwr.company.domain.User;
import com.distar.dtwr.company.service.SearchEmpForm;

import distar.project.dao.GenericHibernateDAO;

public class UserDAOImpl extends GenericHibernateDAO<User, Long> implements
		UserDAO {

	@Override
	public User findByUserPass(Long compId, String username, String password) {
		DetachedCriteria criteria = createDetachedCriteria();
		criteria.add(Restrictions.eq("company.id", compId));
		criteria.add(Restrictions.eq("username", username));
		criteria.add(Restrictions.eq("password", password));

		List<User> users = findByCriteria(criteria);
		if (users.size() > 0)
			return users.get(0);
		return null;
	}

	@Override
	public User findByUsername(Long compId, String username) {
		DetachedCriteria criteria = createDetachedCriteria();
		criteria.add(Restrictions.eq("company.id", compId));
		criteria.add(Restrictions.eq("username", username));

		List<User> users = findByCriteria(criteria);
		if (users.size() > 0)
			return users.get(0);
		return null;
	}

	@Override
	public User findEmpByCode(Long compId, String code) {
		DetachedCriteria criteria = createDetachedCriteria();
		criteria.add(Restrictions.eq("company.id", compId));

		List<User> users = findByCriteria(criteria);
		if (users.size() > 0)
			return users.get(0);
		return null;
	}

	@Override
	public long sizeBySearchEmpForm(SearchEmpForm searchEmpForm, Long compId) {
		return countByCriteria(createCriteriaFromEmpForm(searchEmpForm, compId));
	}

	@Override
	public List<User> findBySearchEmpForm(SearchEmpForm searchEmpForm,
			Long compId) {
		return findByCriteria(createCriteriaFromEmpForm(searchEmpForm, compId));
	}

	@Override
	public List<User> findBySearchEmpForm(SearchEmpForm searchEmpForm,
			Long compId, int startRecord, int maxResults) {
		return findByCriteria(createCriteriaFromEmpForm(searchEmpForm, compId),
				startRecord, maxResults);
	}

	@Override
	public List<User> findBySupport(Long compId) {
		DetachedCriteria criteria = createDetachedCriteria();
		criteria.add(Restrictions.eq("company.id", compId));
		criteria.createAlias("position", "position");
		criteria.add(Restrictions.eq("position.nameEng",
				Position.SUPPORT_NAME_ENG));
		return findByCriteria(criteria);

	}

	@Override
	public DetachedCriteria createCriteriaFromEmpForm(SearchEmpForm searchEmpForm, Long compId) {
		// TODO Auto-generated method stub
		return null;
	}
}
