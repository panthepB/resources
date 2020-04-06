package com.distar.dtwr.company.repository;

import java.util.List;

import com.distar.dtwr.company.domain.Position;

import distar.project.dao.GenericDAO;

public interface PositionDAO extends GenericDAO<Position, Long> {

	public List<Position> findByCompany(Long compId);

	public Position findByNameEng(Long compId, String nameEng);

	public List<Position> findEmpPosition(Long compId);

}
