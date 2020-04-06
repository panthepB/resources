package com.distar.dtwr.system.repository;

import java.util.List;

import com.distar.dtwr.system.domain.Task;

import distar.project.dao.GenericDAO;

public interface TaskDAO extends GenericDAO<Task, Long> {

	public List<Task> findByPosition(Long positionId);

	public List<Task> findByCompany(Long compId);
}
