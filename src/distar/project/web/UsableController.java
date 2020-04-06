package distar.project.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import distar.project.service.Notification;


public class UsableController extends MultiActionController {
	public static final String USER_LOGIN = "userLogin";
	public static final String USER_NOTIFICATION = "userNotification";
	public static final int NOTIFY_INFO = Notification.TYPE_INFO;
	public static final int NOTIFY_ERROR = Notification.TYPE_ERROR;
	public static final int NOTIFY_WARN = Notification.TYPE_WARN;
	public static final int NOTIFY_SUCCESS = Notification.TYPE_SUCCESS;
	
	protected final Log logger = LogFactory.getLog(getClass());
	protected Notification noti;
	private HttpSession session;
	protected String bindingPath;
	protected String appSuffix;

	public boolean chkSession(HttpServletRequest request) {
		 clearNotification(request.getSession());
		if (request.getSession().getAttribute(USER_LOGIN) != null) {
			
			return true;
		}
		return false;
	}
	public void setNotification(int msgType, String msg) {
		noti = new Notification(msgType, msg);

		session.setAttribute(USER_NOTIFICATION, noti);
	}
	public void clearNotification(HttpSession s) {
		this.session = s;
		if (noti == null) {
			noti = new Notification(0, null);
			// System.out.println("clear Noti");
		}
		session.setAttribute(USER_NOTIFICATION, noti);
	}
}
