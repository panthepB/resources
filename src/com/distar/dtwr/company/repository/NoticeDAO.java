package com.distar.dtwr.company.repository;

import java.util.List;

import com.distar.dtwr.company.domain.Notice;

public interface NoticeDAO extends distar.project.dao.GenericDAO<Notice, Long> {

	public List<Notice> getLastNews(Long compId, int n);

}
