package distar.project.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface GenericDAO<T, ID extends Serializable> {

	public T findById(ID id);

	public List<T> findAll();

	public List<T> findByExample(T exampleInstance);

	public List<T> findByProperty(String propertyName, Object value);

	public List<T> findByProperty(String propertyName, Object startValue,
			Object endValue);

	@Transactional
	public void persist(T entity);

	@Transactional
	public void merge(T entity);

	@Transactional
	public void remove(T entity);

	public long count();

	public long countByCriteria(DetachedCriteria criteria);

}