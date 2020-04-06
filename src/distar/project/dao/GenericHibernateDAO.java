package distar.project.dao;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public abstract class GenericHibernateDAO<T, ID extends Serializable>
		implements GenericDAO<T, ID> {

	private Class<T> persistentClass;

	// protected EntityManager entityManager;
	// @Autowired
	protected SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory vSession) {
		sessionFactory = vSession;
	}

	@SuppressWarnings("unchecked")
	public GenericHibernateDAO() {
		this.persistentClass = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}

	public Class<T> getPersistentClass() {
		return persistentClass;
	}

	protected Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	@SuppressWarnings("unchecked")
	public T findById(ID id) {
		return (T) getCurrentSession().get(persistentClass, id);
	}

	public void persist(T entity) {
		getCurrentSession().saveOrUpdate(entity);
	}

	public void merge(T entity) {
		getCurrentSession().saveOrUpdate(entity);
	}

	public void remove(T entity) {
		getCurrentSession().delete(entity);
	}

	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		return findByCriteria();
	}

	@SuppressWarnings("unchecked")
	public List<T> findByExample(T exampleInstance) {
		// return getHibernateTemplate().findByExample(exampleInstance, 0, 1);
		DetachedCriteria criteria = createDetachedCriteria();

		Field[] fields = exampleInstance.getClass().getDeclaredFields();

		for (Field field : fields) {
			if (!field.isAnnotationPresent(Column.class)
					&& !field.isAnnotationPresent(Basic.class)) {
				continue;
			}

			Object value = null;

			try {
				field.setAccessible(true);
				value = field.get(exampleInstance);
			} catch (IllegalArgumentException e) {
				continue;
			} catch (IllegalAccessException e) {
				continue;
			}

			if (value == null) {
				continue;
			}

			criteria.add(Restrictions.eq(field.getName(), value));
		}
		return findByCriteria(criteria);
	}

	/* Use this inside subclasses as a convenience method. */
	@SuppressWarnings("unchecked")
	protected List<T> findByCriteria(Criterion... criterion) {
		DetachedCriteria crit = createDetachedCriteria();
		for (Criterion c : criterion) {
			crit.add(c);
		}
		return findByCriteria(crit);
	}

	/* Use this inside subclasses as a convenience method. */
	@SuppressWarnings("unchecked")
	protected List<T> findByCriteria(DetachedCriteria criteria) {
		return criteria.getExecutableCriteria(getCurrentSession()).list();
	}

	protected List<T> findByCriteria(DetachedCriteria criteria,
			final int firstResult, final int maxResults) {
		Criteria executableCriteria = criteria
				.getExecutableCriteria(getCurrentSession());
		// prepareCriteria(executableCriteria);
		if (firstResult >= 0) {
			executableCriteria.setFirstResult(firstResult);
		}
		if (maxResults > 0) {
			executableCriteria.setMaxResults(maxResults);
		}
		return executableCriteria.list();
	}

	protected DetachedCriteria createDetachedCriteria() {
		return DetachedCriteria.forClass(getPersistentClass());
	}

	@SuppressWarnings("unchecked")
	public List<T> findByProperty(String propertyName, Object value) {
		DetachedCriteria criteria = createDetachedCriteria();
		criteria.add(Restrictions.eq(propertyName, value));
		return findByCriteria(criteria);
	}

	@Override
	public List<T> findByProperty(String propertyName, Object startValue,
			Object endValue) {
		DetachedCriteria criteria = createDetachedCriteria();
		criteria.add(Restrictions.ge(propertyName, startValue));
		criteria.add(Restrictions.le(propertyName, endValue));
		return findByCriteria(criteria);
	}

	@SuppressWarnings("unchecked")
	public long count() {
		List list = getCurrentSession()
				.createQuery(
						"select count(*) from "
								+ getPersistentClass().getName() + " x").list();
		Long count = (Long) list.get(0);
		return count.longValue();
	}

	@Override
	public long countByCriteria(DetachedCriteria criteria) {
		ProjectionList projList = Projections.projectionList();
		projList.add(Projections.count("id"));
		criteria.setProjection(projList);

		List results = criteria.getExecutableCriteria(getCurrentSession())
				.list();
		Integer count = (Integer) results.get(0);

		return count.longValue();
	}

}
