package com.distar.dtwr.company.web;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import com.distar.dtwr.company.domain.Notice;
import com.distar.dtwr.company.domain.Position;
import com.distar.dtwr.company.domain.User;
import com.distar.dtwr.company.repository.NoticeDAO;
import com.distar.dtwr.company.repository.PositionDAO;
import com.distar.dtwr.company.service.PositionForm;
import com.distar.dtwr.company.service.PrivilegeForm;
import com.distar.dtwr.system.domain.Task;
import com.distar.dtwr.system.repository.CompanyDAO;
import com.distar.dtwr.system.repository.TaskDAO;
import com.distar.dtwr.system.service.NaviService;
import com.distar.dtwr.system.web.UsableController;

public class SettingController extends UsableController {
	final Log logger = LogFactory.getLog(getClass());
	private PositionDAO positionDAO;
	private CompanyDAO companyDAO;
	private TaskDAO taskDAO;
	private NoticeDAO noticeDAO;

	public void setPositionDAO(PositionDAO positionDAO) {
		this.positionDAO = positionDAO;
	}

	public void setCompanyDAO(CompanyDAO schoolDAO) {
		this.companyDAO = schoolDAO;
	}

	public void setTaskDAO(TaskDAO taskDAO) {
		this.taskDAO = taskDAO;
	}

	public void setNoticeDAO(NoticeDAO noticeDAO) {
		this.noticeDAO = noticeDAO;
	}

	public ModelAndView defaultRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (super.chkSession(request)) {
			return new ModelAndView("redirect:/member.htm");
		}
		return new ModelAndView("redirect:/");
	}

	public ModelAndView setPosition(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (super.chkSession(request)) {
			User user = (User) request.getSession().getAttribute(
					UsableController.USER_LOGIN);
			List<Position> positions = positionDAO.findByCompany(user
					.getCompany().getId());

			PositionForm positionForm = new PositionForm();
			if (request.getParameter("id") != null) {
				positionForm.setData(positionDAO.findById(Long
						.parseLong(request.getParameter("id"))));
			} else {
				positionForm.setSchool(user.getCompany().getId());
			}

			ModelMap modelMap = new ModelMap();
			modelMap.addAttribute("positions", positions);
			modelMap.addAttribute("positionForm", positionForm);
			return new ModelAndView("/setting/setPosition", modelMap);
		}
		return new ModelAndView("redirect:/");
	}

	public ModelAndView savePosition(HttpServletRequest request,
			HttpServletResponse response, PositionForm positionForm)
			throws Exception {
		if (super.chkSession(request)) {
			Position position = null;
			if (request.getParameter("submitStatus").equals("add")) {
				position = new Position();
				positionForm.insertModelPostion(position);
				position.setCompany(companyDAO.findById(positionForm
						.getSchool()));
				positionDAO.persist(position);
			}
			if (request.getParameter("submitStatus").equals("save")) {
				position = positionDAO.findById(positionForm.getId());
				
				position.setName(positionForm.getName());

				
				positionForm.insertModelPostion(position);
				
				positionDAO.merge(position);
				
				
				position = new Position();
				position.setName(positionForm.getName());
				positionDAO.persist(position);
				
				
			}
			if (request.getParameter("submitStatus").equals("del")) {
				if (!positionForm.getNameEng().equals("student")
						&& !positionForm.getNameEng().equals("parent")
						&& !positionForm.getNameEng().equals("librarian")
						&& !positionForm.getNameEng().equals("admin")) {
					position = positionDAO.findById(positionForm.getId());
					positionDAO.remove(position);
				}
			}

			return new ModelAndView("redirect:/setting/setPosition.htm");
		}
		return new ModelAndView("redirect:/");
	}

	public ModelAndView setPrivilege(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (super.chkSession(request)) {
			User userLogin = (User) request.getSession().getAttribute(
					UsableController.USER_LOGIN);
			List<Position> positions = positionDAO.findByCompany(userLogin
					.getCompany().getId());

			PrivilegeForm privilegeForm = null;
			NaviService naviService = new NaviService();
			if (request.getParameter("id") != null) {
				naviService.addTasks(taskDAO.findByCompany(userLogin
						.getCompany().getId()));
				Position position = positionDAO.findById(Long.parseLong(request
						.getParameter("id")));
				privilegeForm = new PrivilegeForm(position);
			}

			ModelMap modelMap = new ModelMap();
			modelMap.addAttribute("positions", positions);
			modelMap.addAttribute("groups", naviService.getGroups());
			modelMap.addAttribute("privilegeForm", privilegeForm);
			return new ModelAndView("/setting/setPrivilege", modelMap);
		}
		return new ModelAndView("redirect:/");
	}

	public ModelAndView savePrivilege(HttpServletRequest request,
			HttpServletResponse response, PrivilegeForm privilegeForm)
			throws Exception {
		if (super.chkSession(request)) {
			Position position = positionDAO.findById(privilegeForm
					.getPositionId());
			List<Task> positionTasks = new ArrayList<Task>();
			if (privilegeForm.getPositionTasks() != null) {
				List<Long> positionTasksL = privilegeForm.getPositionTasks();
				for (Long id : positionTasksL) {
					positionTasks.add(taskDAO.findById(id));
				}
				position.setTasks(positionTasks);
			} else {
				position.setTasks(new ArrayList<Task>());
			}
			positionDAO.merge(position);
			return new ModelAndView("redirect:/setting/setPrivilege.htm");
		}
		return new ModelAndView("redirect:/");
	}

	public ModelAndView setNotice(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (super.chkSession(request)) {
			User userLogin = (User) request.getSession().getAttribute(
					UsableController.USER_LOGIN);

			List<Notice> notices = noticeDAO.findByProperty("company.id",
					userLogin.getCompany().getId());

			Notice noticeForm = null;
			if (request.getParameter("id") != null) {
				noticeForm = noticeDAO.findById(Long.parseLong(request
						.getParameter("id")));
				noticeForm.setDesc(noticeForm.getDesc4Input());
			} else {
				noticeForm = new Notice();
			}

			ModelMap modelMap = new ModelMap();
			modelMap.addAttribute("notices", notices);
			modelMap.addAttribute("noticeForm", noticeForm);
			return new ModelAndView("/setting/setNotice", modelMap);
		}
		return new ModelAndView("redirect:/");
	}

	public ModelAndView saveNotice(HttpServletRequest request,
			HttpServletResponse response, Notice noticeForm) throws Exception {
		if (super.chkSession(request)) {
			User userLogin = (User) request.getSession().getAttribute(
					UsableController.USER_LOGIN);

			if (request.getParameter("submitStatus").equals("add")) {
				noticeForm.setCreateDate(new Date(System.currentTimeMillis()));
				noticeForm.setCreateTime(new Time(System.currentTimeMillis()));
				noticeForm.setCompany(userLogin.getCompany());
				noticeForm.setDesc(noticeForm.getDesc4Save());
				noticeDAO.persist(noticeForm);
			} else if (request.getParameter("submitStatus").equals("save")) {
				Notice notice = noticeDAO.findById(noticeForm.getId());
				notice.setSubject(noticeForm.getSubject());
				notice.setDesc(noticeForm.getDesc4Save());
				noticeDAO.merge(notice);
			} else if (request.getParameter("submitStatus").equals("del")) {
				Notice notice = noticeDAO.findById(noticeForm.getId());
				noticeDAO.remove(notice);
			}

			return new ModelAndView("redirect:/setting/setNotice.htm");
		}
		return new ModelAndView("redirect:/");
	}
}
