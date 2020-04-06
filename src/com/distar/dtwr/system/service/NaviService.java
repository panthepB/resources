package com.distar.dtwr.system.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.distar.dtwr.system.domain.Group;
import com.distar.dtwr.system.domain.Module;
import com.distar.dtwr.system.domain.Task;

public class NaviService {
	private static final String PREFIX_GROUP = "";
	private static final String BG_DIV_UL = "<div>\n<ul>\n";
	private static final String END_DIV_UL = "</ul>\n</div>\n";

	private HashMap<Long, Group> groupMap;
	private HashMap<Long, Module> moduleMap;
	private String urlRoot;

	public NaviService() {
		groupMap = new HashMap<Long, Group>();
	}

	public NaviService(String urlRoot) {
		this();
		this.urlRoot = urlRoot;
	}

	public Group getGroup(Group group) {
		if (!groupMap.containsKey(group.getId())) {
			group.setModuleMap(new HashMap<Long, Module>());
			groupMap.put(group.getId(), group);
		}
		return groupMap.get(group.getId());
	}

	public Module getModule(Module module) {
		Group group = getGroup(module.getGroup());
		HashMap<Long, Module> moduleMap = group.getModuleMap();
		if (!moduleMap.containsKey(module.getId())) {
			module.setTasks(new ArrayList<Task>());
			moduleMap.put(module.getId(), module);
		}
		return moduleMap.get(module.getId());

	}

	public void addTask(Task task) {
		Module module = getModule(task.getModule());
		module.getTasks().add(task);
	}

	public void addTasks(List<Task> tasks) {
		for (Task task : tasks)
			addTask(task);
	}

	public NaviView getNaviView() {
		StringBuffer buff = new StringBuffer();

		long m = 1;
		for (Group group : getGroups()) {
			m++;
			long s = 0;
			for (Module module : group.getModules()) {
				s++;
				buff.append("<li id=\"m" + m + "s" + s + "\"> <a href=\"javascript:void(0)\"><i class=\"" + module.getDesc() + "\"></i>" + module.getName()
						+ "<span class=\"fa fa-chevron-down\"></span></a>\n");
				buff.append("<ul class=\"nav child_menu\">\n");
				for (Task task : module.getTasks()) {
					buff.append("<li><a href=\"" + urlRoot + task.getUrlPage() + "\">" + task.getName() + "</a></li>\n");
				}
				buff.append("</ul>\n");
				buff.append("</li>\n");
			}
		}

		NaviView naviView = new NaviView();
		naviView.setCode(new String(buff));
		naviView.setSizeGroup(groupMap.size());

		return naviView;
	}

	public List<Group> getGroups() {
		List<Group> groups = new ArrayList<Group>(groupMap.values());
		Collections.sort(groups);
		return groups;
	}

}
