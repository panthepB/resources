
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

public class MasterfileController extends UsableController {

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
			SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd", lc);
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
				// System.out.println("newDateInstall :
				// "+dateformat.format(installDate));

//				String[] dateIns = dateformat.format(installDate).split("-");
//				int year = Integer.parseInt(dateIns[0]);
//				if (dateIns[0].startsWith("20")) {
//
//					newDateInstall = year + "-" + dateIns[1] + "-" + dateIns[2];
//
//				}
//				else if (dateIns[0].startsWith("14")) {
//					year += 543;
//					newDateInstall = year + "-" + dateIns[1] + "-" + dateIns[2];
//
//				}
//				else if (dateIns[0].startsWith("25")) {
//					year -= 543;
//					newDateInstall = year + "-" + dateIns[1] + "-" + dateIns[2];
//				}

				masterFileForm.setDateInstall(newDateInstall);

			} catch (Exception e) {
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

			String selectCustomers = "SELECT DISTINCT customer_name FROM master_file";
			ResultSet rs = stmt.executeQuery(selectCustomers);
			while (rs.next()) {
				customers.add("\"" + rs.getString("customer_name").trim() + "\"");
			}
//			System.out.println("customers : "+customers);

			String selectSales = "SELECT DISTINCT `sale_name` FROM `master_file` ";
			ResultSet rs2 = stmt.executeQuery(selectSales);
			while (rs2.next()) {
				sales.add("\"" + rs2.getString("sale_name").trim() + "\"");
			}

			String selectVehicleType = "SELECT DISTINCT `vehicle_type` FROM `master_file` ORDER BY vehicle_type ASC ";
			ResultSet rs3 = stmt.executeQuery(selectVehicleType);
			while (rs3.next()) {
				vehicleTypes.add("\"" + rs3.getString("vehicle_type").trim() + "\"");
			}
			System.out.println("sales : " + sales);

			List<ProvinceDLT> listProvince = provinceDLTDAO.orderByProvinceName();
			List<VehicleRegisterType> listVehicle = vehicleRegisterTypeDAO.orderByVehicleRegisterType();

			ModelMap modelMap = new ModelMap();
			modelMap.addAttribute("masterFileForm", masterFileForm);
			modelMap.addAttribute("listProvince", listProvince);
			modelMap.addAttribute("listVehicle", listVehicle);
			modelMap.addAttribute("customers", customers);
			modelMap.addAttribute("sales", sales);
			modelMap.addAttribute("vehicleTypes", vehicleTypes);

			return new ModelAndView("/masterfile/addMasterFileForm", modelMap);
		}
		return new ModelAndView("redirect:/");

	}

	public ModelAndView editMasterFileForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (super.chkSession(request)) {

			Locale lc = new Locale("en", "EN");
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", lc);
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
				System.out.println("masterFile  : " + masterFile.getCustomerName());
				String installD = null;
				System.out.println("cardReader : " + cardReader + " dltStatus : " + dltStatus + " moves : " + moves);

				try {
//					installD = df.format(masterFile.getInstallDate()).replace("2560", "2018");
//					installD = df.format(masterFile.getInstallDate()).replace("2561", "2020");
					installD = df.format(masterFile.getInstallDate());
				} catch (Exception e) {
					// TODO: handle exception
				}

				masterFileForm.setImei(masterFile.getImei());
				masterFileForm.setVehicleId(masterFile.getVehicleId());
				masterFileForm.setVehicleType(masterFile.getVehicleType());
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
//				masterFileForm.setDateInstall("test");
				masterFileForm.setInstallDate(installD);
				masterFileForm.setUnitId(unitId);
				masterFileForm.setOldImei(masterFile.getImei());
				System.out.println("getInstallDate  : " + installD);
				// System.out.println("getVehicleRegisterType :
				// "+masterFile.getVehicleRegisterType().getVehicleRegisterType());

//				Locale lc = new Locale("en", "EN");
				SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd", lc);
				dateformat.setTimeZone(TimeZone.getTimeZone("UTC"));
				Date installDate = null;
				String newDateInstall = null;

				try {
					masterFileForm.setDateInstall(dateformat.format(installDate));
				} catch (Exception e) {
					masterFileForm.setDateInstall(null);
				}

				ModelMap modelMap = new ModelMap();
				modelMap.addAttribute("masterFileForm", masterFileForm);
				modelMap.addAttribute("listProvince", listProvince);
				modelMap.addAttribute("listVehicle", listVehicle);

				return new ModelAndView("/masterfile/editMasterFileForm", modelMap);
			} catch (Exception e) {
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

				return new ModelAndView("/masterfile/addMasterFileForm", modelMap);
			}

		}
		return new ModelAndView("redirect:/");

	}

	public ModelAndView changeDeviceForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (super.chkSession(request)) {

			Locale lc = new Locale("en", "EN");
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", lc);
//			df.setTimeZone(TimeZone.getTimeZone("UTC"));

			if (request.getParameter("unitId") != null) {
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
					System.out.println("masterFile  : " + masterFile.getCustomerName());
					String installD = null;
					System.out
							.println("cardReader : " + cardReader + " dltStatus : " + dltStatus + " moves : " + moves);

					try {
//					installD = df.format(masterFile.getInstallDate()).replace("2560", "2018");
//					installD = df.format(masterFile.getInstallDate()).replace("2561", "2020");
						installD = df.format(masterFile.getInstallDate());
					} catch (Exception e) {
						// TODO: handle exception
					}

					masterFileForm.setImei(masterFile.getImei());
					masterFileForm.setVehicleId(masterFile.getVehicleId());
					masterFileForm.setVehicleType(masterFile.getVehicleType());
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
//				masterFileForm.setDateInstall("test");
					masterFileForm.setInstallDate(installD);
					masterFileForm.setUnitId(unitId);

					System.out.println("getInstallDate  : " + installD);

					SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd", lc);
					dateformat.setTimeZone(TimeZone.getTimeZone("UTC"));
					Date installDate = null;
					String newDateInstall = null;

					try {
						masterFileForm.setDateInstall(dateformat.format(installDate));
					} catch (Exception e) {
						masterFileForm.setDateInstall(null);
					}

					ModelMap modelMap = new ModelMap();

					modelMap.addAttribute("masterFileForm", masterFileForm);
					modelMap.addAttribute("listProvince", listProvince);
					modelMap.addAttribute("listVehicle", listVehicle);

					return new ModelAndView("/masterfile/changeDevice", modelMap);
				} catch (Exception e) {
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

					return new ModelAndView("/masterfile/changeDevice", modelMap);
				}
			} else {
				MasterFileForm masterFileForm = new MasterFileForm();
				List<ProvinceDLT> listProvince = provinceDLTDAO.orderByProvinceName();
				List<VehicleRegisterType> listVehicle = vehicleRegisterTypeDAO.orderByVehicleRegisterType();

				ModelMap modelMap = new ModelMap();
				modelMap.addAttribute("masterFileForm", masterFileForm);
				modelMap.addAttribute("listProvince", listProvince);
				modelMap.addAttribute("listVehicle", listVehicle);

				return new ModelAndView("/masterfile/changeDevice", modelMap);
			}
		}
		return new ModelAndView("redirect:/");

	}

	public ModelAndView addMasterFile(HttpServletRequest request, HttpServletResponse response,
			MasterFileForm masterFileForm) throws Exception {
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
			} catch (Exception e) {
				cardReader = 0;
			}

