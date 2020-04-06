package com.distar.dtwr.company.web;

import java.sql.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;

import com.distar.dtwr.company.domain.User;
import com.distar.dtwr.company.repository.PositionDAO;
import com.distar.dtwr.company.repository.UserDAO;
import com.distar.dtwr.company.service.SearchEmpForm;
import com.distar.dtwr.system.web.UsableController;

import distar.project.service.PagedNavHolder;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

public class UserController extends UsableController {
	public static final String PREFIXS = "prefixs";
	public static final String ORIGINS = "origins";
	public static final String NATIONS = "nations";
	public static final String RELIGIONS = "religions";
	public static final String PROVINCES = "provinces";
	private PositionDAO positionDAO;
	private UserDAO userDAO;
	private Cache cache;

	public void setCacheManager(CacheManager cacheMgr) {
		this.cache = cacheMgr.getCache("optionCache");
	}

	public void setPositionDAO(PositionDAO positionDAO) {
		this.positionDAO = positionDAO;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	public ModelAndView defaultRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (super.chkSession(request)) {
			return new ModelAndView("redirect:/member.htm");
		}
		return new ModelAndView("redirect:/");
	}

	public ModelAndView addEmp(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (super.chkSession(request)) {
			User userLogin = (User) request.getSession().getAttribute(
					UsableController.USER_LOGIN);
			
			Date dateNow = new Date(System.currentTimeMillis());

			ModelMap modelMap = new ModelMap();
			setOptionData(modelMap);
			modelMap.addAttribute("positions", positionDAO
					.findEmpPosition(userLogin.getCompany().getId()));

			return new ModelAndView("/human/person/addEmp", modelMap);
		}
		return new ModelAndView("redirect:/");
	}

	public ModelAndView searchEmpEdit(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (super.chkSession(request)) {
			User userLogin = (User) request.getSession().getAttribute(
					UsableController.USER_LOGIN);
			ModelMap modelMap = new ModelMap();
			modelMap.addAttribute("searchEmpForm", new SearchEmpForm());
			modelMap.addAttribute("positions", positionDAO
					.findEmpPosition(userLogin.getCompany().getId()));
			return new ModelAndView("/human/person/searchEmpEdit", modelMap);
		}
		return new ModelAndView("redirect:/");
	}

	public ModelAndView resEmpEdit(HttpServletRequest request,
			HttpServletResponse response, SearchEmpForm searchEmpForm)
			throws Exception {
		if (super.chkSession(request)) {
			if (searchEmpForm.getOnSubmit() == 1)
				request.getSession().setAttribute("searchEmpForm",
						searchEmpForm);
			else {
				if (request.getSession().getAttribute("searchEmpForm") != null)
					searchEmpForm = (SearchEmpForm) request.getSession()
							.getAttribute("searchEmpForm");
			}

			User userLogin = (User) request.getSession().getAttribute(
					UsableController.USER_LOGIN);

			int page = PagedNavHolder.convParameter(request.getParameter("p"));

			ApplicationContext springAppContext = WebApplicationContextUtils
					.getWebApplicationContext(this.getServletContext());
			PagedNavHolder pconfig = (PagedNavHolder) springAppContext
					.getBean(PagedNavHolder.NAME);
			// logger.debug("pagesize: " + pconfig.getPageSize());

			List<User> emps = userDAO.findBySearchEmpForm(searchEmpForm,
					userLogin.getCompany().getId(), pconfig.getPageSize()
							* page, pconfig.getPageSize());

			PagedNavHolder ph = pconfig.createHandler();
			ph.setPage(page);
			ph.setListCount(userDAO.sizeBySearchEmpForm(searchEmpForm,
					userLogin.getCompany().getId()));

			ModelMap modelMap = new ModelMap();
			modelMap.addAttribute("emps", emps);
			modelMap.addAttribute("emps_size", ph.getListCount());
			modelMap.addAttribute("startIndex", ph.getStartIndex());
			modelMap.addAttribute(PagedNavHolder.NAME, ph);
			return new ModelAndView("/human/person/resEmpEdit", modelMap);
		}
		return new ModelAndView("redirect:/");
	}


	
	public ModelAndView viewData(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (super.chkSession(request)) {
			User userLogin = (User) request.getSession().getAttribute(
					UsableController.USER_LOGIN);
			User user = userDAO.findById(userLogin.getId());

			if (user.getPosition().getNameEng().trim().equals("student")) {
				return new ModelAndView("/academic/register/viewMyStu", "user",
						user);
			} else if (user.getPosition().getName().equals("parent")) {

			} else {
				return new ModelAndView("/human/person/viewMyEmp", "user", user);
			}
		}
		return new ModelAndView("redirect:/");
	}

	private void setOptionData(ModelMap modelMap) {

	}
}
