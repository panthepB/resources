package com.distar.dtwr.system.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import com.distar.dtwr.company.domain.User;
import com.distar.dtwr.company.repository.UserDAO;
import com.distar.dtwr.system.service.ChangePasswordForm;
import com.distar.dtwr.system.service.ComposeForm;
import com.distar.dtwr.system.service.Result;

import distar.project.DLT.repository.RealtimeStatusDAO;
import distar.project.service.Alert;


public class MemberController extends UsableController {
	private UserDAO userDAO;
	

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

	public ModelAndView compose(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (super.chkSession(request)) {
			User userLogin = (User) request.getSession().getAttribute(
					UsableController.USER_LOGIN);
			ComposeForm composeForm = new ComposeForm(userLogin);

			if (request.getParameter("userId") != null) {
				User toUser = userDAO.findById(Long.parseLong(request
						.getParameter("userId")));
				if (toUser != null) {
					composeForm.setTo(toUser.getUsername());
				}
				if (request.getParameter("typeId") != null) {
					int typeId = Integer.parseInt(request
							.getParameter("typeId"));
					switch (typeId) {
					case 1: {
						break;
					}
					default: {
						composeForm.setSubject("");
						break;
					}
					}
				}
			}

			return new ModelAndView("/member/compose", "composeForm",
					composeForm);
		}
		return new ModelAndView("redirect:/");
	}

	public ModelAndView composeNow(HttpServletRequest request,
			HttpServletResponse response, ComposeForm composeForm)
			throws Exception {
		if (super.chkSession(request)) {
			User userLogin = (User) request.getSession().getAttribute(
					UsableController.USER_LOGIN);

			User user = userDAO.findByUsername(userLogin.getCompany().getId(),
					composeForm.getTo().trim());
			if (user != null) {
				if (composeForm.getSubject().trim().length() > 0) {
					if (composeForm.getUrlBack().length() > 0) {
						return new ModelAndView("redirect:"
								+ composeForm.getUrlBack());
					}
					return new ModelAndView("redirect:/member/inbox.htm");
				}
			} else {
				ModelMap modelMap = new ModelMap();
				composeForm.setUser(userLogin);
				modelMap.addAttribute("composeForm", composeForm);
				modelMap
						.addAttribute("alert", new Alert("ไม่พบผู้้ใช้ปลายทาง"));
				return new ModelAndView("/member/compose", modelMap);
			}
		}
		return new ModelAndView("redirect:/");
	}

	

	public ModelAndView changePassword(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (super.chkSession(request)) {
			return new ModelAndView("/member/changePassword",
					"changePasswordForm", new ChangePasswordForm());
		}
		return new ModelAndView("redirect:/");
	}

	public ModelAndView changePasswordNow(HttpServletRequest request,
			HttpServletResponse response, ChangePasswordForm changePasswordForm)
			throws Exception {
		if (super.chkSession(request)) {
			User userLogin = (User) request.getSession().getAttribute(
					UsableController.USER_LOGIN);
			if (changePasswordForm.getNewPassword().equals(
					changePasswordForm.getNewPasswordAgain())) {
				if (userLogin.getPassword().equals(
						changePasswordForm.getOldPassword())) {
					User user = userDAO.findById(userLogin.getId());
					user.setPassword(changePasswordForm.getNewPassword());
					userDAO.merge(user);
					Result result = new Result(false);
					result.setResultTxt("������ʼ�ҹ���º��������");
					return new ModelAndView("/showResult", "result", result);
				} else {
					Result result = new Result(true);
					result.setResultTxt("���ʼ�ҹ������١��ͧ");
					return new ModelAndView("/showResult", "result", result);
				}
			} else {
				Result result = new Result(true);
				result.setResultTxt("���ʼ�ҹ�������ç�ѹ");
				return new ModelAndView("/showResult", "result", result);
			}
		}
		return new ModelAndView("redirect:/");
	}
}