//			System.out.println("DltStatus : " + masterFileForm.getDltStatus());
			int dltStatus = 0;
			try {
				if (masterFileForm.getDltStatus().equals("on")) {
					dltStatus = 1;
				}
			} catch (Exception e) {
				dltStatus = 0;
			}

			int move = 0;
			try {
				if (masterFileForm.getMove().equals("on")) {
					move = 0;
				}
			} catch (Exception e) {
				dltStatus = 1;
			}

			String moveStatus = "";
			try {
				if (masterFileForm.getMove().equals("on")) {
					moveStatus = "D";
				}
			} catch (Exception e) {
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
			} catch (Exception e) {
				// TODO: handle exception
			}
			if (unitId.length() < 3) {
				unitId = gpsModel + initIMEI + imei.trim();
				unitId = unitId.trim();
				unitId = unitId.replace(" ", "");
//				System.out.println("GetUnitId : " + masterFileForm.getUnitId());
			}

			String DUnit = gpsModel + "00000000";
			String dummyCode = null;

			Connection connectDB;
			Statement stmtUD;
			String uriDB = serverIP.getMysql();
			Class.forName("com.mysql.jdbc.Driver");
			connectDB = DriverManager.getConnection(uriDB);
			stmtUD = connectDB.createStatement();

			if (gpsModel.equals("0430001")) {
				dummyCode = "MBD01";
			} else if (gpsModel.equals("0430002")) {
				dummyCode = "MBD02";
			} else if (gpsModel.equals("0430003")) {
				dummyCode = "TMT01";
			} else if (gpsModel.equals("0430005")) {
				dummyCode = "MLY01";
			} else if (gpsModel.equals("0430006")) {
				dummyCode = "MSM02";
			} else if (gpsModel.equals("0430008")) {
				dummyCode = "MSM01";
			} else if (gpsModel.equals("0430017")) {
				dummyCode = "MTM01";
			} else if (gpsModel.equals("0430012")) {
				dummyCode = "MTM02";
			} else if (gpsModel.equals("0430013")) {
				dummyCode = "MTG01";
			} else if (gpsModel.equals("0430014")) {
				dummyCode = "MTG02";
			} else if (gpsModel.equals("0430015")) {
				dummyCode = "TFF01";
			} else if (gpsModel.equals("0430016")) {
				dummyCode = "TCC02";
			} else if (gpsModel.equals("0430018")) {
				dummyCode = "TFF02";
			} else if (gpsModel.equals("0430019")) {
				dummyCode = "TRP01";
			}

			String selectUnitDummy = "SELECT unit_dummy, MAX(code_gen) as code FROM `master_file` WHERE unit_dummy = '"
					+ dummyCode + "'";
			ResultSet rsUD = stmtUD.executeQuery(selectUnitDummy);
			String sCode = "";
			while (rsUD.next()) {
				sCode = rsUD.getString("code");
			}
			try {
				String newCode = "";
				if (sCode != null) {
					System.out.println("Code : " + sCode);
					System.out.println("Code Length : " + sCode.length());
					sCode = (Integer.parseInt(sCode) + 1) + "";
					for (int i = 6; sCode.length() < i; i--) {
						newCode += "0";
					}
					sCode = newCode + sCode;
					System.out.println("for : " + sCode);
				} else {
					sCode = "000001";
					System.out.println("Code : " + sCode);
				}
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("Code : " + sCode);
			}

			String dummyUnitId = DUnit + dummyCode + moveStatus + sCode;
			System.out.println("DUnit : " + DUnit + dummyCode + moveStatus + sCode);

			// System.out.println("initVehicleId : " + initVehicleId+vehicleId);
