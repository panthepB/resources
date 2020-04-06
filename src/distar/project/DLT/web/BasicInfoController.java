
package distar.project.DLT.web;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.scheme.SchemeSocketFactory;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import com.distar.dtwr.company.domain.User;
import com.mongodb.BasicDBObject;
import com.mongodb.Cursor;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.WriteResult;

import distar.gateway.controller.Util;
import distar.project.DLT.domain.ExcelImport;
import distar.project.DLT.domain.MasterFile;
import distar.project.DLT.domain.ProvinceDLT;
import distar.project.DLT.domain.VehicleRegisterType;
import distar.project.DLT.repository.ExcelImportDAO;
import distar.project.DLT.repository.MasterFileDAO;
import distar.project.DLT.repository.ProvinceDLTDAO;
import distar.project.DLT.repository.VehicleRegisterTypeDAO;
import distar.project.DLT.service.MasterFileForm;
import distar.project.service.ServerIP;
import distar.project.web.UsableController;

public class BasicInfoController extends UsableController {

	private ProvinceDLTDAO provinceDLTDAO;
	private VehicleRegisterTypeDAO vehicleRegisterTypeDAO;
	private MasterFileDAO masterFileDAO;
	private ExcelImportDAO excelImportDAO;
	private ServerIP serverIP;
	
	static int port_no = 27017;
	static String host_url = "10.1.1.2";
//	static String host_url = "localhost";

	public void setMasterFileDAO(MasterFileDAO masterFileDAO) {
		this.masterFileDAO = masterFileDAO;
	}

	public void setServerIP(ServerIP serverIP) {
		this.serverIP = serverIP;
	}

	public void setExcelImportDAO(ExcelImportDAO excelImportDAO) {
		this.excelImportDAO = excelImportDAO;
	}

	public void setVehicleRegisterTypeDAO(VehicleRegisterTypeDAO vehicleRegisterTypeDAO) {
		this.vehicleRegisterTypeDAO = vehicleRegisterTypeDAO;
	}

	public void setProvinceDLTDAO(ProvinceDLTDAO provinceDLTDAO) {
		this.provinceDLTDAO = provinceDLTDAO;
	}

