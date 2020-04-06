package com.distar.dtwr.company.repository;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.distar.dtwr.company.domain.User;
import com.distar.dtwr.company.service.SearchEmpForm;

import distar.project.dao.GenericDAO;

public interface UserDAO extends GenericDAO<User, Long> {

	public User findByUserPass(Long compId, String username, String password);

	public User findEmpByCode(Long compId, String code);

	public User findByUsername(Long compId, String username);

	public DetachedCriteria createCriteriaFromEmpForm(
			SearchEmpForm searchEmpForm, Long compId);

	public long sizeBySearchEmpForm(SearchEmpForm searchEmpForm, Long compId);

	public List<User> findBySearchEmpForm(SearchEmpForm searchEmpForm,
			Long compId);

	public List<User> findBySearchEmpForm(SearchEmpForm searchEmpForm,
			Long compId, int startRecord, int maxResults);

	public List<User> findBySupport(Long compId);

}
