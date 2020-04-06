package com.distar.dtwr.system.web;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import com.distar.dtwr.company.domain.Notice;
import com.distar.dtwr.company.domain.User;
import com.distar.dtwr.company.repository.NoticeDAO;
import com.distar.dtwr.company.repository.UserDAO;
import com.distar.dtwr.system.domain.Company;
import com.distar.dtwr.system.repository.CompanyDAO;
import com.distar.dtwr.system.repository.TaskDAO;
import com.distar.dtwr.system.service.LoginForm;
import com.distar.dtwr.system.service.NaviService;

import distar.project.DLT.domain.RealtimeStatus;
import distar.project.DLT.repository.RealtimeStatusDAO;
import distar.project.service.ParamImage;


public class IndexController extends UsableController {
	private NoticeDAO noticeDAO;
	private CompanyDAO companyDAO;
	private TaskDAO taskDAO;
	private ParamImage paramImage;
	private UserDAO userDAO;

	public void setNoticeDAO(NoticeDAO noticeDAO) {
		this.noticeDAO = noticeDAO;
	}

	public void setCompanyDAO(CompanyDAO schoolDAO) {
		this.companyDAO = schoolDAO;
	}

	public void setTaskDAO(TaskDAO taskDAO) {
		this.taskDAO = taskDAO;
	}

	public void setParamImage(ParamImage paramImage) {
		this.paramImage = paramImage;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String sId;
		
		if (request.getParameter("compId") != null) {
			sId = request.getParameter("compId");
		} else {
			return new ModelAndView("redirect:/");
		}
		Long compId = Long.parseLong(sId);
		request.getSession().setAttribute("compId", compId);
		Company company = companyDAO.findById(compId);
		request.getSession().setAttribute("companyUrl", company.getWebsite());

		if (super.chkSession(request)) {
			return new ModelAndView("redirect:/member.htm");
		}

		List<Notice> notices = noticeDAO.getLastNews(compId, 10);
		Long timeNew = System.currentTimeMillis() - (86400000 * 7);
		for (Notice notice : notices) {
			if (timeNew <= notice.getCreateDate().getTime())
				notice.setCreateNew(true);
		}

		String dirSlider = paramImage.getPath() + File.separator + compId
				+ File.separator + paramImage.getSliderDir();
		File dir = new File(dirSlider);
		String[] files = dir.list();

		List<String> fileNames = new ArrayList<String>();
		for (int i = 0; i < files.length; i++) {
			if (files[i].toLowerCase().endsWith("jpg")) {
				fileNames.add(files[i]);
			}
		}

		ModelMap modelMap = new ModelMap();
		modelMap.addAttribute("loginForm", new LoginForm());
		modelMap.addAttribute("company", company);
		modelMap.addAttribute("fileNames", fileNames);
		modelMap.addAttribute("notices", notices);
		return new ModelAndView("index", modelMap);
		
	}

	public ModelAndView login(HttpServletRequest request,
			HttpServletResponse response, LoginForm loginForm) throws Exception {
		Long compId = (Long) request.getSession().getAttribute("compId");
		if (compId != null) {
			User userLogin = userDAO.findByUserPass(compId, loginForm
					.getUsername(), loginForm.getPassword());
			if (userLogin != null) {
				NaviService naviService = new NaviService(request
						.getContextPath());

				naviService.addTasks(taskDAO.findByPosition(userLogin
						.getPosition().getId()));

				request.getSession().setAttribute(UsableController.USER_LOGIN,
						userLogin);
				request.getSession().setAttribute("navi",
						naviService.getNaviView().getCode());
				
				

				return new ModelAndView("redirect:/member.htm");
			}
		}
		return new ModelAndView("redirect:/");
	}

	public ModelAndView member(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (super.chkSession(request)) {
			User userLogin = (User) request.getSession().getAttribute(
					UsableController.USER_LOGIN);
			List<Notice> notices = noticeDAO.getLastNews(userLogin.getCompany()
					.getId(), 10);
			int noticesize = notices.size();
			
			
			ModelMap modelMap = new ModelMap();
			modelMap.addAttribute("notices", notices);
			modelMap.addAttribute("noticesize", noticesize);
			return new ModelAndView("member", modelMap);
		}
		return new ModelAndView("redirect:/");
	}

	public ModelAndView logout(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (super.chkSession(request)) {
			request.getSession().invalidate();
			return new ModelAndView("redirect:/");
		}
		return new ModelAndView("redirect:/");
	}

	public ModelAndView viewNotice(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Notice notice = noticeDAO.findById(Long.parseLong(request
				.getParameter("id")));

		ModelMap modelMap = new ModelMap();
		modelMap.addAttribute("notice", notice);
		modelMap.addAttribute("loginForm", new LoginForm());
		return new ModelAndView("viewNotice", modelMap);
	}

	public ModelAndView viewCompany(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Long schoolId = (Long) request.getSession().getAttribute("compId");
		Company company = companyDAO.findById(schoolId);
		ModelMap modelMap = new ModelMap();
		modelMap.addAttribute("company", company);
		modelMap.addAttribute("loginForm", new LoginForm());
		return new ModelAndView("viewCompany", modelMap);
	}

	public ModelAndView aboutus(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView("aboutus", "loginForm", new LoginForm());
	}

}