	public ModelAndView defaultRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return new ModelAndView("redirect:/");
	}

	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) throws Exception {

		response.setContentType("application/json");
		// return new ModelAndView("status", "status", status);
		return new ModelAndView("home");
	}

	public ModelAndView addMasterFileForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (super.chkSession(request)) {

			Locale lc = new Locale("en", "EN");
			SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd",lc);
			dateformat.setTimeZone(TimeZone.getTimeZone("UTC"));
			Date installDate = new Date();
			String newDateInstall = null;

			MasterFileForm masterFileForm = new MasterFileForm();
			masterFileForm.setCardReader(1 + "");
			masterFileForm.setDltStatus(1 + "");
			masterFileForm.setMove(0 + "");
			// masterFileForm.setDateInstall(dateformat.format(installDate));

			try {
				newDateInstall = dateformat.format(installDate);
				masterFileForm.setDateInstall(newDateInstall);

			}
			catch (Exception e) {
				// TODO: handle exception
				System.out.println(e.getMessage());
				masterFileForm.setDateInstall(newDateInstall);
			}
			
			Connection connect;
			Statement stmt;
			
			String uri = serverIP.getMysql();
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection(uri);
			stmt = connect.createStatement();
			
			ArrayList<String> customers = new ArrayList();
			ArrayList<String> sales = new ArrayList();
			ArrayList<String> vehicleTypes = new ArrayList();
			
			User userLogin = (User) request.getSession().getAttribute(UsableController.USER_LOGIN);
			String selectCustomers = "SELECT DISTINCT customer_name FROM master_file WHERE user_create = '"+userLogin.getId()+"' ";
			ResultSet rs = stmt.executeQuery(selectCustomers);
			while (rs.next()) {
				customers.add("\""+rs.getString("customer_name").trim()+"\"");
			}
//			System.out.println("customers : "+customers);
			
			String selectVehicleType = "SELECT DISTINCT `vehicle_type` FROM `master_file` ORDER BY vehicle_type ASC ";
			ResultSet rs3= stmt.executeQuery(selectVehicleType);
			while (rs3.next()) {
				vehicleTypes.add("\""+rs3.getString("vehicle_type").trim()+"\"");
			}

			List<ProvinceDLT> listProvince = provinceDLTDAO.orderByProvinceName();
			List<VehicleRegisterType> listVehicle = vehicleRegisterTypeDAO.orderByVehicleRegisterType();

			
			
			ModelMap modelMap = new ModelMap();
			modelMap.addAttribute("masterFileForm", masterFileForm);
			modelMap.addAttribute("listProvince", listProvince);
			modelMap.addAttribute("listVehicle", listVehicle);
			modelMap.addAttribute("customers", customers);
			modelMap.addAttribute("vehicleTypes", vehicleTypes);

			return new ModelAndView("/basicInfo/addMasterFileForm", modelMap);
		}
		return new ModelAndView("redirect:/");

	}
	
	public ModelAndView editMasterFileForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (super.chkSession(request)) {

			Locale lc = new Locale("en", "EN");
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd",lc);
//			df.setTimeZone(TimeZone.getTimeZone("UTC"));
			
			String unitId = request.getParameter("unitId").trim();
			System.out.println("unitId : \"" + unitId + "\"");
			MasterFile masterFile = null;
			try {
				masterFile = masterFileDAO.findByUnitId(unitId);
				
				MasterFileForm masterFileForm = new MasterFileForm();
				List<ProvinceDLT> listProvince = provinceDLTDAO.orderByProvinceName();
				List<VehicleRegisterType> listVehicle = vehicleRegisterTypeDAO.orderByVehicleRegisterType();

				String cardReader = "0";
				String dltStatus = "0";
				String moves = "0";
				if (masterFile.getCardReader() > 0) {
					cardReader = "1";
				}
				if (masterFile.getDltStatus() > 0) {
					dltStatus = "1";
				}
				if (masterFile.getMoveStatus() > 0) {
					moves = "1";
				}
//				System.out.println("masterFile  : " + masterFile.getCustomerName());
				String installD = null ;
//				System.out.println("cardReader : " + cardReader + " dltStatus : " + dltStatus +" moves : "+moves);

				try {
					installD = df.format(masterFile.getInstallDate());
				}catch (Exception e) {
					// TODO: handle exception
				}
				

				masterFileForm.setImei(masterFile.getImei());
				masterFileForm.setVehicleId(masterFile.getVehicleId());
				masterFileForm.setTracker_sim_number(masterFile.getTrackerSimNumber());
				masterFileForm.setVehicleType(masterFile.getVehicleType());
				masterFileForm.setCustomerTel(masterFile.getCustomerTel());
				masterFileForm.setVehicleChassisNo(masterFile.getVehicleChassisNo());
				masterFileForm.setVehicleRegisterType(masterFile.getVehicleRegisterType().getVehicleRegisterType());
				masterFileForm.setGpsModel(masterFile.getGpsModel());
				masterFileForm.setProvinceCode(masterFile.getProvinceCode().getProvinceCode());
				masterFileForm.setCardReader(cardReader);
				masterFileForm.setCustomerName(masterFile.getCustomerName());
				masterFileForm.setSaleName(masterFile.getSaleName());
				masterFileForm.setDltStatus(dltStatus);
				masterFileForm.setRemark(masterFile.getRemark());
				masterFileForm.setRemark2(masterFile.getRemark2());
				masterFileForm.setMove(moves);
				masterFileForm.setCustomerTel(masterFile.getCustomerTel());
				masterFileForm.setInstallDate(installD);
				masterFileForm.setUnitId(unitId);

//				System.out.println("getInstallDate  : " + installD);

				SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd",lc);
				dateformat.setTimeZone(TimeZone.getTimeZone("UTC"));
				Date installDate = null;
				String newDateInstall = null;

				try {
					masterFileForm.setDateInstall(dateformat.format(installDate));
				}
				catch (Exception e) {
					masterFileForm.setDateInstall(null);
				}

				ModelMap modelMap = new ModelMap();
				modelMap.addAttribute("masterFileForm", masterFileForm);
				modelMap.addAttribute("listProvince", listProvince);
				modelMap.addAttribute("listVehicle", listVehicle);
//				System.out.println("Try editMasterFileForm");

				return new ModelAndView("/basicInfo/addMasterFileForm", modelMap);
			}
			catch (Exception e) {
				// TODO: handle exception
				System.out.println(e.getMessage());
				// masterFile = masterFileDAO.findByUnitId(unitId);

				MasterFileForm masterFileForm = new MasterFileForm();
				List<ProvinceDLT> listProvince = provinceDLTDAO.orderByProvinceName();
				List<VehicleRegisterType> listVehicle = vehicleRegisterTypeDAO.orderByVehicleRegisterType();

				ModelMap modelMap = new ModelMap();
				modelMap.addAttribute("masterFileForm", masterFileForm);
				modelMap.addAttribute("listProvince", listProvince);
				modelMap.addAttribute("listVehicle", listVehicle);

//				System.out.println("Catch editMasterFileForm");
				return new ModelAndView("/basicInfo/addMasterFileForm", modelMap);
				
			}

		}
		return new ModelAndView("redirect:/");

	}
	
	public ModelAndView addMasterFile(HttpServletRequest request, HttpServletResponse response, MasterFileForm masterFileForm) throws Exception {
		if (super.chkSession(request)) {

			User userLogin = (User) request.getSession().getAttribute(UsableController.USER_LOGIN);

			Locale lc = new Locale("en", "EN");
			String status = "";
			String imei = masterFileForm.getImei();
			imei = imei.replace(" ", "");

			String gpsModel = masterFileForm.getGpsModel();
			String vehicleId = masterFileForm.getVehicleId(); // License plate
			String vehicleType = masterFileForm.getVehicleType().toUpperCase(); // Vehicle
																				// brand
			String vehicleChassisNo = masterFileForm.getVehicleChassisNo().toUpperCase();
			Long vehicleRegisterType = masterFileForm.getVehicleRegisterType();
			Long provinceCode = masterFileForm.getProvinceCode();

			int cardReader = 0;
//			System.out.println("CardReader : " + masterFileForm.getCardReader());
			// System.out.println("GetUnitId : " + masterFileForm.getUnitId());
			try {
				if (masterFileForm.getCardReader().equals("on")) {
					cardReader = 1;
				}
			}
			catch (Exception e) {
				cardReader = 0;
			}

//			System.out.println("DltStatus : " + masterFileForm.getDltStatus());
			int dltStatus = 0;
			try {
				if (masterFileForm.getDltStatus().equals("on")) {
					dltStatus = 1;
				}
			}
			catch (Exception e) {
				dltStatus = 0;
			}
			
			int move = 0;
			try {
				if (masterFileForm.getMove().equals("on")) {
					move = 0;
				}
			}
			catch (Exception e) {
				dltStatus = 1;
			}
			
			String moveStatus = "";
			try {
				if (masterFileForm.getMove().equals("on")) {
					moveStatus = "D";
				}
			}
			catch (Exception e) {
				moveStatus = "M";
			}

			String initIMEI = "";
			String initVehicleId = "";
			String customerName = masterFileForm.getCustomerName();
			String saleName = masterFileForm.getSaleName();

			if (imei.trim().length() < 20) {
				int i = 20 - imei.trim().length();
				for (; i > 0; i--) {
					initIMEI += "0";
				}
			}

			if (vehicleId.trim().length() < 7) {
				int i = 7 - vehicleId.trim().length();
				for (; i > 0; i--) {
					initVehicleId += "0";
				}
			}

			String unitId = null;
			try {
				unitId = masterFileForm.getUnitId();
			}
			catch (Exception e) {
				// TODO: handle exception
			}
			if (unitId.length() < 3) {
				unitId = gpsModel + initIMEI + imei.trim();
				unitId = unitId.trim();
				unitId = unitId.replace(" ", "");
//				System.out.println("GetUnitId : " + masterFileForm.getUnitId());
			}
			
			String DUnit = gpsModel+"00000000";
			String dummyCode = null;
			
			Connection connectDB;
			Statement stmtUD;
			String uriDB = serverIP.getMysql();
			Class.forName("com.mysql.jdbc.Driver");
			connectDB = DriverManager.getConnection(uriDB);
			stmtUD = connectDB.createStatement();
			
			

			if(gpsModel.equals("0430001")){
				dummyCode = "MBD01";
			}else if(gpsModel.equals("0430002")){
				dummyCode = "MBD02";
			}else if(gpsModel.equals("0430003")){
				dummyCode = "TMT01";
			}else if(gpsModel.equals("0430005")){
				dummyCode = "MLY01";
			}else if(gpsModel.equals("0430006")){
				dummyCode = "MSM02";
			}else if(gpsModel.equals("0430008")){
				dummyCode = "MSM01";
			}else if(gpsModel.equals("0430017")){
				dummyCode = "MTM01";
			}else if(gpsModel.equals("0430012")){
				dummyCode = "MTM02";
			}else if(gpsModel.equals("0430013")){
				dummyCode = "MTG01";
			}else if(gpsModel.equals("0430014")){
				dummyCode = "MTG02";
			}else if(gpsModel.equals("0430015")){
				dummyCode = "TFF01";
			}else if(gpsModel.equals("0430016")){
				dummyCode = "TCC02";
			}else if(gpsModel.equals("0430018")){
				dummyCode = "TFF02";
			}
			
			String selectUnitDummy = "SELECT unit_dummy, MAX(code_gen) as code FROM `master_file` WHERE unit_dummy = '"+dummyCode+"'";
			ResultSet rsUD = stmtUD.executeQuery(selectUnitDummy);
			String sCode = "";
			while (rsUD.next()) {
				sCode = rsUD.getString("code");
			}
			try {
				String newCode = "";
				if(sCode != null) {
					System.out.println("Code : "+ sCode );
					System.out.println("Code Length : "+ sCode.length() );
					sCode = (Integer.parseInt(sCode)+1)+"";
					for(int i = 6; sCode.length() < i; i--) {
						newCode += "0";
					}
					sCode = newCode+sCode;
					System.out.println("for : "+ sCode );
				}else {
					sCode = "000001";
					System.out.println("Code : "+ sCode );
				}
			}catch (Exception e) {
				// TODO: handle exception
				System.out.println("Code : "+ sCode );
			}
			
			String dummyUnitId = DUnit+dummyCode+moveStatus+sCode;
			System.out.println("DUnit : "+DUnit+dummyCode+moveStatus+sCode);


				SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd", lc);
//				System.out.println("Install Date : "+masterFileForm.getInstallDate());
				Date installDate = null;
				try {
					installDate = dateformat.parse(masterFileForm.getInstallDate());
				}catch (Exception e) {
					// TODO: handle exception
				}

				Connection connect;
				Statement stmt;

				String uri = serverIP.getMysql();
				Class.forName("com.mysql.jdbc.Driver");
				connect = DriverManager.getConnection(uri);
				stmt = connect.createStatement();
				
				// Add new master file

				MasterFile masterFile = masterFileDAO.findByImeiAndChass(imei,vehicleChassisNo);
				if (masterFile == null) {
					
					MasterFile imei_ = masterFileDAO.findByIMEI(imei);
					if(imei_ != null) {
						System.out.println("imei_ is ready");
			
						ArrayList<String> customers = new ArrayList();
						ArrayList<String> sales = new ArrayList();
						ArrayList<String> vehicleTypes = new ArrayList();
						
						List<ProvinceDLT> listProvince = provinceDLTDAO.orderByProvinceName();
						List<VehicleRegisterType> listVehicle = vehicleRegisterTypeDAO.orderByVehicleRegisterType();
						
						
						
//						System.out.println("getCardReader : " + masterFileForm.getCardReader());
//						System.out.println("getDltStatus : " + masterFileForm.getDltStatus());
//						System.out.println("getMove : " + masterFileForm.getMove());
						
						String CardStatus = "0";
						String DLTStatus = "0";
						String MoveStatus = "0";
						
						if(masterFileForm.getCardReader().equals("on")) {
							CardStatus = "1";
						}
						if(masterFileForm.getDltStatus().equals("on")) {
							DLTStatus = "1";
						}
						if(masterFileForm.getMove().equals("on")) {
							MoveStatus = "0";
						}

						masterFileForm.setCardReader(CardStatus);
						masterFileForm.setDltStatus(DLTStatus);
						masterFileForm.setMove(MoveStatus);

						ModelMap modelMap = new ModelMap();
						modelMap.addAttribute("masterFileForm", masterFileForm);
						modelMap.addAttribute("listProvince", listProvince);
						modelMap.addAttribute("listVehicle", listVehicle);
						modelMap.addAttribute("customers", customers);
						modelMap.addAttribute("vehicleTypes", vehicleTypes);
						modelMap.addAttribute("messageCode", "101");

						return new ModelAndView("/basicInfo/addMasterFileForm", modelMap);
					}
					MasterFile chessis = masterFileDAO.findByChass(vehicleChassisNo);
					if(chessis != null) {
						
						ArrayList<String> customers = new ArrayList();
						ArrayList<String> sales = new ArrayList();
						ArrayList<String> vehicleTypes = new ArrayList();
						
						List<ProvinceDLT> listProvince = provinceDLTDAO.orderByProvinceName();
						List<VehicleRegisterType> listVehicle = vehicleRegisterTypeDAO.orderByVehicleRegisterType();
						
						String CardStatus = "0";
						String DLTStatus = "0";
						String MoveStatus = "0";
						
						if(masterFileForm.getCardReader().equals("on")) {
							CardStatus = "1";
						}
						if(masterFileForm.getDltStatus().equals("on")) {
							DLTStatus = "1";
						}
						if(masterFileForm.getMove().equals("on")) {
							MoveStatus = "0";
						}
						
						masterFileForm.setCardReader(CardStatus);
						masterFileForm.setDltStatus(DLTStatus);
						masterFileForm.setMove(MoveStatus);

						ModelMap modelMap = new ModelMap();
						modelMap.addAttribute("masterFileForm", masterFileForm);
						modelMap.addAttribute("listProvince", listProvince);
						modelMap.addAttribute("listVehicle", listVehicle);
						modelMap.addAttribute("customers", customers);
						modelMap.addAttribute("vehicleTypes", vehicleTypes);
						modelMap.addAttribute("messageCode", "102");

						return new ModelAndView("/basicInfo/addMasterFileForm", modelMap);
					}

					MasterFile mf = new MasterFile();

					mf.setVehicleRegisterType(vehicleRegisterTypeDAO.findById(vehicleRegisterType));
					mf.setProvinceCode(provinceDLTDAO.findById(provinceCode));
					mf.setGpsModel(gpsModel);
					mf.setVehicleId(initVehicleId + vehicleId);
					mf.setVehicleType(vehicleType);
					mf.setImei(imei);
					mf.setCodeGen(Integer.parseInt(sCode));
					mf.setUnitDummy(dummyCode);
					mf.setUnitId(dummyUnitId);
					mf.setMoveStatus(move);
					mf.setVehicleChassisNo(vehicleChassisNo);
					mf.setCardReader(cardReader);
					mf.setCustomerName(customerName);
					mf.setCustomerTel(masterFileForm.getCustomerTel());
					mf.setTrackerSimNumber(masterFileForm.getTracker_sim_number());
					mf.setSaleName(saleName);
					mf.setDateCreated(new Date());
					mf.setUserCreate(userLogin);
					mf.setInstallDate(installDate);
					mf.setDltStatus(dltStatus);
					mf.setDeleteStatus(1);
					mf.setStatus(0);
					mf.setRemark(masterFileForm.getRemark());
					mf.setRemark2(masterFileForm.getRemark2());
					masterFileDAO.persist(mf);

					String query = "UPDATE master_file SET province_code = '" + provinceCode + "',vehicle_register_type = '" + vehicleRegisterType + "',unit_dummy = '" + dummyCode + "',code_gen = '" + sCode +  "' WHERE unit_id = '" + dummyUnitId
							+ "'";

					System.out.println("query : "+query);
					stmt.executeUpdate(query);

					System.out.println("Add New MasterFile " + provinceDLTDAO.findById(provinceCode).getProvinceName() + " : " + provinceCode);

				}

				// Edit master file
				else {

					// masterFile.setImei(imei);
					masterFile.setVehicleRegisterType(vehicleRegisterTypeDAO.findById(vehicleRegisterType));
					masterFile.setProvinceCode(provinceDLTDAO.findById(provinceCode));
					masterFile.setGpsModel(gpsModel);
					masterFile.setVehicleType(vehicleType);
					masterFile.setVehicleId(initVehicleId + vehicleId);
					masterFile.setUnitId(dummyUnitId);
					masterFile.setVehicleChassisNo(vehicleChassisNo);
					masterFile.setCardReader(cardReader);
					masterFile.setCustomerName(customerName);
					masterFile.setSaleName(saleName);
					masterFile.setCustomerTel(masterFileForm.getCustomerTel());
					masterFile.setTrackerSimNumber(masterFileForm.getTracker_sim_number());
					masterFile.setMoveStatus(move);
					masterFile.setDateUpdated(new Date());
					masterFile.setUserUpdate(userLogin);
					masterFile.setInstallDate(installDate);
					masterFile.setDltStatus(dltStatus);
					masterFile.setDeleteStatus(1);
					masterFile.setStatus(0);
					masterFile.setRemark(masterFileForm.getRemark());
					masterFile.setRemark2(masterFileForm.getRemark2());
					masterFileDAO.merge(masterFile);

					String query = "UPDATE master_file SET province_code = '" + provinceCode + "',vehicle_register_type = '" + vehicleRegisterType + "',unit_dummy = '" + dummyCode + "',code_gen = '" + sCode +  "' WHERE unit_id = '" + dummyUnitId
							+ "'";

					 System.out.println("query : "+query);
					stmt.executeUpdate(query);

					System.out.println("Update MasterFile : " + provinceDLTDAO.findById(provinceCode).getProvinceName() + " : " + provinceCode);
				}


//			}
//				System.out.println(res.getStatusLine().getStatusCode());
			// return new ModelAndView("redirect:/member.htm", "status", 200);

			return new ModelAndView("redirect:/basicInfo/masterfileList.htm");
		}
		return new ModelAndView("redirect:/");

	}

	public ModelAndView editMasterFile(HttpServletRequest request, HttpServletResponse response, MasterFileForm masterFileForm) throws Exception {
		if (super.chkSession(request)) {

			User userLogin = (User) request.getSession().getAttribute(UsableController.USER_LOGIN);

			Locale lc = new Locale("en", "EN");
			String status = "";
			String imei = masterFileForm.getImei();
			imei = imei.replace(" ", "");

			String gpsModel = masterFileForm.getGpsModel();
			String vehicleId = masterFileForm.getVehicleId(); // License plate
			String vehicleType = masterFileForm.getVehicleType().toUpperCase(); // Vehicle
																				// brand
			String vehicleChassisNo = masterFileForm.getVehicleChassisNo().toUpperCase();
			Long vehicleRegisterType = masterFileForm.getVehicleRegisterType();
			Long provinceCode = masterFileForm.getProvinceCode();

			int cardReader = 0;
//			System.out.println("CardReader : " + masterFileForm.getCardReader());
			// System.out.println("GetUnitId : " + masterFileForm.getUnitId());
			try {
				if (masterFileForm.getCardReader().equals("on")) {
					cardReader = 1;
				}
			}
			catch (Exception e) {
				cardReader = 0;
			}

//			System.out.println("DltStatus : " + masterFileForm.getDltStatus());
			int dltStatus = 0;
			try {
				if (masterFileForm.getDltStatus().equals("on")) {
					dltStatus = 1;
				}
			}
			catch (Exception e) {
				dltStatus = 0;
			}
			
			int move = 0;
			try {
				if (masterFileForm.getMove().equals("on")) {
					move = 0;
				}
			}
			catch (Exception e) {
				dltStatus = 1;
			}
			
			String moveStatus = "";
			try {
				if (masterFileForm.getMove().equals("on")) {
					moveStatus = "D";
				}
			}
			catch (Exception e) {
				moveStatus = "M";
			}

			String initIMEI = "";
			String initVehicleId = "";
			String customerName = masterFileForm.getCustomerName();
			String saleName = masterFileForm.getSaleName();

			if (imei.trim().length() < 20) {
				int i = 20 - imei.trim().length();
				for (; i > 0; i--) {
					initIMEI += "0";
				}
			}

			if (vehicleId.trim().length() < 7) {
				int i = 7 - vehicleId.trim().length();
				for (; i > 0; i--) {
					initVehicleId += "0";
				}
			}

			String unitId = null;
			try {
				unitId = masterFileForm.getUnitId();
//				System.out.println("GetUnitId : " + unitId);
			}
			catch (Exception e) {
				// TODO: handle exception
			}

			// System.out.println("initVehicleId : " + initVehicleId+vehicleId);
			System.out.println("unitId : " + unitId);
	
				SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd", lc);
//				System.out.println("Install Date : "+masterFileForm.getInstallDate());
				Date installDate = null;
				try {
					installDate = dateformat.parse(masterFileForm.getInstallDate());
				}catch (Exception e) {
					// TODO: handle exception
				}

				Connection connect;
				Statement stmt;

				String uri = serverIP.getMysql();
				Class.forName("com.mysql.jdbc.Driver");
				connect = DriverManager.getConnection(uri);
				stmt = connect.createStatement();
								
				
				// Add new master file
				System.out.println("Add new master file");

				MasterFile masterFile = masterFileDAO.findByImeiAndChass(imei,vehicleChassisNo);
				if (masterFile == null) {
					
					MasterFile mf = new MasterFile();

					mf.setVehicleRegisterType(vehicleRegisterTypeDAO.findById(vehicleRegisterType));
					mf.setProvinceCode(provinceDLTDAO.findById(provinceCode));
					mf.setGpsModel(gpsModel);
					mf.setVehicleId(initVehicleId + vehicleId);
					mf.setVehicleType(vehicleType);
					mf.setSaleName(saleName);
					mf.setImei(imei);
					mf.setUnitId(unitId);
					mf.setMoveStatus(move);
					mf.setVehicleChassisNo(vehicleChassisNo);
					mf.setCardReader(cardReader);
					mf.setCustomerName(customerName);
					mf.setCustomerTel(masterFileForm.getCustomerTel());
					mf.setTrackerSimNumber(masterFileForm.getTracker_sim_number());
					mf.setDateCreated(new Date());
					mf.setUserCreate(userLogin);
					mf.setInstallDate(installDate);
					mf.setDltStatus(dltStatus);
					mf.setDeleteStatus(1);
					mf.setStatus(0);
					mf.setRemark(masterFileForm.getRemark());
					mf.setRemark2(masterFileForm.getRemark2());
					masterFileDAO.persist(mf);

					String query = "UPDATE master_file SET province_code = '" + provinceCode + "',vehicle_register_type = '" + vehicleRegisterType + "' WHERE unit_id = '" + unitId
							+ "'";

					System.out.println("query : "+query);
					stmt.executeUpdate(query);

					System.out.println("Add New MasterFile " + provinceDLTDAO.findById(provinceCode).getProvinceName() + " : " + provinceCode);

				}

				// Edit master file
				else {

					// masterFile.setImei(imei);
					masterFile.setVehicleRegisterType(vehicleRegisterTypeDAO.findById(vehicleRegisterType));
					masterFile.setProvinceCode(provinceDLTDAO.findById(provinceCode));
					masterFile.setGpsModel(gpsModel);
					masterFile.setVehicleType(vehicleType);
					masterFile.setVehicleId(initVehicleId + vehicleId);
					masterFile.setUnitId(unitId);
					masterFile.setSaleName(saleName);
					masterFile.setVehicleChassisNo(vehicleChassisNo);
					masterFile.setCardReader(cardReader);
					masterFile.setCustomerName(customerName);
					masterFile.setCustomerTel(masterFileForm.getCustomerTel());
					masterFile.setTrackerSimNumber(masterFileForm.getTracker_sim_number());
					masterFile.setMoveStatus(move);
					masterFile.setDateUpdated(new Date());
					masterFile.setUserUpdate(userLogin);
					masterFile.setInstallDate(installDate);
					masterFile.setDltStatus(dltStatus);
					masterFile.setDeleteStatus(1);
					masterFile.setStatus(0);
					masterFile.setRemark(masterFileForm.getRemark());
					masterFile.setRemark2(masterFileForm.getRemark2());
					masterFileDAO.merge(masterFile);

					String query = "UPDATE master_file SET province_code = '" + provinceCode + "',vehicle_register_type = '" + vehicleRegisterType + "' WHERE unit_id = '" + unitId
							+ "'";
					 System.out.println("query : "+query);
					stmt.executeUpdate(query);

					System.out.println("Update MasterFile : " + provinceDLTDAO.findById(provinceCode).getProvinceName() + " : " + provinceCode);
					
				}

				
				return new ModelAndView("redirect:/basicInfo/masterfileList.htm");
		}
		return new ModelAndView("redirect:/");

	}

	public ModelAndView masterfileList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (super.chkSession(request)) {
			
			User userLogin = (User) request.getSession().getAttribute(UsableController.USER_LOGIN);
//			System.out.println("user : " + userLogin.getId());
			// List<MasterFile> listMasterFile =
			// masterFileDAO.findWithOutBMTA();
			List<MasterFile> listMasterFile = masterFileDAO.findByStatusAndUser(0, userLogin);
//			System.out.println("listMasterFile : " + listMasterFile.size());

			ModelMap modelMap = new ModelMap();
			modelMap.addAttribute("listMasterFile", listMasterFile);

			return new ModelAndView("basicInfo/masterfileList", modelMap);
		}
		return new ModelAndView("redirect:/");

	}
	
	public ModelAndView listForApprove(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (super.chkSession(request)) {
			
			User userLogin = (User) request.getSession().getAttribute(UsableController.USER_LOGIN);
//			System.out.println("user : " + userLogin.getId());
			// List<MasterFile> listMasterFile =
			// masterFileDAO.findWithOutBMTA();
			List<MasterFile> listMasterFile = masterFileDAO.findByStatus(0);
//			System.out.println("listMasterFile : " + listMasterFile.size());

			ModelMap modelMap = new ModelMap();
			modelMap.addAttribute("listMasterFile", listMasterFile);

			return new ModelAndView("basicInfo/listForApprove", modelMap);
		}
		return new ModelAndView("redirect:/");

	}
	
	public ModelAndView rmvMasterFileForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (super.chkSession(request)) {
			MasterFileForm masterFileForm = new MasterFileForm();

			masterFileForm.setStatus(1);

			ModelMap modelMap = new ModelMap();
			modelMap.addAttribute("masterFileForm", masterFileForm);

			return new ModelAndView("/masterfile/rmvMasterFileForm", modelMap);
		}
		return new ModelAndView("redirect:/");

	}

	public ModelAndView rmvMasterFile(HttpServletRequest request, HttpServletResponse response, MasterFileForm masterFileForm) throws Exception {
		if (super.chkSession(request)) {
			String unitId = "";
			String status = "";
			String imei = "";
			String gpsModel = "";
			String initIMEI = "";
			int delStatus = 0;
			int rvmStatus = 0;
			rvmStatus = masterFileForm.getStatus();

			System.out.println(" rvmStatus : " + rvmStatus);

			try {
				if (request.getParameter("unitId").length() > 5 && rvmStatus == 0) {
					System.out.println("request.getParameter : " + request.getParameter("unitId"));
					unitId = request.getParameter("unitId");
					delStatus = 0;

				}
				else if (masterFileForm.getUnitId().length() > 5 && rvmStatus == 1) {
					System.out.println("masterFileForm.getUnitId() : " + masterFileForm.getUnitId());
					unitId = masterFileForm.getUnitId();
					delStatus = 1;
				}

				System.out.println("delStatus : " + delStatus + " rvmStatus : " + rvmStatus);
			}
			catch (Exception e) {
				// TODO: handle exception
			}

			status = "";
			imei = masterFileForm.getImei();
			gpsModel = masterFileForm.getGpsModel();
			initIMEI = "";

			byte[] encodedBytes = Base64.encodeBase64((byte[]) serverIP.getUserPassMaster().getBytes());
			String url = String.valueOf(serverIP.getUrlMesterFile()) + "/masterfile/rmvByUnit/" + unitId;
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpDelete delete = new HttpDelete(url);

			final X509Certificate[] _AcceptedIssuers = new X509Certificate[]{};
			SSLContext ctx = SSLContext.getInstance("TLS");
			X509TrustManager tm = new X509TrustManager() {
				@Override
				public X509Certificate[] getAcceptedIssuers() {
					return _AcceptedIssuers;
				}

				@Override
				public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				}

				@Override
				public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				}
			};
			ctx.init(null, new TrustManager[]{tm}, new SecureRandom());
			SSLSocketFactory ssf = new SSLSocketFactory(ctx, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			ClientConnectionManager ccm = httpClient.getConnectionManager();
			SchemeRegistry sr = ccm.getSchemeRegistry();
			sr.register(new Scheme("https", 443, (SchemeSocketFactory) ssf));

			delete.setHeader("Authorization", "basic " + new String(encodedBytes));
			delete.addHeader("Content-Type", "application/json");
			HttpResponse res = httpClient.execute((HttpUriRequest) delete);
			
			System.out.println("unitId : " + unitId);
			System.out.println("StatusCode : " + res.getStatusLine().getStatusCode());
			// System.out.println("gpsModel : " + gpsModel);
			try {
				if (res.getStatusLine().getStatusCode() == 200) {
					MasterFile masterFile = masterFileDAO.findByUnitId(unitId);

					// masterFileDAO.remove(masterFile);

					Connection connect2;
					Statement stmt2;

					String uri2 = serverIP.getMysql();
					Class.forName("com.mysql.jdbc.Driver");
					connect2 = DriverManager.getConnection(uri2);
					stmt2 = connect2.createStatement();

					if (delStatus == 1) {
						
						String remove = "UPDATE  `distar_dlt`.`master_file` SET `delete_status` = 0,`dlt_status` = 0 WHERE `unit_id` = '" + unitId + "'";
						stmt2.executeUpdate(remove);
					}

					System.out.println(unitId);

					if (unitId.startsWith("0430003")) {
						Connection connect;
						Statement stmt;

						String uri = serverIP.getMysql();
						Class.forName("com.mysql.jdbc.Driver");
						connect = DriverManager.getConnection(uri);
						stmt = connect.createStatement();

						String sql = "UPDATE [dbo].[GPS_Last]  SET [unit_id] = (NULL) WHERE unit_id = '" + masterFile.getUnitId() + "'";
						stmt.executeUpdate(sql);
					}
					// else if (unitId.startsWith("0430005")) {
					// LTYUnitId ltyUnitId =
					// ltyUnitIdDAO.findByUnitId(masterFile.getUnitId());
					// ltyUnitIdDAO.remove(ltyUnitId);
					// }

				}

			}
			catch (Exception e) {
				// TODO: handle exception
				if (res.getStatusLine().getStatusCode() == 200) {
					// System.out.println("else : " + e.getMessage());
				}
			}

			// return new ModelAndView("redirect:/member.htm", "status", "ok");

			return new ModelAndView("redirect:/member.htm", "status", res.getStatusLine().getStatusCode());
		}
		return new ModelAndView("redirect:/");
	}

	public ModelAndView rmvMasterFileFromList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (super.chkSession(request)) {
			String unitId = "";
			String status = "";
			String imei = "";
			String gpsModel = "";
			String initIMEI = "";
			
			Connection connect2;
			Statement stmt2;

			String uri2 = serverIP.getMysql();
			Class.forName("com.mysql.jdbc.Driver");
			connect2 = DriverManager.getConnection(uri2);
			stmt2 = connect2.createStatement();

			status = "";
			List<ExcelImport> excel = excelImportDAO.findAll();
			for (ExcelImport list : excel) {
				unitId = list.getUnit_id();

				byte[] encodedBytes = Base64.encodeBase64((byte[]) serverIP.getUserPassMaster().getBytes());
				String url = String.valueOf(serverIP.getUrlMesterFile()) + "/masterfile/rmvByUnit/" + unitId;
				DefaultHttpClient httpClient = new DefaultHttpClient();
				HttpDelete delete = new HttpDelete(url);

				final X509Certificate[] _AcceptedIssuers = new X509Certificate[]{};
				SSLContext ctx = SSLContext.getInstance("TLS");
				X509TrustManager tm = new X509TrustManager() {
					@Override
					public X509Certificate[] getAcceptedIssuers() {
						return _AcceptedIssuers;
					}

					@Override
					public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
					}

					@Override
					public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
					}
				};
				ctx.init(null, new TrustManager[]{tm}, new SecureRandom());
				SSLSocketFactory ssf = new SSLSocketFactory(ctx, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
				ClientConnectionManager ccm = httpClient.getConnectionManager();
				SchemeRegistry sr = ccm.getSchemeRegistry();
				sr.register(new Scheme("https", 443, (SchemeSocketFactory) ssf));

				delete.setHeader("Authorization", "basic " + new String(encodedBytes));
				delete.addHeader("Content-Type", "application/json");
				HttpResponse res = httpClient.execute((HttpUriRequest) delete);
				System.out.println("unitId : " + unitId);
				System.out.println("StatusCode : " + res.getStatusLine().getStatusCode());
				
				try {
					MongoClient mongo = new MongoClient(new ServerAddress("localhost", 27017));
					DB db = mongo.getDB("distar_gateway");
					DBCollection collection = db.getCollection("dlt_masterfile");

					BasicDBObject query = new BasicDBObject();
					query.append("unit_id", unitId);

					collection.remove(query);
					
					String remove = "DELETE FROM `distar_dlt`.`master_file` WHERE `unit_id` = '" + unitId + "'";
					stmt2.executeUpdate(remove);
					
					System.out.println("unitId removed : " + unitId);
					
				}catch (Exception e) {
					// TODO: handle exception
					System.out.println("Exception : " + e.getMessage());
				}
				
				
//				System.out.println("gpsModel : " + gpsModel);
				try {
					if (res.getStatusLine().getStatusCode() == 200 && gpsModel.equals("0430003")) {
						MasterFile masterFile = masterFileDAO.findByUnitId(unitId);
						System.out.println(unitId);
						masterFileDAO.remove(masterFile);
					}
					else if (res.getStatusLine().getStatusCode() == 200) {
						System.out.println("else : " + unitId);
					}
				}
				catch (Exception e) {
					// TODO: handle exception
					if (res.getStatusLine().getStatusCode() == 200) {
						System.out.println("else : " + unitId);
					}
				}
				System.out.println(res.getStatusLine().getStatusCode());
			}
			return new ModelAndView("redirect:/member.htm", "status", "200");

		}
		return new ModelAndView("redirect:/");
	}

	private String rvmMasterFileN(String unitId) {
		String status = "";

		try {
			MasterFile masterFile = masterFileDAO.findByUnitId(unitId);
			// masterFileDAO.remove(masterFile);
			status = "ok";

		}
		catch (Exception e) {
			// TODO: handle exception

		}

		return status;
	}
	
	
}
