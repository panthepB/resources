package com.distar.dtwr.system.domain;

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
import javax.persistence.Transient;

import com.distar.dtwr.company.domain.Position;

@Entity
@Table(name = "sys_task")
public class Task implements Serializable, Comparable<Task> {
	private Long id;
	private String name;
	private String urlPage;
	private int ordering;
	private Module module;
	private List<Position> positions;
	private List<Company> companies;

	@Id
	@GeneratedValue
	@Column(name = "task_id")
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

	public String getUrlPage() {
		return urlPage;
	}

	public void setUrlPage(String urlPage) {
		this.urlPage = urlPage;
	}

	public int getOrdering() {
		return ordering;
	}

	public void setOrdering(int ordering) {
		this.ordering = ordering;
	}

	@ManyToOne
	@JoinColumn(name = "module_fk")
	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	@ManyToMany
	@JoinTable(name = "comp_position_tasks", joinColumns = { @JoinColumn(name = "task_id") }, inverseJoinColumns = { @JoinColumn(name = "position_id") })
	public List<Position> getPositions() {
		return positions;
	}

	public void setPositions(List<Position> positions) {
		this.positions = positions;
	}

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "sys_company_tasks", joinColumns = { @JoinColumn(name = "task_id") }, inverseJoinColumns = { @JoinColumn(name = "comp_id") })
	public List<Company> getCompanies() {
		return companies;
	}

	public void setCompanies(List<Company> companies) {
		this.companies = companies;
	}

	@Transient
	public String getModuleName() {
		return module.getName();
	}

	@Override
	public int compareTo(Task o) {
		return ordering - o.ordering;
	}
}
