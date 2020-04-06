package com.distar.dtwr.system.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "sys_group")
public class Group implements Serializable, Comparable<Group> {
	private Long id;
	private String name;
	private String desc;
	private int ordering;
	private HashMap<Long, Module> moduleMap;

	@Id
	@GeneratedValue
	@Column(name = "group_id")
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

	@Column(name = "description")
	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getOrdering() {
		return ordering;
	}

	public void setOrdering(int ordering) {
		this.ordering = ordering;
	}

	@Transient
	public HashMap<Long, Module> getModuleMap() {
		return moduleMap;
	}

	public void setModuleMap(HashMap<Long, Module> moduleMap) {
		this.moduleMap = moduleMap;
	}

	@Transient
	public List<Module> getModules() {
		List<Module> modules = new ArrayList<Module>(moduleMap.values());
		Collections.sort(modules);
		for (Module module : modules) {
			Collections.sort(module.getTasks());
		}
		return modules;
	}

	@Override
	public int compareTo(Group o) {
		return ordering - o.ordering;
	}

}
