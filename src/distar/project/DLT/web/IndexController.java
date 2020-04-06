package distar.project.DLT.web;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import com.distar.dtwr.company.domain.Notice;
import com.distar.dtwr.company.domain.User;
import com.distar.dtwr.company.repository.NoticeDAO;
import com.distar.dtwr.company.repository.UserDAO;
import com.distar.dtwr.system.domain.Company;
import com.distar.dtwr.system.domain.Task;
import com.distar.dtwr.system.repository.CompanyDAO;
import com.distar.dtwr.system.repository.TaskDAO;
import com.distar.dtwr.system.service.LoginForm;
import com.distar.dtwr.system.service.NaviService;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;

import distar.gateway.controller.Util;
import distar.project.DLT.domain.RealtimeStatus;
import distar.project.DLT.repository.DevAlarmDAO;
import distar.project.DLT.repository.DevStatusDAO;
import distar.project.DLT.repository.DriverLogDAO;
import distar.project.DLT.repository.ProvinceDLTDAO;
import distar.project.DLT.repository.RealtimeStatusDAO;
import distar.project.service.ParamImage;
import distar.project.service.ServerIP;
import distar.project.web.UsableController;

public class IndexController extends UsableController {
	private NoticeDAO noticeDAO;
	private CompanyDAO companyDAO;
	private TaskDAO taskDAO;
	private ParamImage paramImage;
	private UserDAO userDAO;
	private ServerIP serverIP;
	private RealtimeStatusDAO realtimeStatusDAO;
	
	static int port_no = 27017;
	static String host_url = "10.1.1.2";

	public void setRealtimeStatusDAO(RealtimeStatusDAO realtimeStatusDAO) {
		this.realtimeStatusDAO = realtimeStatusDAO;
	}

	public void setServerIP(ServerIP serverIP) {
		this.serverIP = serverIP;
	}

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

