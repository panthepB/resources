package com.distar.dtwr.company.repository;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.distar.dtwr.company.domain.Position;

import distar.project.dao.GenericHibernateDAO;

public class PositionDAOImpl extends GenericHibernateDAO<Position, Long>
		implements PositionDAO {

	@Override
	public List<Position> findByCompany(Long compId) {
		DetachedCriteria criteria = createDetachedCriteria();
		criteria.add(Restrictions.eq("company.id", compId));
		return findByCriteria(criteria);
	}

	@Override
	public Position findByNameEng(Long compId, String nameEng) {
		DetachedCriteria criteria = createDetachedCriteria();
		criteria.add(Restrictions.eq("company.id", compId));
		criteria.add(Restrictions.eq("nameEng", nameEng));

		List<Position> positions = findByCriteria(criteria);
		if (positions.size() > 0)
			return positions.get(0);
		return null;
	}

	@Override
	public List<Position> findEmpPosition(Long compId) {
		DetachedCriteria criteria = createDetachedCriteria();
		criteria.add(Restrictions.eq("company.id", compId));
		criteria
				.add(Restrictions.not(Restrictions.like("nameEng", "student%")));
		criteria.add(Restrictions.ne("nameEng", "parent"));
		criteria.add(Restrictions.ne("nameEng", "admin"));
		criteria.add(Restrictions.eq("status", 1));
		return findByCriteria(criteria);
	}

}