//			System.out.println("unitId : " + unitId);

			String json = "{\"vender_id\": 43,\"unit_id\": \"" + dummyUnitId + "\",\"vehicle_id\": \"" + initVehicleId
					+ vehicleId + "\",\"vehicle_type\": \"" + vehicleType + "\",\"vehicle_chassis_no\": \""
					+ vehicleChassisNo + "\",\"vehicle_register_type\": " + vehicleRegisterType + ",\"card_reader\": "
					+ cardReader + ",\"province_code\": " + provinceCode + "}";
			byte[] encodedBytes = Base64.encodeBase64((byte[]) serverIP.getUserPassMaster().getBytes());
			String url = String.valueOf(serverIP.getUrlMesterFile()) + "/masterfile/add";

			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost post = new HttpPost(url);
			StringEntity postingString = new StringEntity(json);

			final X509Certificate[] _AcceptedIssuers = new X509Certificate[] {};
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
			ctx.init(null, new TrustManager[] { tm }, new SecureRandom());
			SSLSocketFactory ssf = new SSLSocketFactory(ctx, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			ClientConnectionManager ccm = httpClient.getConnectionManager();
			SchemeRegistry sr = ccm.getSchemeRegistry();
			sr.register(new Scheme("https", 443, (SchemeSocketFactory) ssf));
			post.setHeader("Authorization", "basic " + new String(encodedBytes));
			post.addHeader("Content-Type", "application/json");
			post.setEntity((HttpEntity) postingString);
			HttpResponse res = null;
//			int test = 200;

			if (dltStatus == 1) {
//				res.setStatusCode(400);
				res = httpClient.execute((HttpUriRequest) post);
				String responseAsString = EntityUtils.toString(res.getEntity());
				System.out.println("Json : " + json);
				System.out.println("HttpResponse : " + responseAsString);
//				test = res.getStatusLine().getStatusCode();

				if (res.getStatusLine().getStatusCode() == 200) {

					Connection connect;
					Connection connect2;
					Connection connect3;
					Connection TMconnect;
					Statement stmt;
					Statement stmt2;
					Statement stmt3;
					Statement TMstmt;

					String urlMSSQL = serverIP.getTrackingServer();
					Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
					connect = DriverManager.getConnection(urlMSSQL);
					stmt = connect.createStatement();

//					if (gpsModel.equals("0430003")) {
//
//						String query = "update [ms03].[dbo].[GPS_Last] set [unit_id] = '" + gpsModel + initIMEI + imei + "' WHERE [sn_imei_id] = '" + imei + "'";
//						stmt.executeUpdate(query);
//					}
					if (gpsModel.equals("0430008") || gpsModel.equals("0430006")) {
						String url2 = serverIP.getMhdServer();

						Class.forName("com.mysql.jdbc.Driver");
						connect3 = DriverManager.getConnection(url2);
						stmt3 = connect3.createStatement();

						String query2 = "UPDATE `wcms4`.`vehicledevice` SET `Unit_id` = '" + dummyUnitId
								+ "' WHERE `DeviceID` = " + "'" + imei.trim() + "';";
						stmt3.executeUpdate(query2);

						// System.out.println("query2 : " + query2);
					}

					else if (gpsModel.equals("0430005")) {
						String url2 = serverIP.getMysql();

						Class.forName("com.mysql.jdbc.Driver");
						connect3 = DriverManager.getConnection(url2);
						stmt3 = connect3.createStatement();

						String query2 = "UPDATE `distar_dlt`.`lty_unitid` SET `unit_id` = '" + dummyUnitId
								+ "' WHERE `vehicle_id` = " + "'" + imei.trim() + "';";
						stmt3.executeUpdate(query2);

						// System.out.println("query2 : " + query2);
					}

					else if (gpsModel.equals("0430001") || gpsModel.equals("0430002")) {

						String query = "";
						String devNum = "";
						String devNumDB = "";

						Class.forName("com.mysql.jdbc.Driver");
						connect2 = DriverManager.getConnection(serverIP.getMdvrServer2());

						stmt2 = connect2.createStatement();
						try {
							devNum = masterFileForm.getDevNum();
							System.out.println("DevNum : " + devNum + " unitId : " + dummyUnitId);
							try {
								String selectDevIDNO = "SELECT DevIDNO FROM driver_log WHERE `DevIDNO` = '" + devNum
										+ "'";
								ResultSet rs = stmt2.executeQuery(selectDevIDNO);
								while (rs.next()) {
									devNumDB = rs.getString("DevIDNO");
								}

								if (devNumDB.length() > 1) {
									query = "UPDATE `1010gps`.`driver_log` SET `unitId` = '" + dummyUnitId
											+ "' WHERE `DevIDNO` = '" + devNum + "' ";
									stmt2.executeUpdate(query);
//									System.out.println("update : " + query);
								} else {
									query = "INSERT INTO `1010gps`.`driver_log` (`DevIDNO`,`unitId`) VALUES ('" + devNum
											+ "','" + dummyUnitId + "')";
									stmt2.executeUpdate(query);
//									System.out.println("insert: " + query);
								}

							} catch (Exception e) {
								query = "INSERT INTO `1010gps`.`driver_log` (`DevIDNO`,`unitId`) VALUES ('" + devNum
										+ "','" + dummyUnitId + "')";
								stmt2.executeUpdate(query);
//								System.out.println("insert: " + query);
							}
							stmt2.executeUpdate(query);
						} catch (Exception e) {
							devNum = null;
						}

					} else if (gpsModel.equals("0430017") || gpsModel.equals("0430012")) {
//						Class.forName("com.mysql.jdbc.Driver");
//						connect2 = DriverManager.getConnection(serverIP.getMdvrServer2());
//
//						stmt2 = connect2.createStatement();

						String TMurl = serverIP.getMysql();
						String query = "INSERT INTO unitid_thaibus  (device_id,vehicle_id,unit_id,license_plate,VIN,vehicle_brand,customer) "
								+ "VALUES  ('" + imei.trim() + "','" + initVehicleId + vehicleId + "','" + dummyUnitId
								+ "','" + initVehicleId + vehicleId + "','" + vehicleChassisNo + "','" + vehicleType
								+ "','" + customerName + "' ) ;";
						Class.forName("com.mysql.jdbc.Driver");
						TMconnect = DriverManager.getConnection(TMurl);
						TMstmt = TMconnect.createStatement();
						System.out.println(query);
						TMstmt.executeUpdate(query);

					} else if (gpsModel.equals("0430003") || gpsModel.equals("0430013") || gpsModel.equals("0430014")
							|| gpsModel.equals("0430015") || gpsModel.equals("0430016") || gpsModel.equals("0430018") 
							|| gpsModel.equals("0430019") ) {

						MongoClient client = new MongoClient(host_url, port_no);

						try {

							DB database = client.getDB("distar_gateway");
							DBCollection collection = database.getCollection("dlt_realtime_location");

							BasicDBObject searchQuery2 = new BasicDBObject().append("imei", imei.trim());

							DBCursor cursor2 = collection.find(searchQuery2);

							BasicDBObject newDocument = new BasicDBObject();
							newDocument.put("unit_id", dummyUnitId);
							BasicDBObject updateObj = new BasicDBObject();
							updateObj.put("$set", newDocument);

							collection.update(searchQuery2, updateObj);

						} finally {
							client.close();
						}

					} else {
//						System.out.println("gpsModel : " + gpsModel);
						// System.out.println("query : " + query);
					}
				}
			}

//			System.out.println(json);
//			System.out.println("res : " + res);

//			if (test == 200) {

			SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd", lc);
//				System.out.println("Install Date : "+masterFileForm.getInstallDate());
			Date installDate = null;
			try {
				installDate = dateformat.parse(masterFileForm.getInstallDate());
			} catch (Exception e) {
				// TODO: handle exception
			}

			Connection connect;
			Statement stmt;

			String uri = serverIP.getMysql();
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection(uri);
			stmt = connect.createStatement();

			// Add new master file

			MasterFile masterFile = masterFileDAO.findByIMEI(imei);
			if (masterFile == null) {
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
				mf.setSaleName(saleName);
				mf.setDateCreated(new Date());
				mf.setUserCreate(userLogin);
				mf.setInstallDate(installDate);
				mf.setDltStatus(dltStatus);
				mf.setDeleteStatus(1);
				mf.setStatus(1);
				mf.setRemark(masterFileForm.getRemark());
				mf.setRemark2(masterFileForm.getRemark2());
				masterFileDAO.persist(mf);

				String query = "UPDATE master_file SET province_code = '" + provinceCode + "',vehicle_register_type = '"
						+ vehicleRegisterType + "',unit_dummy = '" + dummyCode + "',code_gen = '" + sCode
						+ "' WHERE unit_id = '" + dummyUnitId + "'";

				System.out.println("query : " + query);
				stmt.executeUpdate(query);

				System.out.println("Add New MasterFile " + provinceDLTDAO.findById(provinceCode).getProvinceName()
						+ " : " + provinceCode);

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
				masterFile.setMoveStatus(move);
				masterFile.setDateUpdated(new Date());
				masterFile.setUserUpdate(userLogin);
				masterFile.setInstallDate(installDate);
				masterFile.setDltStatus(dltStatus);
				masterFile.setDeleteStatus(1);
				masterFile.setStatus(1);
				masterFile.setRemark(masterFileForm.getRemark());
				masterFile.setRemark2(masterFileForm.getRemark2());
				masterFileDAO.merge(masterFile);

				String query = "UPDATE master_file SET province_code = '" + provinceCode + "',vehicle_register_type = '"
						+ vehicleRegisterType + "',unit_dummy = '" + dummyCode + "',code_gen = '" + sCode
						+ "' WHERE unit_id = '" + dummyUnitId + "'";

				System.out.println("query : " + query);
				stmt.executeUpdate(query);

				System.out.println("Update MasterFile : " + provinceDLTDAO.findById(provinceCode).getProvinceName()
						+ " : " + provinceCode);
			}
			if (dltStatus == 0) {
				System.out.println("dltStatus : " + dltStatus);

				return new ModelAndView("redirect:/masterfile/rmvMasterFile.htm?unitId=" + dummyUnitId);
			}

//			}
//				System.out.println(res.getStatusLine().getStatusCode());
			// return new ModelAndView("redirect:/member.htm", "status", 200);

			return new ModelAndView("redirect:/member.htm", "status", res.getStatusLine().getStatusCode());
		}
		return new ModelAndView("redirect:/");

	}

	public ModelAndView editMasterFile(HttpServletRequest request, HttpServletResponse response,
			MasterFileForm masterFileForm) throws Exception {
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
			} catch (Exception e) {
				cardReader = 0;
			}