	public ModelAndView defaultRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return new ModelAndView("redirect:/");
	}

	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) throws Exception {

		ModelMap modelMap = new ModelMap();
		modelMap.addAttribute("loginForm", new LoginForm());

		return new ModelAndView("index", modelMap);
	}

	public ModelAndView login(HttpServletRequest request, HttpServletResponse response, LoginForm loginForm) throws Exception {

		Long compId = 1l;

		if (compId != null) {
			User userLogin = userDAO.findByUserPass(compId, loginForm.getUsername(), loginForm.getPassword());
			if (userLogin != null) {
				NaviService naviService = new NaviService(request.getContextPath());
				naviService.addTasks(taskDAO.findByPosition(userLogin.getPosition().getId()));

				request.getSession().setAttribute(UsableController.USER_LOGIN, userLogin);
				request.getSession().setAttribute("navi", naviService.getNaviView().getCode());

				return new ModelAndView("redirect:/member.htm");
			}
		}
		return new ModelAndView("redirect:/");
	}
	
	private void updateMongo(RealtimeStatus device, String deviceCode) {
		MongoClient client = new MongoClient(host_url, port_no);
		try {

			DB database = client.getDB("distar_gateway");
			DBCollection collection = database.getCollection("dlt_realtime_status");

			BasicDBObject searchQuery = new BasicDBObject().append("device_code", deviceCode);

			DBCursor cursor = collection.find(searchQuery);

			BasicDBObject newDocument = new BasicDBObject();
			newDocument.put("status", device.getStatus());
			newDocument.put("response", device.getException());
			newDocument.put("json", device.getDataLog());
			newDocument.put("size", device.getSize());
			newDocument.put("timeUpdate", device.getTimeUpdate());
			BasicDBObject updateObj = new BasicDBObject();
			updateObj.put("$set", newDocument);

			collection.update(searchQuery, updateObj);

		} finally {
			client.close();
		}
	}

	public ModelAndView member(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (super.chkSession(request)) {
			String status = null;

			try {
				status = request.getParameter("status");
			}
			catch (Exception ex) {
				status = "0";
			}

			User userLogin = (User) request.getSession().getAttribute(UsableController.USER_LOGIN);
			
//			System.out.println("userLogin : "+ userLogin.getPosition().getId());
//			System.out.println();
//			System.out.println();
			
			if(userLogin.getPosition().getId() == 3) {
				return new ModelAndView("redirect:/basicInfo/masterfileList.htm");
			}
			
			
			List<Notice> notices = noticeDAO.getLastNews(userLogin.getCompany().getId(), 10);
			int noticesize = notices.size();

			RealtimeStatus DTK3GStatus = null;
			
			RealtimeStatus TG = null;
			
			RealtimeStatus MDVRSGWStatus2 = null;
			
			RealtimeStatus MDVRMHDStatus = null;
			
			RealtimeStatus LTYStatus = null;
			
			RealtimeStatus SCCC = null;
			
			try{
//				DTK3GStatus = realtimeStatusDAO.findByDevice(1);
				TG = realtimeStatusDAO.findByDevice(7);
				MDVRSGWStatus2 = realtimeStatusDAO.findByDevice(6);
				MDVRMHDStatus = realtimeStatusDAO.findByDevice(4);
//				LTYStatus = realtimeStatusDAO.findByDevice(5);
//				SCCC = realtimeStatusDAO.findByDevice(9);
				
				
				updateMongo(MDVRSGWStatus2,"MDVR_BD");
				updateMongo(MDVRMHDStatus,"MDVR_MHD");
//				updateMongo(LTYStatus,"BMTA");
				
				
			}catch (Exception e) {
				// TODO: handle exception
				System.out.println("exception : "+e.getMessage());
			}
			String tgInfo = Util.getTGStatus("TG");
			String BMTAInfo = Util.getTGStatus("BMTA");
			
			Integer countBmta = Util.getTotal("BMTA");
			Integer bmtaOnline = Util.getOnline("BMTA");
			Integer countTG = Util.getTotal("TG");
			Integer tgOnline = Util.getOnline("TG");
			Integer allOnlineBmta = Util.getOnlineToday("BMTA");
			Integer allOnlineTG = Util.getOnlineToday("TG");
			Integer mdvrOnline = Util.getOnlineMDVR();
			Integer mdvrTotal = Util.getMDVROnlineToday();
			String TGStatus =  tgInfo.split("&")[0];
			String TGSize =  tgInfo.split("&")[1];
			String TGTimeUpdate =  tgInfo.split("&")[2];
			String BMTAStatus = BMTAInfo.split("&")[0];
			String BMTASize =  BMTAInfo.split("&")[1];
			String BMTATimeUpdate = BMTAInfo.split("&")[2];
			
			Locale lc = new Locale("en", "EN");
			SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", lc);
//			TimeZone tz = TimeZone.getTimeZone("UTC");
//			dateformat.setTimeZone(tz);
			
			try{
				TGTimeUpdate = dateformat.format(dateformat.parse(TGTimeUpdate));
				BMTATimeUpdate = dateformat.format(dateformat.parse(BMTATimeUpdate));
				System.out.println("TGTimeUpdate : "+dateformat.format(dateformat.parse(TGTimeUpdate)));
			}catch (Exception e) {
				// TODO: handle exception
			}
			
			ModelMap modelMap = new ModelMap();
			modelMap.addAttribute("bmtaTotal", countBmta);
			modelMap.addAttribute("bmtaOnline", bmtaOnline);
			modelMap.addAttribute("tgTotal", countTG);
			modelMap.addAttribute("tgOnline", tgOnline);
			modelMap.addAttribute("allOnlineBmta", allOnlineBmta);
			modelMap.addAttribute("allOnlineTG", allOnlineTG);
			modelMap.addAttribute("mdvrOnline", mdvrOnline);
			modelMap.addAttribute("mdvrTotal", mdvrTotal);
			
			
			modelMap.addAttribute("BMTAStatus", BMTAStatus);
			modelMap.addAttribute("BMTASize", BMTASize);
			modelMap.addAttribute("BMTATimeUpdate", BMTATimeUpdate);
			modelMap.addAttribute("DTK3GStatus", DTK3GStatus);
			modelMap.addAttribute("MDVRSGWStatus", TG);
			modelMap.addAttribute("MDVRSGWStatus2", MDVRSGWStatus2);
			modelMap.addAttribute("MDVRMHDStatus", MDVRMHDStatus);
			modelMap.addAttribute("TGStatus", TGStatus);
			modelMap.addAttribute("TGSize", TGSize);
			modelMap.addAttribute("TGTimeUpdate", TGTimeUpdate);
//			modelMap.addAttribute("LTYStatus", LTYStatus);
			modelMap.addAttribute("userLogin", userLogin);
			modelMap.addAttribute("status", status);
			modelMap.addAttribute("notices", notices);
			modelMap.addAttribute("noticesize", noticesize);
			
			return new ModelAndView("member", modelMap);
		}
		return new ModelAndView("redirect:/");
	}

	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (super.chkSession(request)) {
			request.getSession().invalidate();
			return new ModelAndView("redirect:/");
		}
		return new ModelAndView("redirect:/");
	}

	public ModelAndView viewNotice(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Notice notice = noticeDAO.findById(Long.parseLong(request.getParameter("id")));

		ModelMap modelMap = new ModelMap();
		modelMap.addAttribute("notice", notice);
		modelMap.addAttribute("loginForm", new LoginForm());
		return new ModelAndView("viewNotice", modelMap);
	}

	public ModelAndView viewCompany(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long schoolId = (Long) request.getSession().getAttribute("compId");
		Company company = companyDAO.findById(schoolId);
		ModelMap modelMap = new ModelMap();
		modelMap.addAttribute("company", company);
		modelMap.addAttribute("loginForm", new LoginForm());
		return new ModelAndView("viewCompany", modelMap);
	}

	public ModelAndView aboutus(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return new ModelAndView("aboutus", "loginForm", new LoginForm());
	}

}
