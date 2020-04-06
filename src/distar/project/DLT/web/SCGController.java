
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
import java.util.Timer;
import java.util.TimerTask;

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
import com.google.gson.Gson;

import distar.project.DLT.domain.APIMasterfile;
import distar.project.DLT.domain.MasterFile;
import distar.project.DLT.domain.ProvinceDLT;
import distar.project.DLT.domain.VehicleRegisterType;
import distar.project.DLT.repository.APIMasterfileDAO;
import distar.project.DLT.repository.ProvinceDLTDAO;
import distar.project.DLT.repository.VehicleRegisterTypeDAO;
import distar.project.DLT.service.MasterFileForm;
import distar.project.service.ServerIP;
import distar.project.web.UsableController;

public class SCGController extends UsableController {

	private ProvinceDLTDAO provinceDLTDAO;
	private VehicleRegisterTypeDAO vehicleRegisterTypeDAO;
	private APIMasterfileDAO apiMasterfileDAO;
	private ServerIP serverIP;

	public void setApiMasterfileDAO(APIMasterfileDAO apiMasterfileDAO) {
		this.apiMasterfileDAO = apiMasterfileDAO;
	}

	public void setServerIP(ServerIP serverIP) {
		this.serverIP = serverIP;
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

			SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
			Date installDate = new Date();
			String newDateInstall = null;

			MasterFileForm masterFileForm = new MasterFileForm();
			masterFileForm.setCardReader(1 + "");
			masterFileForm.setDltStatus(1 + "");
			// masterFileForm.setDateInstall(dateformat.format(installDate));

			try {
				newDateInstall = dateformat.format(installDate);
				// System.out.println("newDateInstall :
				// "+dateformat.format(installDate));

				String[] dateIns = dateformat.format(installDate).split("-");
				int year = Integer.parseInt(dateIns[0]);
				if (dateIns[0].startsWith("20")) {

					newDateInstall = year + "-" + dateIns[1] + "-" + dateIns[2];

				} else if (dateIns[0].startsWith("14")) {
					year += 543;
					newDateInstall = year + "-" + dateIns[1] + "-" + dateIns[2];

				} else if (dateIns[0].startsWith("25")) {
					year -= 543;
					newDateInstall = year + "-" + dateIns[1] + "-" + dateIns[2];
				}

				masterFileForm.setDateInstall(newDateInstall);

			} catch (Exception e) {
				// TODO: handle exception
				System.out.println(e.getMessage());
				masterFileForm.setDateInstall(newDateInstall);
			}

			List<ProvinceDLT> listProvince = provinceDLTDAO.orderByProvinceName();
			List<VehicleRegisterType> listVehicle = vehicleRegisterTypeDAO.orderByVehicleRegisterType();

			ModelMap modelMap = new ModelMap();
			modelMap.addAttribute("masterFileForm", masterFileForm);
			modelMap.addAttribute("listProvince", listProvince);
			modelMap.addAttribute("listVehicle", listVehicle);

			return new ModelAndView("/thaipost/addMasterFileForm", modelMap);
		}
		return new ModelAndView("redirect:/");

	}

	public ModelAndView editMasterFileForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (super.chkSession(request)) {

			String unitId = request.getParameter("unitId").trim();
			System.out.println("unitId : \"" + unitId + "\"");
			APIMasterfile masterFile = null;
			try {
				masterFile = apiMasterfileDAO.findByUnitId(unitId);

				MasterFileForm masterFileForm = new MasterFileForm();
				List<ProvinceDLT> listProvince = provinceDLTDAO.orderByProvinceName();
				List<VehicleRegisterType> listVehicle = vehicleRegisterTypeDAO.orderByVehicleRegisterType();

				String cardReader = "0";
				String dltStatus = "0";
				if (masterFile.getCardReader() > 0) {
					cardReader = "1";
				}
				if (masterFile.getDltStatus() > 0) {
					dltStatus = "1";
				}

				System.out.println("cardReader : " + cardReader + " dltStatus : " + dltStatus);

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

//				System.out.println("getVehicleRegisterType : "+masterFile.getVehicleRegisterType().getVehicleRegisterType());

				SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
				Date installDate = null;
				String newDateInstall = null;

				try {
					// System.out.println("masterFile.getInstallDate() :
					// "+masterFile.getInstallDate());

					// System.out.println("year :
					// "+masCterFile.getInstallDate().toString());

					installDate = masterFile.getInstallDate();

					String[] dateIns = masterFile.getInstallDate().toString().split("-");

					int year = Integer.parseInt(dateIns[0]);

					if (dateIns[0].startsWith("20")) {

						// System.out.println("year 20 : "+dateIns[0]);

						newDateInstall = year + "-" + dateIns[1] + "-" + dateIns[2];
						installDate = dateformat.parse(newDateInstall);

						// System.out.println("installDate 20: "+installDate);

					} else if (dateIns[0].startsWith("14")) {

						year += 543;
						newDateInstall = year + "-" + dateIns[1] + "-" + dateIns[2];
						installDate = dateformat.parse(newDateInstall);

						// System.out.println("installDate 14: "+installDate);

					} else if (dateIns[0].startsWith("25")) {

						// System.out.println("year 14 : "+dateIns[0]);

						year -= 543;
						newDateInstall = year + "-" + dateIns[1] + "-" + dateIns[2];
						installDate = dateformat.parse(newDateInstall);

						// System.out.println("installDate 25: "+installDate);

					}

				} catch (Exception e) {
					// TODO: handle exception
					installDate = masterFile.getInstallDate();
				}

				try {
					masterFileForm.setDateInstall(dateformat.format(installDate));
				} catch (Exception e) {
					masterFileForm.setDateInstall(null);
				}

				ModelMap modelMap = new ModelMap();
				modelMap.addAttribute("masterFileForm", masterFileForm);
				modelMap.addAttribute("listProvince", listProvince);
				modelMap.addAttribute("listVehicle", listVehicle);

				return new ModelAndView("/thaipost/addMasterFileForm", modelMap);
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

				return new ModelAndView("/scg/addMasterFileForm", modelMap);
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

			String gpsModel = "2370001";
			String vehicleId = masterFileForm.getVehicleId(); // License plate
			String vehicleType = masterFileForm.getVehicleType().toUpperCase(); // Vehicle
																				// brand
			String vehicleChassisNo = masterFileForm.getVehicleChassisNo().toUpperCase();
			Long vehicleRegisterType = masterFileForm.getVehicleRegisterType();
			Long provinceCode = masterFileForm.getProvinceCode();

			int cardReader = 0;
			System.out.println("CardReader : " + masterFileForm.getCardReader());
			try {
				if (masterFileForm.getCardReader().equals("on")) {
					cardReader = 1;
				}
			} catch (Exception e) {
				cardReader = 0;
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

			String unitId = gpsModel + initIMEI + imei.trim();
			unitId = unitId.trim();
			unitId = unitId.replace(" ", "");

			// System.out.println("initVehicleId : " + initVehicleId+vehicleId);
			System.out.println("SCG unitId Add: " + unitId);

			String json = "{\"vender_id\": 237,\"unit_id\": \"" + unitId + "\",\"vehicle_id\": \"" + initVehicleId
					+ vehicleId + "\",\"vehicle_type\": \"" + vehicleType + "\",\"vehicle_chassis_no\": \""
					+ vehicleChassisNo + "\",\"vehicle_register_type\": " + vehicleRegisterType + ",\"card_reader\": "
					+ cardReader + ",\"province_code\": " + provinceCode + "}";
			byte[] encodedBytes = Base64.encodeBase64((byte[]) "distar:a9j72k".getBytes());
			String url = "http://scgtms.gpsgatewayhub.com/masterfile/add";

			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost post = new HttpPost(url);
			StringEntity postingString = new StringEntity(json , "UTF-8");

			System.out.println("encodedBytes : " + new String(encodedBytes));

			post.setHeader("Authorization", "Basic " + new String(encodedBytes));
			post.addHeader("Content-Type", "application/json");
			post.setHeader("Content-Type", "text/plain; charset=UTF-8");
			post.setEntity((HttpEntity) postingString );
//			post.setEntity(new StringEntity(json, "UTF-8"));
			
			HttpResponse res = httpClient.execute((HttpUriRequest) post);
//			HttpResponse res = null;
			int test = 200;

			System.out.println(json);
//			System.out.println("status code : " + res.getStatusLine());
//			String responseAsString = EntityUtils.toString(res.getEntity());
//			System.out.println("response : " + responseAsString);

			if (test == 200) {

				SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd", lc);
				Date installDate = null;
				String newDateInstall = null;

				if (masterFileForm.getDateInstall().length() > 0) {
					try {
						installDate = dateformat.parse(masterFileForm.getDateInstall());
						// System.out.println("installDate2 :
						// "+dateformat.parse(masterFileForm.getDateInstall()));

						String[] dateIns = masterFileForm.getDateInstall().split("-");
						int year = Integer.parseInt(dateIns[0]);
						if (dateIns[0].startsWith("20")) {

							year += 543;
							newDateInstall = year + "-" + dateIns[1] + "-" + dateIns[2];
							installDate = dateformat.parse(newDateInstall);
							// System.out.println("installDate2 20 : " +
							// installDate);
						} else if (dateIns[0].startsWith("14")) {
							year += 1086;
							newDateInstall = year + "-" + dateIns[1] + "-" + dateIns[2];
							installDate = dateformat.parse(newDateInstall);
							// System.out.println("installDate2 14 : " +
							// installDate);
						} else if (dateIns[0].startsWith("25")) {
							// year -= 543;
							newDateInstall = year + "-" + dateIns[1] + "-" + dateIns[2];
							installDate = dateformat.parse(newDateInstall);
							// System.out.println("installDate2 25: " +
							// installDate);
						}

					} catch (Exception e) {
						// TODO: handle exception

						installDate = dateformat.parse(masterFileForm.getDateInstall());
						// System.out.println("installDate2 exception: " +
						// e.getMessage() + " installDate : "+installDate);
					}
				} else {
					installDate = dateformat.parse(dateformat.format(new Date()));
				}

				Connection connect;
				Statement stmt;

				String uri = serverIP.getMysql();
				Class.forName("com.mysql.jdbc.Driver");
				connect = DriverManager.getConnection(uri);
				stmt = connect.createStatement();

				// Add new master file

				APIMasterfile masterFile = apiMasterfileDAO.findByIMEI(imei);
				if (masterFile == null) {
					APIMasterfile mf = new APIMasterfile();

					mf.setImei(imei);
					mf.setVehicleRegisterType(vehicleRegisterTypeDAO.findById(vehicleRegisterType));
					mf.setProvinceCode(provinceDLTDAO.findById(provinceCode));
					mf.setGpsModel(gpsModel);
					mf.setVehicleId(initVehicleId + vehicleId);
					mf.setVehicleType(vehicleType);
					mf.setUnitId(gpsModel + initIMEI + imei);
					mf.setVehicleChassisNo(vehicleChassisNo);
					mf.setCardReader(cardReader);
					mf.setCustomerName(customerName);
					mf.setSaleName(saleName);
					mf.setDateCreated(new Date());
					mf.setUserCreate(userLogin);
					mf.setInstallDate(installDate);
					mf.setRemark("SCG_API");
					apiMasterfileDAO.persist(mf);

					String query = "UPDATE api_masterfile SET province_code = '" + provinceCode
							+ "',vehicle_register_type = '" + vehicleRegisterType + "' WHERE unit_id = '" + gpsModel
							+ initIMEI + imei + "'";

					// System.out.println("query : "+query);
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
					// masterFile.setUnitId(gpsModel + initIMEI + imei);
					masterFile.setVehicleChassisNo(vehicleChassisNo);
					masterFile.setCardReader(cardReader);
					masterFile.setCustomerName(customerName);
					masterFile.setSaleName(saleName);
					masterFile.setDateUpdated(new Date());
					masterFile.setUserUpdate(userLogin);
					masterFile.setInstallDate(installDate);
					masterFile.setRemark(masterFileForm.getRemark());
					apiMasterfileDAO.merge(masterFile);

					String query = "UPDATE api_masterfile SET province_code = '" + provinceCode
							+ "',vehicle_register_type = '" + vehicleRegisterType + "' WHERE unit_id = '" + gpsModel
							+ initIMEI + imei + "'";
					// System.out.println("query : "+query);
					stmt.executeUpdate(query);

					System.out.println("Update MasterFile : " + provinceDLTDAO.findById(provinceCode).getProvinceName()
							+ " : " + provinceCode);
				}

			}
			// return new ModelAndView("redirect:/member.htm", "status", 200);

			return new ModelAndView("redirect:/member.htm", "status", test);
		}
		return new ModelAndView("redirect:/");

	}

	public ModelAndView rmvMasterFileForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (super.chkSession(request)) {
			MasterFileForm masterFileForm = new MasterFileForm();

			masterFileForm.setStatus(1);

			ModelMap modelMap = new ModelMap();
			modelMap.addAttribute("masterFileForm", masterFileForm);

			return new ModelAndView("/thaipost/rmvMasterFileForm", modelMap);
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

			byte[] encodedBytes = Base64.encodeBase64((byte[]) "distar:a9j72k".getBytes());
			String url = "http://scgtms.gpsgatewayhub.com/masterfile/rmvByUnit/" + unitId;
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
			// sr.register(new Scheme("https", 443, (SchemeSocketFactory) ssf));

			delete.setHeader("Authorization", "basic " + new String(encodedBytes));
			delete.addHeader("Content-Type", "application/json");
			HttpResponse res = httpClient.execute((HttpUriRequest) delete);
			System.out.println("unitId : " + unitId);

			System.out.println("status code : " + res);
			String responseAsString = EntityUtils.toString(res.getEntity());
			System.out.println("response : " + responseAsString);

			return new ModelAndView("redirect:/member.htm", "status", res.getStatusLine().getStatusCode());
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

			byte[] encodedBytes = Base64.encodeBase64((byte[]) "distar:a9j72k".getBytes());
			String url = "http://scgtms.gpsgatewayhub.com/masterfile/getList/0/10000";
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpGet get = new HttpGet(url);

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

			get.setHeader("Authorization", "Basic " + new String(encodedBytes));
			get.addHeader("Content-Type", "application/json");
			get.addHeader("charset", "utf-8 ");
			HttpResponse res = httpClient.execute((HttpUriRequest) get);
			BufferedReader rd = new BufferedReader(new InputStreamReader(res.getEntity().getContent()));
			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
//			TimeZone tz = TimeZone.getTimeZone("GMT+0700");

			Locale lc = new Locale("en", "EN");
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", lc);
			SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", lc);
			
//			df.setTimeZone(tz);
			
			List<List<Object>> table = new ArrayList<List<Object>>();
			List<Object> row = new ArrayList<Object>();

			Date newDate = new Date();
			
			int i = 1;
			try {
				JsonNode arrNode = new ObjectMapper().readTree(result.toString()).get("results");
				if (arrNode.isArray()) {

					for (JsonNode objNode : arrNode) {
						String dateUpdate = "";
						String lastupdate = objNode.get("last_update").getTextValue().replace("Z+00:00", "+0000");
						
						try {
//							System.out.println("newDate : " + (df2.format(newDate)));
//							System.out.println("Date : " + (df.format(df2.parse(lastupdate))));
							dateUpdate = df.format(df2.parse(lastupdate));
							
						}catch (Exception e) {
							// TODO: handle exception
							dateUpdate = "No realtime data";
//							System.out.println("Exception : " + e.getMessage());
						}
						byte[] vehicle_id  = objNode.get("vehicle_id").toString().getBytes();
						
						//System.out.println("ทดสอบภาษาไทย : " + new String(vehicle_id,"UTF-8"));
						
						row.add(objNode.get("unit_id").getTextValue());
						row.add(objNode.get("vehicle_chassis_no").getTextValue());
						row.add(new String(vehicle_id,"UTF-8").replace("\"", ""));
						row.add(objNode.get("vehicle_type").getTextValue());
						row.add(objNode.get("vehicle_register_type"));
						row.add(objNode.get("province_code"));
						row.add(dateUpdate);
						row.add(i);
						
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

			return new ModelAndView("SCG/listMasterFile", modelMap);
//		}
//		return new ModelAndView("redirect:/");

	}

	
}
