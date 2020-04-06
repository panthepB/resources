package com.distar.dtwr.company.service;

import java.util.ArrayList;
import java.util.List;

import com.distar.dtwr.company.domain.Position;
import com.distar.dtwr.system.domain.Task;

public class PrivilegeForm {
	Long positionId;
	Position position;
	List<Long> positionTasks;

	public PrivilegeForm() {
	}

	public PrivilegeForm(Position position) {
		setPosition(position);
		positionTasks = new ArrayList<Long>();
		for (Task task : position.getTasks()) {
			positionTasks.add(task.getId());
		}
	}

	public void setPositionId(Long positionId) {
		this.positionId = positionId;
	}

	public Long getPositionId() {
		return positionId;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
		this.positionId = position.getId();
	}

	public List<Long> getPositionTasks() {
		return positionTasks;
	}

	public void setPositionTasks(List<Long> positionTasks) {
		this.positionTasks = positionTasks;
	}

}
