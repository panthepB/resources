package com.distar.dtwr.company.service;

import com.distar.dtwr.company.domain.Position;

public class PositionForm {
	private Long id;
	private String name;
	private String nameEng;
	private String desc;
	private int status;
	private Long school;

	public PositionForm() {
		status = 1;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNameEng() {
		return nameEng;
	}

	public void setNameEng(String nameEng) {
		this.nameEng = nameEng;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Long getSchool() {
		return school;
	}

	public void setSchool(Long school) {
		this.school = school;
	}

	public void setData(Position position) {
		id = position.getId();
		name = position.getName();
		nameEng = position.getNameEng();
		desc = position.getDesc();
		status = position.getStatus();
	}

	public void insertModelPostion(Position position) {
		position.setName(name);
		position.setNameEng(nameEng);
		position.setDesc(desc);
		position.setStatus(status);
	}
}
