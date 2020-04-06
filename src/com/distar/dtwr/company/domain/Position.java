package com.distar.dtwr.company.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.distar.dtwr.system.domain.Company;
import com.distar.dtwr.system.domain.Task;

@Entity
@Table(name = "comp_position")
public class Position implements Serializable {
	public static final String ADMIN_NAME_ENG = "admin";
	public static final String SUPPORT_NAME_ENG = "support";
	private Long id;
	private String name;
	private String nameEng;
	private String desc;
	private int status;
	private Company company;
	private List<Task> tasks;

	@Id
	@GeneratedValue
	@Column(name = "position_id")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "name")
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

	@Column(name = "description")
	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	@Column(name = "status")
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@ManyToOne
	@JoinColumn(name = "comp_fk")
	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "comp_position_tasks", joinColumns = { @JoinColumn(name = "position_id") }, inverseJoinColumns = { @JoinColumn(name = "task_id") })
	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}
}