//			System.out.println("DltStatus : " + masterFileForm.getDltStatus());
			int dltStatus = 0;
			try {
				if (masterFileForm.getDltStatus().equals("on")) {
					dltStatus = 1;
				}
			} catch (Exception e) {
				dltStatus = 0;
			}

			int move = 0;
			try {
				if (masterFileForm.getMove().equals("on")) {
					move = 0;
				}
			} catch (Exception e) {
				dltStatus = 1;
			}

			String moveStatus = "";
			try {
				if (masterFileForm.getMove().equals("on")) {
					moveStatus = "D";
				}
			} catch (Exception e) {
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
				System.out.println("GetUnitId : " + unitId);
			} catch (Exception e) {
				// TODO: handle exception
			}

			// System.out.println("initVehicleId : " + initVehicleId+vehicleId);
			System.out.println("unitId : " + unitId);

			String json = "{\"vender_id\": 43,\"unit_id\": \"" + unitId + "\",\"vehicle_id\": \"" + initVehicleId
					+ vehicleId + "\",\"vehicle_type\": \"" + vehicleType + "\",\"vehicle_chassis_no\": \""
					+ vehicleChassisNo + "\",\"vehicle_register_type\": " + vehicleRegisterType + ",\"card_reader\": "
					+ cardReader + ",\"province_code\": " + provinceCode + "}";
			byte[] encodedBytes = Base64.encodeBase64((byte[]) serverIP.getUserPassMaster().getBytes());
			String url = String.valueOf(serverIP.getUrlMesterFile()) + "/masterfile/add";

			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost post = new HttpPost(url);
			StringEntity postingString = new StringEntity(json);

			final X509Certificate[] _AcceptedIssuers = new X509Certificate[] {};
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
			ctx.init(null, new TrustManager[] { tm }, new SecureRandom());
			SSLSocketFactory ssf = new SSLSocketFactory(ctx, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			ClientConnectionManager ccm = httpClient.getConnectionManager();
			SchemeRegistry sr = ccm.getSchemeRegistry();
			sr.register(new Scheme("https", 443, (SchemeSocketFactory) ssf));
			post.setHeader("Authorization", "basic " + new String(encodedBytes));
			post.addHeader("Content-Type", "application/json");
			post.setEntity((HttpEntity) postingString);
			HttpResponse res = null;
//			int test = 200;

			if (dltStatus == 1) {
//				res.setStatusCode(400);
				res = httpClient.execute((HttpUriRequest) post);
				String responseAsString = EntityUtils.toString(res.getEntity());
				System.out.println("Json : " + json);
				System.out.println("HttpResponse : " + responseAsString);
//				test = res.getStatusLine().getStatusCode();

				if (res.getStatusLine().getStatusCode() == 200) {

					Connection connect;
					Connection connect2;
					Connection connect3;
					Connection TMconnect;
					Statement stmt;
					Statement stmt2;
					Statement stmt3;
					Statement TMstmt;

					String urlMSSQL = serverIP.getTrackingServer();
					Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
					connect = DriverManager.getConnection(urlMSSQL);
					stmt = connect.createStatement();

//					if (gpsModel.equals("0430003")) {
//
//						String query = "update [ms03].[dbo].[GPS_Last] set [unit_id] = '" + gpsModel + initIMEI + imei + "' WHERE [sn_imei_id] = '" + imei + "'";
//						stmt.executeUpdate(query);
//					}
					if (gpsModel.equals("0430008") || gpsModel.equals("0430006")) {
						String url2 = serverIP.getMhdServer();

						Class.forName("com.mysql.jdbc.Driver");
						connect3 = DriverManager.getConnection(url2);
						stmt3 = connect3.createStatement();

						String query2 = "UPDATE `wcms4`.`vehicledevice` SET `Unit_id` = '" + unitId
								+ "' WHERE `DeviceID` = " + "'" + imei.trim() + "';";
						stmt3.executeUpdate(query2);

						// System.out.println("query2 : " + query2);
					}

					else if (gpsModel.equals("0430005")) {
						String url2 = serverIP.getMysql();

						Class.forName("com.mysql.jdbc.Driver");
						connect3 = DriverManager.getConnection(url2);
						stmt3 = connect3.createStatement();

						String query2 = "UPDATE `distar_dlt`.`lty_unitid` SET `unit_id` = '" + unitId
								+ "' WHERE `vehicle_id` = " + "'" + imei.trim() + "';";
						stmt3.executeUpdate(query2);

						// System.out.println("query2 : " + query2);
					}

					else if (gpsModel.equals("0430001") || gpsModel.equals("0430002")) {

						String query = "";
						String devNum = "";
						String devNumDB = "";

						Class.forName("com.mysql.jdbc.Driver");
						connect2 = DriverManager.getConnection(serverIP.getMdvrServer2());

						stmt2 = connect2.createStatement();
						try {
							devNum = masterFileForm.getDevNum();
							System.out.println("DevNum : " + devNum + " unitId : " + unitId);
							try {
								String selectDevIDNO = "SELECT DevIDNO FROM driver_log WHERE `DevIDNO` = '" + devNum
										+ "'";
								ResultSet rs = stmt2.executeQuery(selectDevIDNO);
								while (rs.next()) {
									devNumDB = rs.getString("DevIDNO");
								}

								if (devNumDB.length() > 1) {
									query = "UPDATE `1010gps`.`driver_log` SET `unitId` = '" + unitId
											+ "' WHERE `DevIDNO` = '" + devNum + "' ";
									stmt2.executeUpdate(query);
//									System.out.println("update : " + query);
								} else {
									query = "INSERT INTO `1010gps`.`driver_log` (`DevIDNO`,`unitId`) VALUES ('" + devNum
											+ "','" + unitId + "')";
									stmt2.executeUpdate(query);
//									System.out.println("insert: " + query);
								}

							} catch (Exception e) {
								query = "INSERT INTO `1010gps`.`driver_log` (`DevIDNO`,`unitId`) VALUES ('" + devNum
										+ "','" + unitId + "')";
								stmt2.executeUpdate(query);
//								System.out.println("insert: " + query);
							}
							stmt2.executeUpdate(query);
						} catch (Exception e) {
							devNum = null;
						}

					} else if (gpsModel.equals("0430017") || gpsModel.equals("0430012")) {
//						Class.forName("com.mysql.jdbc.Driver");
//						connect2 = DriverManager.getConnection(serverIP.getMdvrServer2());
//
//						stmt2 = connect2.createStatement();

						String TMurl = serverIP.getMysql();
						String query = "INSERT INTO unitid_thaibus  (device_id,vehicle_id,unit_id,license_plate,VIN,vehicle_brand,customer) "
								+ "VALUES  ('" + imei.trim() + "','" + initVehicleId + vehicleId + "','" + unitId
								+ "','" + initVehicleId + vehicleId + "','" + vehicleChassisNo + "','" + vehicleType
								+ "','" + customerName + "' ) ;";
						Class.forName("com.mysql.jdbc.Driver");
						TMconnect = DriverManager.getConnection(TMurl);
						TMstmt = TMconnect.createStatement();
						System.out.println(query);
						TMstmt.executeUpdate(query);

					} else if (gpsModel.equals("0430003") || gpsModel.equals("0430013") || gpsModel.equals("0430014")
							|| gpsModel.equals("0430015") || gpsModel.equals("0430016") || gpsModel.equals("0430018")) {

						MongoClient client = new MongoClient(host_url, port_no);

						try {
							System.out.println("old imei : " + masterFileForm.getOldImei());

							DB database = client.getDB("distar_gateway");
							DBCollection collection = database.getCollection("dlt_realtime_location");

							DBCollection collectionOld = database.getCollection("dlt_realtime_location");

//			                BasicDBObject searchQuery2 = new BasicDBObject().append("imei", imei.trim());
							BasicDBObject searchQueryOld = new BasicDBObject().append("imei",
									masterFileForm.getOldImei().trim());

							DBCursor cursorOld = collection.find(searchQueryOld);

							BasicDBObject newDocumentOld = new BasicDBObject();
							newDocumentOld.put("unit_id", "");
							BasicDBObject updateObjOld = new BasicDBObject();
							updateObjOld.put("$set", newDocumentOld);

							collectionOld.update(searchQueryOld, updateObjOld);

							//////////////////////////////////////////////////////////////////////////////////////////////////////////////

//			                BasicDBObject searchQuery2 = new BasicDBObject().append("imei", imei.trim());
							BasicDBObject searchQuery2 = new BasicDBObject().append("imei", imei.trim());

							DBCursor cursor2 = collection.find(searchQuery2);

							BasicDBObject newDocument = new BasicDBObject();
							newDocument.put("unit_id", unitId);
							BasicDBObject updateObj = new BasicDBObject();
							updateObj.put("$set", newDocument);

							collection.update(searchQuery2, updateObj);

						} finally {
							client.close();
						}

					}
				}
			}

			SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd", lc);
//				System.out.println("Install Date : "+masterFileForm.getInstallDate());
			Date installDate = null;
			try {
				installDate = dateformat.parse(masterFileForm.getInstallDate());
			} catch (Exception e) {
				// TODO: handle exception
			}

			Connection connect;
			Statement stmt;

			String uri = serverIP.getMysql();
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection(uri);
			stmt = connect.createStatement();

			// Add new master file

			MasterFile masterFile = masterFileDAO.findByUnitId(unitId);
			if (masterFile == null) {
				MasterFile mf = new MasterFile();

				mf.setVehicleRegisterType(vehicleRegisterTypeDAO.findById(vehicleRegisterType));
				mf.setProvinceCode(provinceDLTDAO.findById(provinceCode));
				mf.setGpsModel(gpsModel);
				mf.setVehicleId(initVehicleId + vehicleId);
				mf.setVehicleType(vehicleType);
				mf.setImei(imei);
				mf.setUnitId(unitId);
				mf.setMoveStatus(move);
				mf.setVehicleChassisNo(vehicleChassisNo);
				mf.setCardReader(cardReader);
				mf.setCustomerName(customerName);
				mf.setSaleName(saleName);
				mf.setDateCreated(new Date());
				mf.setUserCreate(userLogin);
				mf.setInstallDate(installDate);
				mf.setDltStatus(dltStatus);
				mf.setDeleteStatus(1);
				mf.setStatus(1);
				mf.setRemark(masterFileForm.getRemark());
				mf.setRemark2(masterFileForm.getRemark2());
				masterFileDAO.persist(mf);

				String query = "UPDATE master_file SET province_code = '" + provinceCode + "',vehicle_register_type = '"
						+ vehicleRegisterType + "' WHERE unit_id = '" + unitId + "'";

				System.out.println("query : " + query);
				stmt.executeUpdate(query);

				System.out.println("Add New MasterFile " + provinceDLTDAO.findById(provinceCode).getProvinceName()
						+ " : " + provinceCode);

			}

			// Edit master file
			else {
//					System.out.println("ImeiAndChass " + imei +" : " + vehicleChassisNo);
				MasterFile ImeiAndChass = masterFileDAO.findByImeiAndChass(imei, vehicleChassisNo);
//					System.out.println("ImeiAndChass " + ImeiAndChass.getImei() );
				if (ImeiAndChass == null) {
					System.out.println("ImeiAndChass ");
					MasterFile imei_ = masterFileDAO.findByIMEI(imei);
					if (imei_ == null) {
						// masterFile.setImei(imei);
						masterFile.setImei(imei);
						masterFile.setRemark(masterFileForm.getRemark());
						masterFile.setRemark2(masterFileForm.getRemark2());
						masterFile.setDltStatus(dltStatus);
						masterFile.setDeleteStatus(1);
						masterFile.setStatus(1);
						masterFileDAO.merge(masterFile);
						
						if (dltStatus == 0) {
							System.out.println("dltStatus : " + dltStatus);
							System.out.println("imei : " + imei);
							
							MongoClient client = new MongoClient(host_url, port_no);
							DB database = client.getDB("distar_gateway");
				            DBCollection collection = database.getCollection("dlt_realtime_location");
				                
							BasicDBObject searchQuery2 = new BasicDBObject().append("imei", imei.trim());

							DBCursor cursor2 = collection.find(searchQuery2);
							
			                BasicDBObject newDocument = new BasicDBObject();
			                newDocument.put("unit_id", "");
							BasicDBObject updateObj = new BasicDBObject();
							updateObj.put("$set", newDocument);

							collection.update(searchQuery2, updateObj);

						}

						System.out.println("Update device imei ");
					} else {
						List<ProvinceDLT> listProvince = provinceDLTDAO.orderByProvinceName();
						List<VehicleRegisterType> listVehicle = vehicleRegisterTypeDAO.orderByVehicleRegisterType();

						try {
							masterFileForm.setDateInstall(dateformat.format(installDate));
						} catch (Exception e) {
							masterFileForm.setDateInstall(null);
						}

//								System.out.println("getCardReader : " + masterFileForm.getCardReader());
//								System.out.println("getDltStatus : " + masterFileForm.getDltStatus());
//								System.out.println("getMove : " + masterFileForm.getMove());

						String CardStatus = "0";
						String DLTStatus = "0";
						String MoveStatus = "0";

						if (masterFileForm.getCardReader().equals("on")) {
							CardStatus = "1";
						}
						if (masterFileForm.getDltStatus().equals("on")) {
							DLTStatus = "1";
						}
						if (masterFileForm.getMove().equals("on")) {
							MoveStatus = "0";
						}

						masterFileForm.setCardReader(CardStatus);
						masterFileForm.setDltStatus(DLTStatus);
						masterFileForm.setMove(MoveStatus);

//								System.out.println("messageCode 101");
						ModelMap modelMap = new ModelMap();
						modelMap.addAttribute("masterFileForm", masterFileForm);
						modelMap.addAttribute("listProvince", listProvince);
						modelMap.addAttribute("listVehicle", listVehicle);
						modelMap.addAttribute("messageCode", "101");

						return new ModelAndView("/masterfile/changeDevice", modelMap);
					}
				} else {

					System.out.println("Update device imei 2");
					masterFile.setVehicleRegisterType(vehicleRegisterTypeDAO.findById(vehicleRegisterType));
					masterFile.setProvinceCode(provinceDLTDAO.findById(provinceCode));
					masterFile.setGpsModel(gpsModel);
					masterFile.setImei(imei);
					masterFile.setVehicleType(vehicleType);
					masterFile.setVehicleId(initVehicleId + vehicleId);
					masterFile.setUnitId(unitId);
					masterFile.setVehicleChassisNo(vehicleChassisNo);
					masterFile.setCardReader(cardReader);
					masterFile.setCustomerName(customerName);
					masterFile.setSaleName(saleName);
					masterFile.setMoveStatus(move);
					masterFile.setDateUpdated(new Date());
					masterFile.setUserUpdate(userLogin);
					masterFile.setInstallDate(installDate);
					masterFile.setDltStatus(dltStatus);
					masterFile.setDeleteStatus(dltStatus);
					masterFile.setStatus(1);
					masterFile.setRemark(masterFileForm.getRemark());
					masterFile.setRemark2(masterFileForm.getRemark2());
					masterFileDAO.merge(masterFile);

					String query = "UPDATE master_file SET province_code = '" + provinceCode
							+ "',vehicle_register_type = '" + vehicleRegisterType + "' WHERE unit_id = '" + unitId
							+ "'";
					System.out.println("query 2: " + query);
					stmt.executeUpdate(query);

					System.out.println("Update MasterFile 2: " + provinceDLTDAO.findById(provinceCode).getProvinceName()
							+ " : " + provinceCode);

					if (dltStatus == 0) {
						
						// If DLT status is not send will delete unitId in mongodb and change status in MYSql masterfile to delete 
						System.out.println("dltStatus : " + dltStatus);
						System.out.println("imei 2 : " + imei);
						
						MongoClient client = new MongoClient(host_url, port_no);
						DB database = client.getDB("distar_gateway");
			            DBCollection collection = database.getCollection("dlt_realtime_location");
			                
						BasicDBObject searchQuery2 = new BasicDBObject().append("imei", imei.trim());

						DBCursor cursor2 = collection.find(searchQuery2);
						
		                BasicDBObject newDocument = new BasicDBObject();
		                newDocument.put("unit_id", "");
						BasicDBObject updateObj = new BasicDBObject();
						updateObj.put("$set", newDocument);

						collection.update(searchQuery2, updateObj);

						return new ModelAndView("redirect:/masterfile/rmvMasterFile.htm?unitId=" + unitId);
					}
				}

			}

			return new ModelAndView("redirect:/member.htm", "status", 200);
		}
		return new ModelAndView("redirect:/");

	}

	public ModelAndView getMesterFileList(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		if (super.chkSession(request)) {

		int skip = 0;
		int take = 10000;

//			try {
//				skip = 0;
//				take = 1000;
//			}
//			catch (Exception e) {
//				// TODO: handle exception
//			}

		byte[] encodedBytes = Base64.encodeBase64((byte[]) serverIP.getUserPassMaster().getBytes());
		String url = String.valueOf(serverIP.getUrlMesterFile()) + "/masterfile/getList/" + skip + "/" + take;
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpGet get = new HttpGet(url);

		final X509Certificate[] _AcceptedIssuers = new X509Certificate[] {};
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
		ctx.init(null, new TrustManager[] { tm }, new SecureRandom());
		SSLSocketFactory ssf = new SSLSocketFactory(ctx, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
		ClientConnectionManager ccm = httpClient.getConnectionManager();
		SchemeRegistry sr = ccm.getSchemeRegistry();
		sr.register(new Scheme("https", 443, (SchemeSocketFactory) ssf));

		get.setHeader("Authorization", "basic " + new String(encodedBytes));
		get.addHeader("Content-Type", "application/json");
		HttpResponse res = httpClient.execute((HttpUriRequest) get);
		BufferedReader rd = new BufferedReader(new InputStreamReader(res.getEntity().getContent()));
		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}

		List<List<Object>> table = new ArrayList<List<Object>>();
		List<Object> row = new ArrayList<Object>();

		int i = 1;
		try {
			JsonNode arrNode = new ObjectMapper().readTree(result.toString()).get("results");
			if (arrNode.isArray()) {

				for (JsonNode objNode : arrNode) {
					row.add(objNode.get("unit_id").getTextValue());
					row.add(objNode.get("vehicle_chassis_no").getTextValue());
					row.add(objNode.get("vehicle_id").getTextValue());
					row.add(objNode.get("vehicle_type").getTextValue());
					row.add(objNode.get("vehicle_register_type"));

					MasterFile masterFile = new MasterFile();
					try {

						masterFile = masterFileDAO.findByUnitId(objNode.get("unit_id").getTextValue());

						try {
							row.add(masterFile.getCustomerName());
							row.add(masterFile.getSaleName());
						} catch (Exception e) {
							row.add("");
							row.add("");
						}

						// row.add("ไม่พบข้อมูล");
						try {
							row.add(masterFile.getDltUpdateTime().toString());
						} catch (Exception e) {
							row.add("ไม่พบข้อมูล");
							// System.out.println("ไม่พบข้อมูล");
						}

						row.add(i);
					} catch (Exception e) {
						System.out.println("getDltUpdateTime : Error!");
						row.add("");
						row.add("");
					}

					i++;
				}
				table.add(row);
				// System.out.println("i : "+i);
			}
		}

		catch (Exception e) {
			// System.out.println("Null");
		}

		ModelMap modelMap = new ModelMap();
		modelMap.addAttribute("table", table);
		modelMap.addAttribute("row", row);
		modelMap.addAttribute("count", i);

		return new ModelAndView("masterfile/listMasterFile", modelMap);
//		}
//		return new ModelAndView("redirect:/");

	}

	public ModelAndView getMasterfileListMongoDB(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		List<String> json = Util.masterfilelist();
//		String initData = "{ \"data\" : ";

//			System.out.println("json : "+json);

		ModelMap modelMap = new ModelMap();
//			modelMap.addAttribute("table", table);
//			modelMap.addAttribute("row", row);
		modelMap.addAttribute("json", json.toString());

		return new ModelAndView("masterfile/test", modelMap);

	}

	public ModelAndView updateMasterFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (super.chkSession(request)) {

			Connection connect;
			Statement stmt;

			Connection connect2;
			Statement stmt2;

			String url = serverIP.getTrackingServer();
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			connect = DriverManager.getConnection(url);
			stmt = connect.createStatement();

			String uri = serverIP.getMysql();
			Class.forName("com.mysql.jdbc.Driver");
			connect2 = DriverManager.getConnection(uri);
			stmt2 = connect2.createStatement();

			String sn_imei_id = null;
			String tracker_name = null;
			String tracker_sim_number = null;
			String tracker_register_time = null;
			String tracker_expired_time = null;

			if (connect != null) {
				String query = "SELECT [sn_imei_id],[tracker_name],[tracker_sim_number],[tracker_register_time],[tracker_expired_time] FROM [ms03].[dbo].[Tracker]";
				ResultSet rs = stmt.executeQuery(query);
				while (rs.next()) {
					sn_imei_id = rs.getString("sn_imei_id");
					tracker_name = rs.getString("tracker_name");
					tracker_sim_number = rs.getString("tracker_sim_number");
					tracker_register_time = rs.getString("tracker_register_time");
					tracker_expired_time = rs.getString("tracker_expired_time");

					try {
						String sql = "UPDATE `distar_dlt`.`master_file` SET `tracker_name` = '" + tracker_name
								+ "',`tracker_sim_number` = '" + tracker_sim_number + "', `tracker_register_time` = '"
								+ tracker_register_time + "', `tracker_expired_time` = '" + tracker_expired_time
								+ "' WHERE `imei` = '" + sn_imei_id + "'";
						stmt2.executeUpdate(sql);
					} catch (Exception e) {
						// TODO: handle exception
						System.out.println(e.getMessage());
					}

				}
			}

			connect.close();
			connect2.close();

			return new ModelAndView("redirect:/member.htm");
		}
		return new ModelAndView("redirect:/");

	}

	public ModelAndView getMesterFileList2(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (super.chkSession(request)) {

			// List<MasterFile> listMasterFile =
			// masterFileDAO.findWithOutBMTA();
			List<MasterFile> listMasterFile = masterFileDAO.findSendMasterfile(1);
			System.out.println("listMasterFile : " + listMasterFile.size());

			ModelMap modelMap = new ModelMap();
			modelMap.addAttribute("listMasterFile", listMasterFile);

			return new ModelAndView("masterfile/listMasterFile3", modelMap);
		}
		return new ModelAndView("redirect:/");

	}

	public ModelAndView getMesterFileListRemoved(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (super.chkSession(request)) {

			// List<MasterFile> listMasterFile =
			// masterFileDAO.findWithOutBMTA();
			List<MasterFile> listMasterFile = masterFileDAO.findSendMasterfile(0);
			System.out.println("listMasterFile : " + listMasterFile.size());

			ModelMap modelMap = new ModelMap();
			modelMap.addAttribute("listMasterFile", listMasterFile);

			return new ModelAndView("masterfile/listMasterFile3", modelMap);
		}
		return new ModelAndView("redirect:/");

	}

	public ModelAndView getMesterFileOther(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (super.chkSession(request)) {

			// List<MasterFile> listMasterFile =
			// masterFileDAO.findWithOutBMTA();
			List<MasterFile> listMasterFile = masterFileDAO.findOther();
			System.out.println("listMasterFile : " + listMasterFile.size());

			ModelMap modelMap = new ModelMap();
			modelMap.addAttribute("listMasterFile", listMasterFile);

			return new ModelAndView("masterfile/listMasterFile2", modelMap);
		}
		return new ModelAndView("redirect:/");

	}

	public ModelAndView getMesterFileTracking(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (super.chkSession(request)) {

			// List<MasterFile> listMasterFile =
			// masterFileDAO.findWithOutBMTA();
			List<MasterFile> listMasterFile = masterFileDAO.findTracking();
			System.out.println("listMasterFile : " + listMasterFile.size());

			ModelMap modelMap = new ModelMap();
			modelMap.addAttribute("listMasterFile", listMasterFile);

			return new ModelAndView("masterfile/listMasterFile2", modelMap);
		}
		return new ModelAndView("redirect:/");

	}

	public ModelAndView getMesterFileMDVR(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (super.chkSession(request)) {

			// List<MasterFile> listMasterFile =
			// masterFileDAO.findWithOutBMTA();
			List<MasterFile> listMasterFile = masterFileDAO.findMDVR();
			System.out.println("listMasterFile : " + listMasterFile.size());

			ModelMap modelMap = new ModelMap();
			modelMap.addAttribute("listMasterFile", listMasterFile);

			return new ModelAndView("masterfile/listMasterFile2", modelMap);
		}
		return new ModelAndView("redirect:/");

	}

	public ModelAndView getMesterFileMHD(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (super.chkSession(request)) {

			// List<MasterFile> listMasterFile =
			// masterFileDAO.findWithOutBMTA();
			List<MasterFile> listMasterFile = masterFileDAO.findMHD();
			System.out.println("listMasterFile : " + listMasterFile.size());

			ModelMap modelMap = new ModelMap();
			modelMap.addAttribute("listMasterFile", listMasterFile);

			return new ModelAndView("masterfile/listMasterFile2", modelMap);
		}
		return new ModelAndView("redirect:/");

	}

	public ModelAndView getMesterFileBMTA(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (super.chkSession(request)) {

			// List<MasterFile> listMasterFile =
			// masterFileDAO.findWithOutBMTA();
			List<MasterFile> listMasterFile = masterFileDAO.findBMTA();
			System.out.println("listMasterFile : " + listMasterFile.size());

			ModelMap modelMap = new ModelMap();
			modelMap.addAttribute("listMasterFile", listMasterFile);

			return new ModelAndView("masterfile/listMasterFile2", modelMap);
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

	public ModelAndView rmvMasterFile(HttpServletRequest request, HttpServletResponse response,
			MasterFileForm masterFileForm) throws Exception {
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

				} else if (masterFileForm.getUnitId().length() > 5 && rvmStatus == 1) {
					System.out.println("masterFileForm.getUnitId() : " + masterFileForm.getUnitId());
					unitId = masterFileForm.getUnitId();
					delStatus = 1;
				}

				System.out.println("delStatus : " + delStatus + " rvmStatus : " + rvmStatus);
			} catch (Exception e) {
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

			final X509Certificate[] _AcceptedIssuers = new X509Certificate[] {};
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
			ctx.init(null, new TrustManager[] { tm }, new SecureRandom());
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

						String remove = "UPDATE  `distar_dlt`.`master_file` SET `delete_status` = 0,`dlt_status` = 0 WHERE `unit_id` = '"
								+ unitId + "'";
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

						String sql = "UPDATE [dbo].[GPS_Last]  SET [unit_id] = (NULL) WHERE unit_id = '"
								+ masterFile.getUnitId() + "'";
						stmt.executeUpdate(sql);
					}
					// else if (unitId.startsWith("0430005")) {
					// LTYUnitId ltyUnitId =
					// ltyUnitIdDAO.findByUnitId(masterFile.getUnitId());
					// ltyUnitIdDAO.remove(ltyUnitId);
					// }

				}

			} catch (Exception e) {
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

	public ModelAndView rmvMasterFileFromList(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
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

				final X509Certificate[] _AcceptedIssuers = new X509Certificate[] {};
				SSLContext ctx = SSLContext.getInstance("TLS");
				X509TrustManager tm = new X509TrustManager() {
					@Override
					public X509Certificate[] getAcceptedIssuers() {
						return _AcceptedIssuers;
					}

					@Override
					public void checkServerTrusted(X509Certificate[] chain, String authType)
							throws CertificateException {
					}

					@Override
					public void checkClientTrusted(X509Certificate[] chain, String authType)
							throws CertificateException {
					}
				};
				ctx.init(null, new TrustManager[] { tm }, new SecureRandom());
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

				} catch (Exception e) {
					// TODO: handle exception
					System.out.println("Exception : " + e.getMessage());
				}

//				System.out.println("gpsModel : " + gpsModel);
				try {
					if (res.getStatusLine().getStatusCode() == 200 && gpsModel.equals("0430003")) {
						MasterFile masterFile = masterFileDAO.findByUnitId(unitId);
						System.out.println(unitId);
						masterFileDAO.remove(masterFile);
					} else if (res.getStatusLine().getStatusCode() == 200) {
						System.out.println("else : " + unitId);
					}
				} catch (Exception e) {
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

		} catch (Exception e) {
			// TODO: handle exception

		}

		return status;
	}

	public ModelAndView masterfileImport(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (super.chkSession(request)) {
			Connection connect = null;
			Statement stmt = null;
			Connection connect2 = null;
			Statement stmt2 = null;

			String mySqlUrl = serverIP.getMysql();
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection(mySqlUrl);
			stmt = connect.createStatement();

			connect2 = DriverManager.getConnection(mySqlUrl);
			stmt2 = connect2.createStatement();

			String query = "SELECT  `id`, `card_reader`, `distar_sn`, `gps_model`, `imei`, `province_code`, `unit_id`, `vehicle_chassis_no`, `vehicle_id`, `vehicle_register_type`, `vehicle_type`, `customer_name`, `sale_name` \n"
					+ "FROM `distar_dlt`.`excel_import`; ";
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				String update = "update `distar_dlt`.`lty_unitid` set  `unit_id` = '" + rs.getString("unit_id")
						+ "' where `vehicle_id` = '" + rs.getString("imei") + "' ;";
				System.out.println("update " + update);
				stmt2.executeUpdate(update);

//				String  masterfileUpdate = "INSERT INTO `distar_dlt`.`master_file` (" + 
//						"  `card_reader`, `gps_model`, `imei`, `province_code`, `unit_id`, `vehicle_chassis_no`, `vehicle_id`, `vehicle_register_type`, `vehicle_type`, `customer_name`, `sale_name`, `dlt_status`" + 
//						")" + 
//						"VALUES" + 
//						"  (" + 
//						"    '1', '"+rs.getString("gps_model")+"', '"+rs.getString("imei")+"', '"+rs.getString("province_code")+"', '"+rs.getString("unit_id")+"', '"+rs.getString("vehicle_chassis_no")+"', '"+rs.getString("vehicle_id")+"', '"+rs.getString("vehicle_register_type")+"', '"+rs.getString("vehicle_type")+"', 'BMTA', 'Zulex', '1'" + 
//						"  );";
//				System.out.println("masterfileUpdate " + masterfileUpdate);
//				stmt2.executeUpdate(masterfileUpdate);

				// Remove
//				String  masterfileRemove = "DELETE FROM   `distar_dlt`.`master_file` WHERE `unit_id` = '"+rs.getString("unit_id")+"';";
//				System.out.println("masterfileRemove " + masterfileRemove);
//				stmt2.executeUpdate(masterfileRemove);

				// Remove Masterfile
//				byte[] encodedBytes = Base64.encodeBase64((byte[]) serverIP.getUserPassMaster().getBytes());
//				String url = String.valueOf(serverIP.getUrlMesterFile()) + "/masterfile/rmvByUnit/" + rs.getString("unit_id");
//				DefaultHttpClient httpClient = new DefaultHttpClient();
//				HttpDelete delete = new HttpDelete(url);
//
//				final X509Certificate[] _AcceptedIssuers = new X509Certificate[]{};
//				SSLContext ctx = SSLContext.getInstance("TLS");
//				X509TrustManager tm = new X509TrustManager() {
//					@Override
//					public X509Certificate[] getAcceptedIssuers() {
//						return _AcceptedIssuers;
//					}
//
//					@Override
//					public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
//					}
//
//					@Override
//					public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
//					}
//				};
//				ctx.init(null, new TrustManager[]{tm}, new SecureRandom());
//				SSLSocketFactory ssf = new SSLSocketFactory(ctx, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
//				ClientConnectionManager ccm = httpClient.getConnectionManager();
//				SchemeRegistry sr = ccm.getSchemeRegistry();
//				sr.register(new Scheme("https", 443, (SchemeSocketFactory) ssf));
//
//				delete.setHeader("Authorization", "basic " + new String(encodedBytes));
//				delete.addHeader("Content-Type", "application/json");
//				HttpResponse res = httpClient.execute((HttpUriRequest) delete);
//				
//				System.out.println("unitId : " + rs.getString("unit_id"));
//				System.out.println("StatusCode : " + res.getStatusLine().getStatusCode());

				// Import Masterfiel
//				String json = "{\"vender_id\": 43,\"unit_id\": \"" + rs.getString("unit_id") + "\",\"vehicle_id\": \"" + rs.getString("vehicle_id") + "\",\"vehicle_type\": \"" + rs.getString("vehicle_type")
//						+ "\",\"vehicle_chassis_no\": \"" + rs.getString("vehicle_chassis_no") + "\",\"vehicle_register_type\": " + rs.getString("vehicle_register_type") + ",\"card_reader\": " + rs.getInt("card_reader") + ",\"province_code\": "
//						+ rs.getInt("province_code") + "}";
//				byte[] encodedBytes = Base64.encodeBase64((byte[]) serverIP.getUserPassMaster().getBytes());
//				String mfUrl = String.valueOf(serverIP.getUrlMesterFile()) + "/masterfile/add";
//				System.out.println("Json : "+json);
//				DefaultHttpClient httpClient = new DefaultHttpClient();
//				HttpPost post = new HttpPost(mfUrl);
//				StringEntity postingString = new StringEntity(json);
//
//				final X509Certificate[] _AcceptedIssuers = new X509Certificate[]{};
//				SSLContext ctx = SSLContext.getInstance("TLS");
//				X509TrustManager tm = new X509TrustManager() {
//					@Override
//					public X509Certificate[] getAcceptedIssuers() {
//						return _AcceptedIssuers;
//					}
//
//					@Override
//					public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
//					}
//
//					@Override
//					public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
//					}
//				};
//				ctx.init(null, new TrustManager[]{tm}, new SecureRandom());
//				SSLSocketFactory ssf = new SSLSocketFactory(ctx, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
//				ClientConnectionManager ccm = httpClient.getConnectionManager();
//				SchemeRegistry sr = ccm.getSchemeRegistry();
//				sr.register(new Scheme("https", 443, (SchemeSocketFactory) ssf));
//				post.setHeader("Authorization", "basic " + new String(encodedBytes));
//				post.addHeader("Content-Type", "application/json");
//				post.setEntity((HttpEntity) postingString);
//				HttpResponse res = null;
////				int test = 200;
//				res = httpClient.execute((HttpUriRequest) post);
//				System.out.println("Response " + res);
			}

			return new ModelAndView("redirect:/member.htm", "status", 200);
		}
		return new ModelAndView("redirect:/");

	}

	public ModelAndView masterfileRemoveFromFile(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (super.chkSession(request)) {
			Connection connect = null;
			Statement stmt = null;
			Connection connect2 = null;
			Statement stmt2 = null;

			String mySqlUrl = serverIP.getMysql();
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection(mySqlUrl);
			stmt = connect.createStatement();

			connect2 = DriverManager.getConnection(mySqlUrl);
			stmt2 = connect2.createStatement();

			String query = "SELECT  `id`, `card_reader`, `distar_sn`, `gps_model`, `imei`, `province_code`, `unit_id`, `vehicle_chassis_no`, `vehicle_id`, `vehicle_register_type`, `vehicle_type`, `customer_name`, `sale_name` \n"
					+ "FROM `distar_dlt`.`excel_import`; ";
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
//				String  update = "update `distar_dlt`.`lty_unitid` set  `unit_id` = '"+rs.getString("unit_id")+"' where `vehicle_id` = '"+rs.getString("imei")+"' ;";
//				System.out.println("update " + update);
//				stmt2.executeUpdate(update);

//				String  masterfileUpdate = "INSERT INTO `distar_dlt`.`master_file` (" + 
//						"  `card_reader`, `gps_model`, `imei`, `province_code`, `unit_id`, `vehicle_chassis_no`, `vehicle_id`, `vehicle_register_type`, `vehicle_type`, `customer_name`, `sale_name`, `dlt_status`" + 
//						")" + 
//						"VALUES" + 
//						"  (" + 
//						"    '1', '"+rs.getString("gps_model")+"', '"+rs.getString("imei")+"', '"+rs.getString("province_code")+"', '"+rs.getString("unit_id")+"', '"+rs.getString("vehicle_chassis_no")+"', '"+rs.getString("vehicle_id")+"', '"+rs.getString("vehicle_register_type")+"', '"+rs.getString("vehicle_type")+"', 'BMTA', 'Zulex', '1'" + 
//						"  );";
//				System.out.println("masterfileUpdate " + masterfileUpdate);
//				stmt2.executeUpdate(masterfileUpdate);

				// Remove
				String masterfileRemove = "DELETE FROM   `distar_dlt`.`master_file` WHERE `unit_id` = '"
						+ rs.getString("unit_id") + "';";
				System.out.println("masterfileRemove " + masterfileRemove);
				stmt2.executeUpdate(masterfileRemove);

				// Remove Masterfile
				byte[] encodedBytes = Base64.encodeBase64((byte[]) serverIP.getUserPassMaster().getBytes());
				String url = String.valueOf(serverIP.getUrlMesterFile()) + "/masterfile/rmvByUnit/"
						+ rs.getString("unit_id");
				DefaultHttpClient httpClient = new DefaultHttpClient();
				HttpDelete delete = new HttpDelete(url);

				final X509Certificate[] _AcceptedIssuers = new X509Certificate[] {};
				SSLContext ctx = SSLContext.getInstance("TLS");
				X509TrustManager tm = new X509TrustManager() {
					@Override
					public X509Certificate[] getAcceptedIssuers() {
						return _AcceptedIssuers;
					}

					@Override
					public void checkServerTrusted(X509Certificate[] chain, String authType)
							throws CertificateException {
					}

					@Override
					public void checkClientTrusted(X509Certificate[] chain, String authType)
							throws CertificateException {
					}
				};
				ctx.init(null, new TrustManager[] { tm }, new SecureRandom());
				SSLSocketFactory ssf = new SSLSocketFactory(ctx, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
				ClientConnectionManager ccm = httpClient.getConnectionManager();
				SchemeRegistry sr = ccm.getSchemeRegistry();
				sr.register(new Scheme("https", 443, (SchemeSocketFactory) ssf));

				delete.setHeader("Authorization", "basic " + new String(encodedBytes));
				delete.addHeader("Content-Type", "application/json");
				HttpResponse res = httpClient.execute((HttpUriRequest) delete);

				System.out.println("unitId : " + rs.getString("unit_id"));
				System.out.println("StatusCode : " + res.getStatusLine().getStatusCode());

				// Import Masterfiel
//				String json = "{\"vender_id\": 43,\"unit_id\": \"" + rs.getString("unit_id") + "\",\"vehicle_id\": \"" + rs.getString("vehicle_id") + "\",\"vehicle_type\": \"" + rs.getString("vehicle_type")
//						+ "\",\"vehicle_chassis_no\": \"" + rs.getString("vehicle_chassis_no") + "\",\"vehicle_register_type\": " + rs.getString("vehicle_register_type") + ",\"card_reader\": " + rs.getInt("card_reader") + ",\"province_code\": "
//						+ rs.getInt("province_code") + "}";
//				byte[] encodedBytes = Base64.encodeBase64((byte[]) serverIP.getUserPassMaster().getBytes());
//				String mfUrl = String.valueOf(serverIP.getUrlMesterFile()) + "/masterfile/add";
//				System.out.println("Json : "+json);
//				DefaultHttpClient httpClient = new DefaultHttpClient();
//				HttpPost post = new HttpPost(mfUrl);
//				StringEntity postingString = new StringEntity(json);
//
//				final X509Certificate[] _AcceptedIssuers = new X509Certificate[]{};
//				SSLContext ctx = SSLContext.getInstance("TLS");
//				X509TrustManager tm = new X509TrustManager() {
//					@Override
//					public X509Certificate[] getAcceptedIssuers() {
//						return _AcceptedIssuers;
//					}
//
//					@Override
//					public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
//					}
//
//					@Override
//					public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
//					}
//				};
//				ctx.init(null, new TrustManager[]{tm}, new SecureRandom());
//				SSLSocketFactory ssf = new SSLSocketFactory(ctx, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
//				ClientConnectionManager ccm = httpClient.getConnectionManager();
//				SchemeRegistry sr = ccm.getSchemeRegistry();
//				sr.register(new Scheme("https", 443, (SchemeSocketFactory) ssf));
//				post.setHeader("Authorization", "basic " + new String(encodedBytes));
//				post.addHeader("Content-Type", "application/json");
//				post.setEntity((HttpEntity) postingString);
//				HttpResponse res = null;
////				int test = 200;
//				res = httpClient.execute((HttpUriRequest) post);
//				System.out.println("Response " + res);
			}

			return new ModelAndView("redirect:/member.htm", "status", 200);
		}
		return new ModelAndView("redirect:/");

	}

}
