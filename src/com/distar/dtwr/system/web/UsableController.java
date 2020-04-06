package com.distar.dtwr.system.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.distar.dtwr.company.domain.User;

public class UsableController extends MultiActionController {
	public static final String USER_LOGIN = "userLogin";
	protected final Log logger = LogFactory.getLog(getClass());


	public boolean chkSession(HttpServletRequest request) {
		if (request.getSession().getAttribute(USER_LOGIN) != null) {
			User userLogin = (User) request.getSession().getAttribute(
					USER_LOGIN);

			return true;
		}
		return false;
	}
}
