
package distar.project.DLT.web;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
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
import org.apache.taglibs.standard.tag.common.fmt.SetTimeZoneSupport;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import com.distar.dtwr.company.domain.User;
import com.google.gson.Gson;

import distar.project.DLT.domain.ExcelImport;
import distar.project.DLT.domain.LTYUnitId;
import distar.project.DLT.domain.MALog;
import distar.project.DLT.domain.MasterFile;
import distar.project.DLT.repository.ExcelImportDAO;
import distar.project.DLT.repository.LTYUnitIdDAO;
import distar.project.DLT.repository.MALogDAO;
import distar.project.DLT.repository.MasterFileDAO;
import distar.project.DLT.service.GpsBackupForm;
import distar.project.DLT.service.MasterFileForm;
import distar.project.DLT.service.SearchForm;
import distar.project.service.ServerIP;
import distar.project.web.UsableController;

public class RealtimeController extends UsableController {
	private MasterFileDAO masterFileDAO;
	private ExcelImportDAO excelImportDAO;
	private ServerIP serverIP;
	private LTYUnitIdDAO ltyUnitIdDAO;
	private MALogDAO maLogDAO;

	public void setMaLogDAO(MALogDAO maLogDAO) {
		this.maLogDAO = maLogDAO;
	}

	public void setLtyUnitIdDAO(LTYUnitIdDAO ltyUnitIdDAO) {
		this.ltyUnitIdDAO = ltyUnitIdDAO;
	}

	public void setMasterFileDAO(MasterFileDAO masterFileDAO) {
		this.masterFileDAO = masterFileDAO;
	}

	public void setServerIP(ServerIP serverIP) {
		this.serverIP = serverIP;
	}

	public void setExcelImportDAO(ExcelImportDAO excelImportDAO) {
		this.excelImportDAO = excelImportDAO;
	}

	public ModelAndView defaultRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return new ModelAndView("redirect:/");
	}

	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) throws Exception {

		response.setContentType("application/json");
		// return new ModelAndView("status", "status", status);
		return new ModelAndView("home");
	}

	public ModelAndView addRealTimeTG(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String benz = null;
		benz = request.getParameter("benz");
		System.out.println("Benz : " + benz);

		if (super.chkSession(request) || benz.equals("OK")) {
			Timer timer = new Timer();
			int initialDelay = 1000;
			int period = 30000;
			TimerTask task = new TimerTask() {
				Connection connect;
				Statement stmt;

				String json;
				ArrayList<String> data;

				Locale lc = new Locale("en", "EN");
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", lc);
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'", lc);

				
				public void run() {
					connect = null;
					stmt = null;
					json = null;
					data = new ArrayList();

					Date d = new Date();
					// timer send gps data to DLT

					try {
//						String url = "jdbc:mysql://localhost:3306/distar_dlt?characterEncoding=UTF-8&user=distardev&password=p@ssw0rd@distar";

						 String url = serverIP.getMysql();
						Class.forName("com.mysql.jdbc.Driver");
						connect = DriverManager.getConnection(url);
						stmt = connect.createStatement();

						if (connect != null) {

							// System.out.println("Connected!!" +
							// formatter.format(new Date()));

							String initLic = "";
							String driverId = "";
							String license = "";
							String unitId = "";
							String lat = "";
							String lon = "";
							String utc_ts = "2016-01-18T11:00:13.939Z";
							String recv_utc = "2016-01-18T11:00:15.939Z";
							String imei_id = "";
							String rfid = "";
							String driverLic = "";
							String gsm_station_id = "";
							int course = 0;
							int altitude = 0;
							int gps_valid = 0;
							int satellite_number = 0;
							int hdop = 0;
							int gsm_signal = 0;
							int engine_status = 0;
							int speed = 0;
							int max_speed = 0;
							boolean powerStatus = false;
							boolean bat = false;
							String extBat = "0";
							String rfidTime = null;
							int ext_power_status = 0;

							int loginStatus = 0;

							TimeZone tz = TimeZone.getTimeZone("GMT-07:00");
							TimeZone tz2 = TimeZone.getTimeZone("GMT+00:00");

							Date dateNow = new Date();

							SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", lc);
							SimpleDateFormat dateformat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", lc);
							df.setTimeZone(tz2);
							// dateformat2.setTimeZone(tz2);

							Date sDate = new Date(System.currentTimeMillis() - 300 * 1000);
							String selDate = dateformat.format(sDate);

							String query = "SELECT `device_id`, `azimuth`, `gps_time`, `receive_time`, `latitude`, `longitude`, `speed`, `gsm`, `sat_number`, `battery`,`car_battery`, `unit_id`, `card_type`, `license_id`, `driver_name`, `swipe_time`, `login_status`, `engine_status`, `position_status`, `online_status`, `ext_bat_status` FROM  `tg_gps_last` WHERE `unit_id` IS NOT NULL AND `unit_id` NOT LIKE '' AND `gps_time` > '"
									+ selDate + "'  ";
							ResultSet rs = stmt.executeQuery(query);
							while (rs.next()) {

								// try {
								// loginStatus = rs.getInt("login_status");
								// }
								// catch (Exception e) {
								// loginStatus = 0;
								// }

								driverId = rs.getString("license_id");
								gps_valid = rs.getInt("position_status");
								extBat = rs.getString("car_battery");

								ext_power_status = rs.getInt("ext_bat_status");
								satellite_number = rs.getInt("sat_number");
								// hdop = rs.getInt("hdop");
								gsm_signal = rs.getInt("gsm");
								course = rs.getInt("azimuth");
								// gsm_station_id =
								// rs.getString("gsm_station_id");
								lat = rs.getString("latitude");
								lon = rs.getString("longitude");
								speed = rs.getInt("speed");
								utc_ts = df.format(formatter.parse(rs.getString("gps_time"))).trim();
								recv_utc = df.format(formatter.parse(rs.getString("receive_time"))).trim();
								unitId = rs.getString("unit_id");
								imei_id = rs.getString("device_id");
								String lastLic = "0000000000000000000000000000000000000000";

								initLic = "00000000000000000000000000000000000000000000000000000000";

								String newLic = "";
								try {
									newLic = initLic + driverId + lastLic;
									driverLic = driverId;
									// license = newLic;
									license = newLic.substring(0, 110);
									// System.out.println(license);

								}
								catch (Exception e) {
									// TODO: handle exception
									license = "";
									driverLic = "";
								}

								if (rs.getInt("login_status") == 0 || rs.getInt("engine_status") == 0) {
									license = "";
									driverLic = "";
								}
								// license = newLic.substring(0, 110);

								json = "{ \"driver_id\": \"" + driverLic + "\", \"unit_id\": \"" + unitId.trim() + "\", \"seq\": 0, \"utc_ts\": \"" + utc_ts + "\", \"recv_utc_ts\": \"" + recv_utc
										+ "\", \"lat\": " + lat + ", \"lon\": " + lon + ", \"alt\": " + altitude + ", \"speed\": " + speed + ", \"engine_status\": " + engine_status + ", \"fix\": "
										+ gps_valid + ", \"license\": \"" + license + "\", \"course\": " + course + ", \"hdop\": " + hdop + ", \"num_sats\": " + satellite_number
										+ ", \"gsm_cell\": 0, \"gsm_loc\": 0, \"gsm_rssi\": " + gsm_signal + ", \"mileage\": " + "0" + ", \"ext_power_status\": " + ext_power_status
										+ ", \"ext_power\": " + extBat + ", \"high_acc_count\": null, \"high_de_acc_count\": 0, \"over_speed_count\": 0, \"max_speed\": " + max_speed + " } ";

								System.out.println("json : " + json);

								String timeUpdate = dateformat2.format(dateNow);

								data.add(json);
								// }

							}
							connect.close();
							rs.close();
						}

						boolean tmpMaxSpeed = false;
						byte[] encodedBytes = Base64.encodeBase64((byte[]) serverIP.getUserPassGPS().getBytes());
						DefaultHttpClient httpClient = new DefaultHttpClient();
						HttpPost post = new HttpPost(String.valueOf(serverIP.getUrlRealTime()) + "/gps/add/locations");

						String jsonInit = "{ \"vender_id\": \"043\", \"locations_count\": " + data.size() + ", \"locations\": ";
						Gson gson = new Gson();
						String jsonNew = String.valueOf(jsonInit) + gson.toJson(data).replace("\\", "").replace("[\"", "[").replace("\"]", "]").replace("\",\"", ",") + "}";
						System.out.println("jsonNew : " + jsonNew);
						StringEntity postingString = new StringEntity(jsonNew);
						HttpResponse res = null;
						post.setHeader("Authorization", "basic " + new String(encodedBytes));
						post.addHeader("Content-Type", "application/json");

						post.setEntity((HttpEntity) postingString);
						res = httpClient.execute((HttpUriRequest) post);

						String responseAsString = EntityUtils.toString(res.getEntity());
						System.out.println("TG response : " + responseAsString);

						System.out.println("TG Status code : " + res.getStatusLine() + " Time : " + new Date());

						connect.close();
						stmt.close();

						data.clear();
					}
					// }
					catch (Exception e) {
						System.out.println("DB connection fail!!");
						e.printStackTrace();
						data.clear();
					}

				}
			};
			timer.scheduleAtFixedRate((TimerTask) task, initialDelay, (long) period);
			return new ModelAndView("redirect:/member.htm", "status", "100");

		}
		return new ModelAndView("redirect:/");
	}

	public ModelAndView addRealTimeDTK3G(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String benz = null;
		benz = request.getParameter("benz");
		System.out.println("Benz : " + benz);

		if (super.chkSession(request) || benz.equals("OK")) {
			Timer timer = new Timer();
			int initialDelay = 1000;
			int period = 45000;
			TimerTask task = new TimerTask() {
				
				Connection connect;
				Statement stmt;

				String json;
				ArrayList<String> data;
				String tmp_utc_ts;
				String tmp_recv_utc;
				// ArrayList<String> listimei = new ArrayList();
				List<String> listunitId ;
				List<String> updateDB ;

				Locale lc = new Locale("en", "EN");
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", lc);
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'", lc);
				
				
				
				public void run() {
					connect = null;
					stmt = null;
					json = null;
					data = new ArrayList();
					updateDB = new ArrayList();
					listunitId = new ArrayList<String>();
					tmp_utc_ts = null;
					tmp_recv_utc = null;

					Date d = new Date();
					// timer send gps data to DLT
					
					
					SimpleDateFormat dateformat3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					TimeZone tz3 = TimeZone.getTimeZone("GMT+07:00");

					dateformat3.setTimeZone(tz3);
					Date dateNow3 = new Date();
					
					String updateIni = "UPDATE `distar_dlt`.`master_file` SET `dlt_update_time` = '" + dateformat3.format(dateNow3) + "' WHERE `imei` IN (";


					try {
						String url = serverIP.getTrackingServer();
						Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
						connect = DriverManager.getConnection(url);
						stmt = connect.createStatement();
						if (connect != null) {

							// System.out.println("DTK-3G100T realtime start !!"
							// + formatter.format(new Date()));

							String initLic = "";
							Object driverId = "";
							String license = "";
							String unitId = "";
							String lat = "";
							String lon = "";
							String utc_ts = "2016-01-18T11:00:13.939Z";
							String recv_utc = "2016-01-18T11:00:15.939Z";
							String imei_id = "";
							String rfid = "";
							String driverLic = "";
							String gsm_station_id = "";
							int course = 0;
							int altitude = 0;
							int gps_valid = 0;
							int satellite_number = 0;
							int hdop = 0;
							int gsm_signal = 0;
							int engine_status = 0;
							int speed = 0;
							int max_speed = 0;
							boolean powerStatus = false;
							boolean bat = false;
							int extBat = 0;
							String ad5 = "";
							String rfidTime = null;
							int ext_power_status = 0;
							String gpsLastRfid = "";
							String gpsLastAlarmId = "";
							String alarmHistoryId = "";
							int loginStatus = 0;

							SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", lc);
							TimeZone tmz = TimeZone.getTimeZone("GMT+00:00");
							df.setTimeZone(tmz);

							Date sDate = new Date(System.currentTimeMillis() - 300 * 1000);
							Date rDate = new Date(System.currentTimeMillis() + 300 * 1000);
							String selDate = df.format(sDate);
							String selRDate = df.format(rDate);

							String query = "SELECT sn_imei_id ,l_datetime ,r_datetime ,latitude,longitude,speed ,altitude,gps_valid,hdop ,satellite_number,gsm_signal,unit_id,gsm_station_id,extend_str,azimuth,license_rfid,alarm_history_id,ad4,ad5,login_status,engine_status FROM dbo.GPS_Last WITH (NOLOCK) WHERE unit_id IS NOT NULL and l_datetime > '"
									+ selDate + "' and l_datetime < '" + selRDate + "'";
							ResultSet rs = stmt.executeQuery(query);
							// System.out.println("query : "+query);

							while (rs.next()) {

								try {
									loginStatus = rs.getInt("login_status");
								}
								catch (Exception e) {
									loginStatus = 0;
								}

								// System.out.println("loginStatus :
								// "+loginStatus);

								gpsLastRfid = rs.getString("license_rfid");
								gpsLastAlarmId = rs.getString("alarm_history_id");
								altitude = rs.getInt("altitude");
								gps_valid = rs.getInt("gps_valid");
								ad5 = rs.getString("ad5").trim();
								if (ad5.length() == 6) {
									extBat = Integer.parseInt(ad5.toString().substring(0, 2));
								}
								else if (ad5.length() == 5) {
									extBat = Integer.parseInt(ad5.toString().substring(0, 1));
								}
								ext_power_status = extBat >= 9 ? 1 : 0;
								satellite_number = rs.getInt("satellite_number");
								hdop = rs.getInt("hdop");
								gsm_signal = rs.getInt("gsm_signal");
								course = rs.getInt("azimuth");
								gsm_station_id = rs.getString("gsm_station_id");
								lat = rs.getString("latitude");
								lon = rs.getString("longitude");
								speed = rs.getInt("speed");
								utc_ts = df.format(formatter.parse(rs.getString("l_datetime"))).trim();
								recv_utc = df.format(formatter.parse(rs.getString("r_datetime"))).trim();
								unitId = rs.getString("unit_id");
								imei_id = rs.getString("sn_imei_id");
								String lastLic = "00000000000000";

								initLic = "00000000000000000000000000000000000000000000000000000000";

								listunitId.add(unitId);

								String newLic = "";
								try {
									newLic = initLic + gpsLastRfid.replace(" ", "0") + lastLic;
									driverLic = gpsLastRfid.replace(" ", "");
									// license = newLic;
									license = newLic.substring(0, 110);
									// System.out.println(license);

								}
								catch (Exception e) {
									// TODO: handle exception
									license = "";
									driverLic = "";
								}

								// license = newLic.substring(0, 110);

								TimeZone tz = TimeZone.getTimeZone("GMT+00:00");
								TimeZone tz2 = TimeZone.getTimeZone("GMT+07:00");

								Date dateNow = new Date();

								SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", lc);
								SimpleDateFormat dateformat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", lc);
								dateformat.setTimeZone(tz);
								dateformat2.setTimeZone(tz2);

								String timeTracker = dateformat2.format(df.parse(utc_ts));
								String timeReceive = dateformat2.format(df.parse(recv_utc));
								String timeNow = dateformat.format(dateNow);

								long getTT = dateformat.parse(timeTracker).getTime();
								long getTR = dateformat.parse(timeReceive).getTime();
								long getTN = dateformat.parse(timeNow).getTime();
								long min = 60000;

								// System.out.println(timeNow);
								// System.out.println("new : "+selDate);

								if (rs.getInt("engine_status") == 0 || rs.getInt("login_status") == 0) {
									license = "";
									driverLic = "";
								}

								json = "{ \"driver_id\": \"" + driverLic + "\", \"unit_id\": \"" + unitId.trim() + "\", \"seq\": 0, \"utc_ts\": \"" + dateformat2.format(new Date(getTT))
										+ "\", \"recv_utc_ts\": \"" + dateformat2.format(new Date(getTR)) + "\", \"lat\": " + lat + ", \"lon\": " + lon + ", \"alt\": " + altitude + ", \"speed\": "
										+ speed + ", \"engine_status\": " + engine_status + ", \"fix\": " + gps_valid + ", \"license\": \"" + license + "\", \"course\": " + course + ", \"hdop\": "
										+ hdop + ", \"num_sats\": " + satellite_number + ", \"gsm_cell\": 0, \"gsm_loc\": 0, \"gsm_rssi\": " + gsm_signal + ", \"mileage\": " + "0"
										+ ", \"ext_power_status\": " + ext_power_status + ", \"ext_power\": " + extBat
										+ ", \"high_acc_count\": null, \"high_de_acc_count\": 0, \"over_speed_count\": 0, \"max_speed\": " + max_speed + " } ";

								data.add(json);
								// }

							}
							connect.close();
							rs.close();
						}

						boolean tmpMaxSpeed = false;
						byte[] encodedBytes = Base64.encodeBase64((byte[]) serverIP.getUserPassGPS().getBytes());
						DefaultHttpClient httpClient = new DefaultHttpClient();
						HttpPost post = new HttpPost(String.valueOf(serverIP.getUrlRealTime()) + "/gps/add/locations");

						String jsonInit = "{ \"vender_id\": \"043\", \"locations_count\": " + data.size() + ", \"locations\": ";
						Gson gson = new Gson();
						String jsonNew = String.valueOf(jsonInit) + gson.toJson(data).replace("\\", "").replace("[\"", "[").replace("\"]", "]").replace("\",\"", ",") + "}";
						// System.out.println("jsonNew : "+jsonNew);
						StringEntity postingString = new StringEntity(jsonNew);
						HttpResponse res = null;
						post.setHeader("Authorization", "basic " + new String(encodedBytes));
						post.addHeader("Content-Type", "application/json");
						if (data.size() > 0) {
							post.setEntity((HttpEntity) postingString);
							res = httpClient.execute((HttpUriRequest) post);

							String responseAsString = EntityUtils.toString(res.getEntity());
							System.out.println("DTK3G-100T response : " + responseAsString);

							System.out.println("DTK3G-100T Status code : " + res.getStatusLine() + " Time : " + new Date());

							connect.close();
							stmt.close();

							String uri = serverIP.getMysql();
							Class.forName("com.mysql.jdbc.Driver");
							connect = DriverManager.getConnection(uri);
							stmt = connect.createStatement();

							SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", lc);
							TimeZone tz = TimeZone.getTimeZone("GMT+07:00");

							dateformat.setTimeZone(tz);
							Date dateNow = new Date();
							String timeUpdate = "";

							timeUpdate = dateformat.format(dateNow);

							System.out.print("total : " + listunitId.size() + " timeUpdate : " + timeUpdate);

							try {
								String update = "UPDATE `distar_dlt`.`realtime_status` SET `exception` = '" + String.valueOf(res) + "', `status` = '"
										+ String.valueOf(res.getStatusLine().getStatusCode()) + "', `time_update` = '" + timeUpdate + "', `data_log` = '" + jsonNew + "', `size` = '" + data.size() + "' WHERE `device` = '1';";
								stmt.executeUpdate(update);
								System.out.println(" DB updated : " + timeUpdate);
							}
							catch (Exception e) {
								System.out.println("Exception : " + e.getMessage());
								String sql = "INSERT INTO `distar_dlt`.`realtime_status`  (`device`,`status`,`exception`,`time_update`,`data_log`,`size`) VALUES ('5','"
										+ String.valueOf(res.getStatusLine().getStatusCode()) + "','" + String.valueOf(res) + "','" + timeUpdate + "','" + jsonNew + "','" + data.size()+ "');";
								stmt.executeUpdate(sql);
							}

							Connection connect6;
							Statement stmt6;

							String uri2 = serverIP.getMysql();
							Class.forName("com.mysql.jdbc.Driver");
							connect6 = DriverManager.getConnection(uri);
							stmt6 = connect6.createStatement();

							if (res.getStatusLine().getStatusCode() == 200) {
								System.out.println("listunitId : "+listunitId.size());
								for (String unitId : listunitId) {

									try {

//										String sql = "UPDATE `distar_dlt`.`master_file` SET `dlt_update_time` = '" + timeUpdate + "' WHERE `unit_id` = '" + unitId + "';";
										
										String sql = "'" + unitId + "'";
										updateDB.add(sql);
										
										

//										System.out.println("sql : "+sql);
										
									}
									catch (Exception e) {
//										System.out.println("sql exception : "+ e.getMessage());
									}
								}
								
								String update = updateIni + updateDB.toString().replace("]", "").replace("[", "")+");";
								
								System.out.println("updateDB : "+update);
								
								stmt6.executeUpdate(update);
								connect6.close();
								
							}
							
							listunitId.clear();

							data.clear();
						}
					}
					catch (Exception e) {
						System.out.println("DB connection fail!!");
						e.printStackTrace();
						data.clear();
					}

				}
			};
			timer.scheduleAtFixedRate((TimerTask) task, initialDelay, (long) period);
			return new ModelAndView("redirect:/member.htm", "status", "100");

		}
		return new ModelAndView("redirect:/");
	}

	public ModelAndView addRealTimeDTK3G2(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String benz = null;
		benz = request.getParameter("benz");
		System.out.println("Benz : " + benz);

		if (super.chkSession(request) || benz.equals("OK")) {
			Timer timer = new Timer();
			int initialDelay = 1000;
			int period = 30000;
			TimerTask task = new TimerTask() {
				Connection connect;
				Connection connect2;
				Statement stmt5;
				Statement stmt;
				Statement stmt2;
				Statement stmt3;
				Statement stmt4;
				String json;
				ArrayList<String> data;
				String tmp_utc_ts;
				String tmp_recv_utc;

				Locale lc = new Locale("en", "EN");
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", lc);
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'", lc);

				
				public void run() {
					connect = null;
					stmt = null;
					connect2 = null;
					stmt5 = null;
					stmt2 = null;
					stmt3 = null;
					stmt4 = null;
					json = null;
					data = new ArrayList();
					tmp_utc_ts = null;
					tmp_recv_utc = null;

					Date d = new Date();
					// timer send gps data to DLT

					try {
						String url = serverIP.getTrackingServer();
						Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
						connect = DriverManager.getConnection(url);
						stmt = connect.createStatement();
						stmt2 = connect.createStatement();
						stmt3 = connect.createStatement();
						stmt4 = connect.createStatement();
						if (connect != null) {

							System.out.println("DTK-3G100T realtime2 start !!" + formatter.format(new Date()));

							String initLic = "";
							Object driverId = "";
							String license = "";
							String unitId = "";
							String lat = "";
							String lon = "";
							String utc_ts = "2016-01-18T11:00:13.939Z";
							String recv_utc = "2016-01-18T11:00:15.939Z";
							String imei_id = "";
							String rfid = "";
							String driverLic = "";
							String gsm_station_id = "";
							int course = 0;
							int altitude = 0;
							int gps_valid = 0;
							int satellite_number = 0;
							int hdop = 0;
							int gsm_signal = 0;
							int engine_status = 0;
							int speed = 0;
							int max_speed = 0;
							boolean powerStatus = false;
							boolean bat = false;
							int extBat = 0;
							String ad5 = "";
							String rfidTime = null;
							int ext_power_status = 0;
							String gpsLastRfid = "";
							String gpsLastAlarmId = "";
							String alarmHistoryId = "";
							int loginStatus = 0;

							SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", lc);
							TimeZone tmz = TimeZone.getTimeZone("GMT+00:00");
							df.setTimeZone(tmz);

							Date sDate = new Date(System.currentTimeMillis() - 300 * 1000);
							Date rDate = new Date(System.currentTimeMillis() + 300 * 1000);
							String selDate = df.format(sDate);
							String selRDate = df.format(rDate);

							String query = "SELECT sn_imei_id ,l_datetime ,r_datetime ,latitude,longitude,speed ,altitude,gps_valid,hdop ,satellite_number,gsm_signal,unit_id,gsm_station_id,extend_str,azimuth,license_rfid,alarm_history_id,ad4,ad5,login_status,engine_status FROM dbo.GPS_Last WITH (NOLOCK) WHERE unit_id IS NOT NULL and l_datetime > '"
									+ selDate + "' and l_datetime < '" + selRDate + "'";
							ResultSet rs = stmt.executeQuery(query);
							// System.out.println("query : "+query);

							while (rs.next()) {

								try {
									loginStatus = rs.getInt("login_status");
								}
								catch (Exception e) {
									loginStatus = 0;
								}

								gpsLastRfid = rs.getString("license_rfid");
								gpsLastAlarmId = rs.getString("alarm_history_id");
								altitude = rs.getInt("altitude");
								gps_valid = rs.getInt("gps_valid");
								ad5 = rs.getString("ad5").trim();
								if (ad5.length() == 6) {
									extBat = Integer.parseInt(ad5.toString().substring(0, 2));
								}
								else if (ad5.length() == 5) {
									extBat = Integer.parseInt(ad5.toString().substring(0, 1));
								}
								ext_power_status = extBat >= 9 ? 1 : 0;
								satellite_number = rs.getInt("satellite_number");
								hdop = rs.getInt("hdop");
								gsm_signal = rs.getInt("gsm_signal");
								course = rs.getInt("azimuth");
								gsm_station_id = rs.getString("gsm_station_id");
								lat = rs.getString("latitude");
								lon = rs.getString("longitude");
								speed = rs.getInt("speed");
								utc_ts = df.format(formatter.parse(rs.getString("l_datetime"))).trim();
								recv_utc = df.format(formatter.parse(rs.getString("r_datetime"))).trim();
								unitId = rs.getString("unit_id");
								imei_id = rs.getString("sn_imei_id");
								engine_status = rs.getInt("engine_status");
								String lastLic = "00000000000000";

								initLic = "00000000000000000000000000000000000000000000000000000000";

								String newLic = "";
								try {
									newLic = initLic + gpsLastRfid.replace(" ", "0") + lastLic;
									driverLic = gpsLastRfid.replace(" ", "");
									// license = newLic;
									license = newLic.substring(0, 110);
									// System.out.println(license);

								}
								catch (Exception e) {
									// TODO: handle exception
//									System.out.println("Imei :"+imei_id+" gpsLastRfid : "+gpsLastRfid);
									license = "";
									driverLic = "";
								}

								TimeZone tz = TimeZone.getTimeZone("GMT+00:00");
								TimeZone tz2 = TimeZone.getTimeZone("GMT+07:00");

								Date dateNow = new Date();

								SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", lc);
								SimpleDateFormat dateformat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", lc);
								dateformat.setTimeZone(tz);
								dateformat2.setTimeZone(tz2);

								String timeTracker = dateformat2.format(df.parse(utc_ts));
								String timeReceive = dateformat2.format(df.parse(recv_utc));
								String timeNow = dateformat.format(dateNow);

								long getTT = dateformat.parse(timeTracker).getTime();
								long getTR = dateformat.parse(timeReceive).getTime();
								long getTN = dateformat.parse(timeNow).getTime();
								long min = 60000;

								if (rs.getInt("engine_status") == 0 || rs.getInt("login_status") == 0) {
									license = "";
									driverLic = "";
								}

								json = "{ \"driver_id\": \"" + driverLic + "\", \"unit_id\": \"" + unitId.trim() + "\", \"seq\": 0, \"utc_ts\": \"" + dateformat2.format(new Date(getTT))
										+ "\", \"recv_utc_ts\": \"" + dateformat2.format(new Date(getTR)) + "\", \"lat\": " + lat + ", \"lon\": " + lon + ", \"alt\": " + altitude + ", \"speed\": "
										+ speed + ", \"engine_status\": " + engine_status + ", \"fix\": " + gps_valid + ", \"license\": \"" + license + "\", \"course\": " + course + ", \"hdop\": "
										+ hdop + ", \"num_sats\": " + satellite_number + ", \"gsm_cell\": 0, \"gsm_loc\": 0, \"gsm_rssi\": " + gsm_signal + ", \"mileage\": " + "0"
										+ ", \"ext_power_status\": " + ext_power_status + ", \"ext_power\": " + extBat
										+ ", \"high_acc_count\": null, \"high_de_acc_count\": 0, \"over_speed_count\": 0, \"max_speed\": " + max_speed + " } ";

								data.add(json);

							}
							connect.close();
							rs.close();
						}

						boolean tmpMaxSpeed = false;
						byte[] encodedBytes = Base64.encodeBase64((byte[]) serverIP.getUserPassGPS().getBytes());
						DefaultHttpClient httpClient = new DefaultHttpClient();
						HttpPost post = new HttpPost(String.valueOf(serverIP.getUrlRealTime()) + "/gps/add/locations");

						String jsonInit = "{ \"vender_id\": \"043\", \"locations_count\": " + data.size() + ", \"locations\": ";
						Gson gson = new Gson();
						String jsonNew = String.valueOf(jsonInit) + gson.toJson(data).replace("\\", "").replace("[\"", "[").replace("\"]", "]").replace("\",\"", ",") + "}";
						// System.out.println("jsonNew : "+jsonNew);
						StringEntity postingString = new StringEntity(jsonNew);
						HttpResponse res = null;
						post.setHeader("Authorization", "basic " + new String(encodedBytes));
						post.addHeader("Content-Type", "application/json");
						if (data.size() > 0) {
							post.setEntity((HttpEntity) postingString);
							res = httpClient.execute((HttpUriRequest) post);

							String responseAsString = EntityUtils.toString(res.getEntity());
							System.out.println("DTK3G-100T response2 : " + responseAsString);

							System.out.println("DTK3G-100T Status code2 : " + res.getStatusLine() + " Time : " + new Date());

							connect.close();
							stmt.close();

							data.clear();
						}
					}
					catch (Exception e) {
						System.out.println("DB connection fail!!");
						e.printStackTrace();
						data.clear();
					}

				}
			};
			timer.scheduleAtFixedRate((TimerTask) task, initialDelay, (long) period);
			return new ModelAndView("redirect:/member.htm", "status", "100");

		}
		return new ModelAndView("redirect:/");
	}

	public ModelAndView updateDriverLog(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String benz = null;
		benz = request.getParameter("benz");
		System.out.println("Benz : " + benz);

		if (super.chkSession(request) || benz.equals("OK")) {
			Timer timer = new Timer();
			int initialDelay = 5000;
			int period = 20000;

			TimerTask task = new TimerTask() {
				Connection connect;
				Statement stmt;
				Statement stmt2;
				Statement stmt3;
				Statement stmt4;
				Statement stmt5;
				String json;
				String tmp_utc_ts;
				String tmp_recv_utc;

				
				public void run() {

					try {

						Class.forName("com.mysql.jdbc.Driver");
						connect = DriverManager.getConnection(serverIP.getMdvrServer());
						stmt = connect.createStatement();
						stmt2 = connect.createStatement();
						stmt3 = connect.createStatement();
						stmt4 = connect.createStatement();
						stmt5 = connect.createStatement();
						if (connect != null) {
							String utc_ts = "2016-01-18T11:00:13.939Z";
							String recv_utc = "2016-01-18T11:00:15.939Z";
							String lat = null;
							String lon = null;
							int speed = 0;
							String engine_status = "0";

							String selectDevIDNO = "SELECT DevIDNO FROM driver_log WHERE `unitId` IS NOT NULL";
							ResultSet resultSet = stmt5.executeQuery(selectDevIDNO);
							while (resultSet.next()) {

								String query = "SELECT DevIDNO,JingDu,WeiDu,GPSTime,UpdateTime ,Speed, Online FROM dev_status WHERE JingDu > 0 AND DevIDNO = '" + resultSet.getString("DevIDNO") + "'";
								ResultSet rs = stmt.executeQuery(query);
								while (rs.next()) {
									// System.out.println(String.valueOf(rs.getString("DevIDNO"))
									// + " Time :" + new
									// Date(System.currentTimeMillis()));
									lat = rs.getString("WeiDu");
									lat = Float.parseFloat(lat.substring(0, 2)) >= 90.0f
											? String.valueOf(lat.substring(0, 1)) + "." + lat.substring(1, lat.length())
											: String.valueOf(lat.substring(0, 2)) + "." + lat.substring(2, lat.length());
									lon = rs.getString("JingDu");
									lon = Float.parseFloat(lon.substring(0, 3)) >= 180.0f
											? String.valueOf(lon.substring(0, 2)) + "." + lon.substring(2, lon.length())
											: String.valueOf(lon.substring(0, 3)) + "." + lon.substring(3, lon.length());
									SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
									TimeZone tz = TimeZone.getTimeZone("UTC");
									SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'");
									df.setTimeZone(tz);
									utc_ts = df.format(formatter.parse(rs.getString("GPSTime"))).trim();
									recv_utc = df.format(formatter.parse(rs.getString("UpdateTime"))).trim();
									speed = rs.getInt("Speed") / 10;
									engine_status = rs.getString("Online");
									String query2 = "SELECT GuId,`DevIDNO`,`ArmTime`,`ArmType`,`ArmDesc` FROM dev_alarm WHERE DevIDNO = '" + rs.getString("DevIDNO")
											+ "' AND ArmType ='113' ORDER BY ArmTime DESC LIMIT 1";
									ResultSet rs2 = stmt2.executeQuery(query2);
									String driverId = "";
									String license = "";
									String initLic = "";
									String armDesc = "";
									while (rs2.next()) {

										String lic;
										String deviceId;
										String armId = "";
										int statusLic = 0;

										String selectDriverLog = "SELECT `ArmDesc`,`DevIDNO`,`driver_id`,armId,status,swipeTime FROM `driver_log` WHERE `DevIDNO` = '" + rs.getString("DevIDNO") + "'";
										ResultSet rsDL = stmt4.executeQuery(selectDriverLog);
										while (rsDL.next()) {
											armDesc = rsDL.getString("ArmDesc");
											armId = rsDL.getString("armId");
											statusLic = rsDL.getInt("status");
										}

										if (!rs2.getString("ArmDesc").equals(armDesc) && !rs2.getString("GuId").equals(armId) && Integer.parseInt(engine_status) == 1) {
											deviceId = rs2.getString("ArmDesc").replace(" ", "");
											driverId = deviceId.split("=")[2].replace("?", "");
											initLic = "00000000000000000000000000000000000";
											lic = rs2.getString("ArmDesc").replace(" ", "0");
											license = lic.split("=")[2].replace("?", "");

											String updateDriverLog = "UPDATE driver_log SET `ArmDesc` = '" + rs2.getString("ArmDesc") + "',`driver_id` = '" + driverId + "',`license` = '" + initLic
													+ license + "',armId = '" + rs2.getString("GuId") + "',swipeTime = '" + rs2.getString("ArmTime") + "',status = 1 WHERE `DevIDNO` = '"
													+ rs.getString("DevIDNO") + "'";
											stmt3.executeUpdate(updateDriverLog);
											// System.out.println("Login1 ");

										}
										else if (rs2.getString("ArmDesc").equals(armDesc) && !rs2.getString("GuId").equals(armId) && statusLic == 1) {
											driverId = "";
											initLic = "";
											String updateDriverLog = "UPDATE driver_log SET `ArmDesc` = '',`driver_id` = '',`license` = '' ,armId = '',status = 0 WHERE `DevIDNO` = '"
													+ rs.getString("DevIDNO") + "'";
											stmt3.executeUpdate(updateDriverLog);
										}
										else if (Integer.parseInt(engine_status) == 0) {
											driverId = "";
											initLic = "";
											String updateDriverLog = "UPDATE driver_log SET `ArmDesc` = '',`driver_id` = '',`license` = '' ,armId = '',status = 0 WHERE `DevIDNO` = '"
													+ rs.getString("DevIDNO") + "'";
											stmt3.executeUpdate(updateDriverLog);

											// System.out.println("Logout engine
											// off");

										}

										else if (rs2.getString("ArmDesc").equals(armDesc) && rs2.getString("GuId").equals(armId) && Integer.parseInt(engine_status) == 1) {
											deviceId = rs2.getString("ArmDesc").replace(" ", "");
											driverId = deviceId.split("=")[2].replace("?", "");
											initLic = "00000000000000000000000000000000000";
											lic = rs2.getString("ArmDesc").replace(" ", "0");
											license = lic.split("=")[2].replace("?", "");

											String updateDriverLog = "UPDATE driver_log SET `ArmDesc` = '" + rs2.getString("ArmDesc") + "',`driver_id` = '" + driverId + "',`license` = '" + initLic
													+ license + "',armId = '" + rs2.getString("GuId") + "',swipeTime = '" + rs2.getString("ArmTime") + "',status = 1 WHERE `DevIDNO` = '"
													+ rs.getString("DevIDNO") + "'";
											stmt3.executeUpdate(updateDriverLog);
										}
									}

									String query3 = "SELECT GuId,`DevIDNO`,`ArmTime`,`ArmType`,`ArmDesc` FROM dev_alarm WHERE DevIDNO = '" + rs.getString("DevIDNO")
											+ "' AND (ArmType ='66' or ArmType ='16') ORDER BY ArmTime DESC LIMIT 1";
									ResultSet rs3 = stmt2.executeQuery(query3);
									while (rs3.next()) {
										rs3.getString("ArmType");

										String updateDriverLog = "UPDATE driver_log SET `accStatus` = '" + rs3.getString("ArmType") + "', `accTime` = '" + rs3.getString("ArmTime")
												+ "' WHERE `DevIDNO` = '" + rs.getString("DevIDNO") + "'";
										// System.out.println("updateDriverLog :
										// "+updateDriverLog);
										stmt3.executeUpdate(updateDriverLog);

									}
								}
							}
							System.out.println("UpdateDriver" + " : " + new Date());
						}
					}
					catch (Exception e) {
						e.printStackTrace();
					}
					try {
						if (connect != null) {
							connect.close();
						}
					}
					catch (SQLException e) {
						e.printStackTrace();
					}
				}
			};
			timer.scheduleAtFixedRate((TimerTask) task, initialDelay, (long) period);
			return new ModelAndView("redirect:/member.htm", "status", "101");
		}
		return new ModelAndView("redirect:/");
	}

	public ModelAndView addGPSRealTimeMDVR(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String benz = null;
		benz = request.getParameter("benz");
		System.out.println("Benz : " + benz);

		if (super.chkSession(request) || benz.equals("OK")) {
			Timer timer = new Timer();
			int initialDelay = 5000;
			int period = 50000;

			TimerTask task = new TimerTask() {
				Connection connectDev;
				Connection connect;
				Connection connect2;
				Connection connect3;
				Connection connect4;
				Connection connect5;
				Statement stmtDev;
				Statement stmt;
				Statement stmt2;
				Statement stmt3;
				Statement stmt5;
				Statement stmtSelect;
				Statement stmtUpdate;
				String json;
				ArrayList<String> data = new ArrayList();;
				String tmp_utc_ts;
				String tmp_recv_utc;

				Locale lc = new Locale("en", "EN");
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", lc);
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'", lc);
				SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", lc);

				
				public void run() {
					try {
						Class.forName("com.mysql.jdbc.Driver");
						// System.out.println("Server address :
						// "+serverIP.getMdvrServer());
						connectDev = DriverManager.getConnection(serverIP.getMdvrServer());
						connect = DriverManager.getConnection(serverIP.getMdvrServer());
						connect2 = DriverManager.getConnection(serverIP.getMdvrServer());
						connect3 = DriverManager.getConnection(serverIP.getMdvrServer());
						connect4 = DriverManager.getConnection(serverIP.getMdvrServer());
						connect5 = DriverManager.getConnection(serverIP.getMdvrServer());
						stmtDev = connectDev.createStatement();
						stmt = connect.createStatement();
						stmt2 = connect2.createStatement();
						stmt3 = connect3.createStatement();
						stmtUpdate = connect4.createStatement();
						stmtSelect = connect5.createStatement();
						if (connect != null) {
							String utc_ts = "2016-01-18T11:00:13.939Z";
							String recv_utc = "2016-01-18T11:00:15.939Z";
							String lat = null;
							String lon = null;
							boolean powerStatus = false;
							int speed = 0;
							int maxSpeed = 0;
							String engine_status = "0";

							String queryDevIdno = "SELECT `DevIDNO` FROM `driver_log`  WHERE unitId IS NOT NULL AND unitId != ''";
							ResultSet rsDev = stmtDev.executeQuery(queryDevIdno);

							while (rsDev.next()) {

								String query = "SELECT DevIDNO,JingDu,WeiDu,GPSTime,UpdateTime ,Speed, Online,LiCheng,YouLiang FROM dev_status WHERE DevIDNO = '" + rsDev.getString("DevIDNO")
										+ "' AND JingDu > 0"; // AND Online // 1
								// System.out.println("query : "+query);

								ResultSet rs = stmt.executeQuery(query);
								int eMileage = 0;
								while (rs.next()) {
									ResultSet rsMileage = stmt3.executeQuery(
											"SELECT MAX(dev_daily.ELiCheng) AS eMileage , dev_daily.GPSDate AS GPSDate, dev_info.VehiType AS VehiType, dev_info.VehiBand AS VehiBand FROM dev_daily INNER JOIN dev_info ON dev_daily.DevIDNO = dev_info.IDNO WHERE dev_info.IDNO = '"
													+ rs.getString("DevIDNO") + "'");
									while (rsMileage.next()) {
										eMileage = rsMileage.getInt("eMileage") / 1000;
									}
									lat = rs.getString("WeiDu");
									lat = Float.parseFloat(lat.substring(0, 2)) >= 85.0f
											? String.valueOf(lat.substring(0, 1)) + "." + lat.substring(1, lat.length())
											: String.valueOf(lat.substring(0, 2)) + "." + lat.substring(2, lat.length());
									lon = rs.getString("JingDu");
									lon = Float.parseFloat(lon.substring(0, 3)) >= 180.0f
											? String.valueOf(lon.substring(0, 2)) + "." + lon.substring(2, lon.length())
											: String.valueOf(lon.substring(0, 3)) + "." + lon.substring(3, lon.length());
									SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", lc);
									TimeZone tz = TimeZone.getTimeZone("GMT+0000");
									// formatter.setTimeZone(TimeZone.getTimeZone("GMT+1400"));
									SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'", lc);
									df.setTimeZone(tz);
									utc_ts = df.format(formatter.parse(rs.getString("GPSTime"))).trim();
									recv_utc = df.format(formatter.parse(rs.getString("UpdateTime"))).trim();

									Date dateNow = new Date();

									speed = rs.getInt("Speed") / 10;
									engine_status = rs.getString("Online");
									String query2 = "SELECT `DevIDNO`,`driver_id`,`license`,unitId,`max_speed`,`status`,`swipeTime`,`accStatus`,`accTime` FROM `driver_log`  WHERE DevIDNO = '"
											+ rs.getString("DevIDNO") + "' AND unitId IS NOT NULL AND unitId != ''";

									ResultSet rs2 = stmt2.executeQuery(query2);
									int max_speed = 0;
									String driverId = "";
									String license = "";
									String unitId = null;
									String swipeTime = null;
									String accStatus = null;
									String accTime = null;
									Date swipeTimeD = null;
									Date accTimeD = null;
									int min = 60000;
									while (rs2.next()) {

										swipeTime = rs2.getString("swipeTime");
										accStatus = rs2.getString("accStatus");
										accTime = rs2.getString("accTime");

										try {
											swipeTimeD = dateformat.parse(swipeTime);
											accTimeD = dateformat.parse(accTime);

											if (Integer.parseInt(engine_status) == 1) {

												if (accStatus.equals("16") && swipeTimeD.after(accTimeD)) {

													driverId = rs2.getString("driver_id");
													license = rs2.getString("license");

													if (driverId.length() > 18) {
														driverId = driverId.substring(0, 15);
														license = license.substring(0, 110);
													}
												}

											}
											else {
												driverId = "";
												license = "";
											}
										}
										catch (Exception e) {
											driverId = "";
											license = "";
										}

										// System.out.println("driverId :
										// "+driverId.length());

										unitId = rs2.getString("unitId");
										max_speed = rs2.getInt("max_speed");

										json = "{ \"driver_id\": \"" + driverId + "\", \"unit_id\": \"" + unitId + "\", \"seq\": 0, \"utc_ts\": \"" + utc_ts + "\", \"recv_utc_ts\": \"" + recv_utc
												+ "\", \"lat\": " + lat + ", \"lon\": " + lon + ", \"alt\": 0, \"speed\": " + speed + ", \"engine_status\": " + engine_status
												+ ", \"fix\": 1, \"license\": \"" + license
												+ "\", \"course\": 102, \"hdop\": 2, \"num_sats\": 8, \"gsm_cell\": 0, \"gsm_loc\": 0, \"gsm_rssi\": 0, \"mileage\": " + eMileage
												+ ", \"ext_power_status\": 1, \"ext_power\": 24, \"high_acc_count\": 0, \"high_de_acc_count\": 0, \"over_speed_count\": 0, \"max_speed\": " + max_speed
												+ " } ";

										if (((Math.abs((Math.abs(df.parse(df.format(dateNow)).getTime()) - Math.abs(df.parse(utc_ts).getTime())) / min)) <= 5) && !unitId.equals(null)
												&& !unitId.isEmpty() && engine_status.equals("1")) {

											try {

												String uri = serverIP.getMysql();
												Class.forName("com.mysql.jdbc.Driver");
												connect2 = DriverManager.getConnection(uri);
												stmt5 = connect2.createStatement();

												String sql = "UPDATE `distar_dlt`.`master_file` SET `dlt_update_time` = '" + dateformat.format(dateNow) + "' WHERE `unit_id` = '" + unitId + "';";
												stmt5.executeUpdate(sql);

											}
											catch (Exception e) {
												// TODO: handle exception
												System.out.println("sql exception : " + e.getMessage());
											}

											data.add(json);
										}
										else {

										}

										tmp_utc_ts = utc_ts;
										tmp_recv_utc = recv_utc;
										int tmpMaxSpeed = 0;
										String selectMaxSpeed = "SELECT `max_speed` FROM `driver_log`  WHERE DevIDNO = '" + rs.getString("DevIDNO") + "'";
										ResultSet resSelectMaxspeed = stmtSelect.executeQuery(selectMaxSpeed);
										while (resSelectMaxspeed.next()) {
											tmpMaxSpeed = resSelectMaxspeed.getInt("max_speed");
										}
										maxSpeed = speed > tmpMaxSpeed ? speed : tmpMaxSpeed;
									}
									String updateMaxSpeed = "UPDATE driver_log SET `max_speed` = '" + maxSpeed + "' WHERE `DevIDNO` = '" + rs.getString("DevIDNO") + "'";
									stmtUpdate.executeUpdate(updateMaxSpeed);
									maxSpeed = 0;
								}
							}
							byte[] encodedBytes = Base64.encodeBase64((byte[]) serverIP.getUserPassGPS().getBytes());
							DefaultHttpClient httpClient = new DefaultHttpClient();
							HttpPost post = new HttpPost(serverIP.getUrlRealTime() + "/gps/add/locations");
							String jsonInit = "{ \"vender_id\": \"043\", \"locations_count\": " + data.size() + ", \"locations\": ";
							Gson gson = new Gson();
							String jsonNew = String.valueOf(jsonInit) + gson.toJson(data).replace("\\", "").replace("[\"", "[").replace("\"]", "]").replace("\",\"", ",") + "}";
							System.out.println(jsonNew);
							StringEntity postingString = new StringEntity(jsonNew);
							HttpResponse res = null;
							post.setHeader("Authorization", "basic " + new String(encodedBytes));
							post.addHeader("Content-Type", "application/json");
							if (data.size() > 0) {
								post.setEntity((HttpEntity) postingString);
								res = httpClient.execute((HttpUriRequest) post);

								String responseAsString = EntityUtils.toString(res.getEntity());
								System.out.println("MDVR response : " + responseAsString);

								connect.close();
								stmt.close();

								String uri = serverIP.getMysql();
								Class.forName("com.mysql.jdbc.Driver");
								connect = DriverManager.getConnection(uri);
								stmt = connect.createStatement();

								SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", lc);
								TimeZone tz = TimeZone.getTimeZone("GMT+07:00");

								dateformat.setTimeZone(tz);
								Date dateNow = new Date();
								String timeUpdate = "";

								timeUpdate = dateformat.format(dateNow);

								try {
									String update = "UPDATE `distar_dlt`.`realtime_status` SET `exception` = '" + String.valueOf(res) + "', `status` = '"
											+ String.valueOf(res.getStatusLine().getStatusCode()) + "', `time_update` = '" + timeUpdate + "', `data_log` = '" + jsonNew + "' WHERE `device` = '3'";
									stmt.executeUpdate(update);
								}
								catch (Exception e) {
									String sql = "INSERT INTO `distar_dlt`.`realtime_status` (`device`,`status`,`exception`,`time_update`,`data_log`) VALUES ('3','"
											+ String.valueOf(res.getStatusLine().getStatusCode() + "','" + String.valueOf(res) + "','" + timeUpdate + "','" + jsonNew + "')");
									stmt.executeUpdate(sql);
								}

								System.out.println("MDVR Status : " + res.getStatusLine().getStatusCode() + " : " + new Date());
							}
							data.clear();
						}
					}
					catch (Exception e) {
						e.printStackTrace();
					}
					try {
						if (connect != null) {
							connect.close();
						}
						if (connect2 != null) {
							connect2.close();
						}
						if (connect3 != null) {
							connect3.close();
						}
						if (connect4 != null) {
							connect4.close();
						}
					}
					catch (SQLException e) {
						e.printStackTrace();
					}
				}
			};
			timer.scheduleAtFixedRate((TimerTask) task, initialDelay, (long) period);
			// return new ModelAndView("status", "status", "ok");
			return new ModelAndView("redirect:/member.htm", "status", "100");
		}
		return new ModelAndView("redirect:/");
	}

	public ModelAndView updateDriverLog2(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String benz = null;
		benz = request.getParameter("benz");
		System.out.println("Benz : " + benz);

		if (super.chkSession(request) || benz.equals("OK")) {
			Timer timer = new Timer();
			int initialDelay = 5000;
			int period = 30000;

			TimerTask task = new TimerTask() {
				Connection connect;
				Statement stmt;
				Statement stmt2;
				Statement stmt3;
				Statement stmt4;
				Statement stmt5;
				String json;
				String tmp_utc_ts;
				String tmp_recv_utc;

				
				public void run() {

					try {

						Class.forName("com.mysql.jdbc.Driver");
						connect = DriverManager.getConnection(serverIP.getMdvrServer2());
						stmt = connect.createStatement();
						stmt2 = connect.createStatement();
						stmt3 = connect.createStatement();
						stmt4 = connect.createStatement();
						stmt5 = connect.createStatement();
						if (connect != null) {
							String utc_ts = "2016-01-18T11:00:13.939Z";
							String recv_utc = "2016-01-18T11:00:15.939Z";
							String lat = null;
							String lon = null;
							int speed = 0;
							String engine_status = "0";

							String selectDevIDNO = "SELECT DevIDNO FROM driver_log WHERE `unitId` IS NOT NULL";
							ResultSet resultSet = stmt5.executeQuery(selectDevIDNO);
							while (resultSet.next()) {

								String query = "SELECT DevIDNO,JingDu,WeiDu,GPSTime,UpdateTime ,Speed, Online FROM dev_status WHERE JingDu > 0 AND DevIDNO = '" + resultSet.getString("DevIDNO") + "'";
								ResultSet rs = stmt.executeQuery(query);
								while (rs.next()) {
									System.out.println(String.valueOf(rs.getString("DevIDNO")) + " Time :" + new Date(System.currentTimeMillis()));

									lat = rs.getString("WeiDu");
									lat = Float.parseFloat(lat.substring(0, 2)) >= 90.0f
											? String.valueOf(lat.substring(0, 1)) + "." + lat.substring(1, lat.length())
											: String.valueOf(lat.substring(0, 2)) + "." + lat.substring(2, lat.length());
									lon = rs.getString("JingDu");
									lon = Float.parseFloat(lon.substring(0, 3)) >= 180.0f
											? String.valueOf(lon.substring(0, 2)) + "." + lon.substring(2, lon.length())
											: String.valueOf(lon.substring(0, 3)) + "." + lon.substring(3, lon.length());
									SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
									TimeZone tz = TimeZone.getTimeZone("UTC");
									SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'");
									df.setTimeZone(tz);
									utc_ts = df.format(formatter.parse(rs.getString("GPSTime"))).trim();
									recv_utc = df.format(formatter.parse(rs.getString("UpdateTime"))).trim();
									speed = rs.getInt("Speed") / 10;
									engine_status = rs.getString("Online");

									Date dateNow = new Date();
									String month = dateNow.getMonth() + 1 + "";

									if (month.length() == 1) {
										month = "0" + month;
									}
									String ym = Integer.parseInt(1900 + dateNow.getYear() + "") + month;

									String query2 = "SELECT GuId,`DevIDNO`,`ArmTime`,`ArmType`,`ArmDesc` FROM dev_alarm_custom_" + ym + " WHERE DevIDNO = '" + rs.getString("DevIDNO")
											+ "' AND ArmType ='113' ORDER BY ArmTime DESC LIMIT 1";
									ResultSet rs2 = stmt2.executeQuery(query2);
									String driverId = "";
									String license = "";
									String initLic = "";
									String armDesc = "";
									String armTime = "";
									while (rs2.next()) {

										// System.out.println("Select : " +
										// query2);
										String lic;
										String deviceId;
										String armId = "";
										int statusLic = 0;
										String selectDriverLog = "SELECT `ArmDesc`,`DevIDNO`,`driver_id`,armId,status FROM `driver_log` WHERE `DevIDNO` = '" + rs.getString("DevIDNO") + "'";
										ResultSet rsDL = stmt4.executeQuery(selectDriverLog);
										while (rsDL.next()) {
											armDesc = rsDL.getString("ArmDesc");
											armId = rsDL.getString("armId");
											statusLic = rsDL.getInt("status");

										}

										if (!rs2.getString("ArmDesc").equals(armDesc) && !rs2.getString("GuId").equals(armId) && Integer.parseInt(engine_status) == 1) {
											deviceId = rs2.getString("ArmDesc").replace(" ", "");
											driverId = deviceId.split("=")[2].replace("?", "");
											initLic = "00000000000000000000000000000000000";
											lic = rs2.getString("ArmDesc").replace(" ", "0");
											license = lic.split("=")[2].replace("?", "");

											String updateDriverLog = "UPDATE driver_log SET `ArmDesc` = '" + rs2.getString("ArmDesc") + "',`driver_id` = '" + driverId + "',`license` = '" + initLic
													+ license + "',armId = '" + rs2.getString("GuId") + "',swipeTime = '" + rs2.getString("ArmTime") + "',status = 1 WHERE `DevIDNO` = '"
													+ rs.getString("DevIDNO") + "'";
											stmt3.executeUpdate(updateDriverLog);
											// System.out.println("Login1 ");

										}
										else if (rs2.getString("ArmDesc").equals(armDesc) && !rs2.getString("GuId").equals(armId) && statusLic == 1) {
											driverId = "";
											initLic = "";
											String updateDriverLog = "UPDATE driver_log SET `ArmDesc` = '',`driver_id` = '',`license` = '' ,armId = '',status = 0 WHERE `DevIDNO` = '"
													+ rs.getString("DevIDNO") + "'";
											stmt3.executeUpdate(updateDriverLog);
										}
										else if (Integer.parseInt(engine_status) == 0) {
											driverId = "";
											initLic = "";
											String updateDriverLog = "UPDATE driver_log SET `ArmDesc` = '',`driver_id` = '',`license` = '' ,armId = '',status = 0 WHERE `DevIDNO` = '"
													+ rs.getString("DevIDNO") + "'";
											stmt3.executeUpdate(updateDriverLog);

											// System.out.println("Logout engine
											// off");

										}

										else if (rs2.getString("ArmDesc").equals(armDesc) && rs2.getString("GuId").equals(armId) && Integer.parseInt(engine_status) == 1) {
											deviceId = rs2.getString("ArmDesc").replace(" ", "");
											driverId = deviceId.split("=")[2].replace("?", "");
											initLic = "00000000000000000000000000000000000";
											lic = rs2.getString("ArmDesc").replace(" ", "0");
											license = lic.split("=")[2].replace("?", "");

											String updateDriverLog = "UPDATE driver_log SET `ArmDesc` = '" + rs2.getString("ArmDesc") + "',`driver_id` = '" + driverId + "',`license` = '" + initLic
													+ license + "',armId = '" + rs2.getString("GuId") + "',swipeTime = '" + rs2.getString("ArmTime") + "',status = 1 WHERE `DevIDNO` = '"
													+ rs.getString("DevIDNO") + "'";
											stmt3.executeUpdate(updateDriverLog);
										}
									}
									rs2.close();
									String query3 = "SELECT GuId,`DevIDNO`,`ArmTime`,`ArmType`,`ArmDesc` FROM dev_alarm_acc_" + ym + " WHERE DevIDNO = '" + rs.getString("DevIDNO")
											+ "' AND (ArmType ='66' or ArmType ='16') ORDER BY ArmTime DESC LIMIT 1";
									ResultSet rs3 = stmt2.executeQuery(query3);

									// System.out.println("query3 : "+query3);
									while (rs3.next()) {
										rs3.getString("ArmType");

										String updateDriverLog = "UPDATE driver_log SET `accStatus` = '" + rs3.getString("ArmType") + "', `accTime` = '" + rs3.getString("ArmTime")
												+ "' WHERE `DevIDNO` = '" + rs.getString("DevIDNO") + "'";
										// System.out.println("updateDriverLog :
										// "+updateDriverLog);
										stmt3.executeUpdate(updateDriverLog);

									}
								}
								rs.close();
							}

							System.out.println("UpdateDriver" + " : " + new Date());
						}
					}
					catch (Exception e) {
						e.printStackTrace();
					}
					try {

						connect.close();

					}
					catch (SQLException e) {
						e.printStackTrace();
					}
				}
			};
			timer.scheduleAtFixedRate((TimerTask) task, initialDelay, (long) period);
			return new ModelAndView("redirect:/member.htm", "status", "101");
		}
		return new ModelAndView("redirect:/");
	}

	public ModelAndView addGPSRealTimeMDVR2(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String benz = null;
		benz = request.getParameter("benz");
		System.out.println("Realtime MDVR2 Benz : " + benz);

		if (super.chkSession(request) || benz.equals("OK")) {
			Timer timer = new Timer();
			int initialDelay = 5000;
			int period = 50000;

			TimerTask task = new TimerTask() {
				Connection connectDev;
				Connection connect;
				Connection connect2;
				Connection connect3;
				Connection connect4;
				Connection connect5;
				Statement stmtDev;
				Statement stmt;
				Statement stmt2;
				Statement stmt3;
				Statement stmt5;
				Statement stmtSelect;
				Statement stmtUpdate;
				String json;
				ArrayList<String> data = new ArrayList();;
				String tmp_utc_ts;
				String tmp_recv_utc;
				Locale lc = new Locale("en", "EN");

				
				public void run() {
					try {
						Class.forName("com.mysql.jdbc.Driver");
						connectDev = DriverManager.getConnection(serverIP.getMdvrServer2());
						connect = DriverManager.getConnection(serverIP.getMdvrServer2());
						connect2 = DriverManager.getConnection(serverIP.getMdvrServer2());
						connect3 = DriverManager.getConnection(serverIP.getMdvrServer2());
						connect4 = DriverManager.getConnection(serverIP.getMdvrServer2());
						connect5 = DriverManager.getConnection(serverIP.getMdvrServer2());
						stmtDev = connectDev.createStatement();
						stmt = connect.createStatement();
						stmt2 = connect2.createStatement();
						stmt3 = connect3.createStatement();
						stmtUpdate = connect4.createStatement();
						stmtSelect = connect5.createStatement();
						if (connect != null) {
							String utc_ts = "2016-01-18T11:00:13.939Z";
							String recv_utc = "2016-01-18T11:00:15.939Z";
							String lat = null;
							String lon = null;
							boolean powerStatus = false;
							int speed = 0;
							int maxSpeed = 0;
							String engine_status = "0";

							String queryDevIdno = "SELECT `DevIDNO` FROM `driver_log`  WHERE unitId IS NOT NULL AND unitId != ''";
							ResultSet rsDev = stmtDev.executeQuery(queryDevIdno);

							while (rsDev.next()) {

								String query = "SELECT DevIDNO,JingDu,WeiDu,GPSTime,UpdateTime ,Speed, Online,LiCheng,YouLiang FROM dev_status WHERE DevIDNO = '" + rsDev.getString("DevIDNO")
										+ "' AND JingDu > 0"; // AND Online // 1
								// System.out.println("query : "+query);

								ResultSet rs = stmt.executeQuery(query);
								int eMileage = 0;
								while (rs.next()) {
									String gMileage = "SELECT MAX(dev_daily.ELiCheng) AS eMileage , dev_daily.GPSDate AS GPSDate, dev_info.VehiType AS VehiType, dev_info.VehiBand AS VehiBand FROM dev_daily INNER JOIN dev_info ON dev_daily.DevIDNO = dev_info.IDNO WHERE dev_info.IDNO = '"
											+ rs.getString("DevIDNO") + "'";
									ResultSet rsMileage = stmt3.executeQuery(gMileage);

//									 System.out.println("SQL : "+gMileage);

									while (rsMileage.next()) {
										eMileage = rsMileage.getInt("eMileage") / 1000;
									}

									lat = rs.getString("WeiDu");
									lat = Float.parseFloat(lat.substring(0, 2)) >= 85.0f
											? String.valueOf(lat.substring(0, 1)) + "." + lat.substring(1, lat.length())
											: String.valueOf(lat.substring(0, 2)) + "." + lat.substring(2, lat.length());
									lon = rs.getString("JingDu");
									lon = Float.parseFloat(lon.substring(0, 3)) >= 180.0f
											? String.valueOf(lon.substring(0, 2)) + "." + lon.substring(2, lon.length())
											: String.valueOf(lon.substring(0, 3)) + "." + lon.substring(3, lon.length());
									SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", lc);
									TimeZone tz = TimeZone.getTimeZone("GMT+00:00");
									// formatter.setTimeZone(TimeZone.getTimeZone("GMT+1400"));
									SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'", lc);
									df.setTimeZone(tz);
//									utc_ts = df.format(formatter.parse(rs.getString("GPSTime"))).trim();
									utc_ts = df.format(formatter.parse(rs.getString("UpdateTime"))).trim();
									recv_utc = df.format(formatter.parse(rs.getString("UpdateTime"))).trim();
									
									

									Date dateNow = new Date();

									speed = rs.getInt("Speed") / 10;
									engine_status = rs.getString("Online");
									String query2 = "SELECT `DevIDNO`,`driver_id`,`license`,unitId,`max_speed`,`swipeTime`,`accStatus`,`accTime` FROM `driver_log`  WHERE DevIDNO = '"
											+ rs.getString("DevIDNO") + "' AND unitId IS NOT NULL AND unitId != ''";

									ResultSet rs2 = stmt2.executeQuery(query2);
									int max_speed = 0;
									String driverId = "";
									String license = "";
									String unitId = null;
									String accStatus = null;
									String accTime = null;
									String swipeTime = null;
									Date swipeTimeD = null;
									Date accTimeD = null;
									int min = 60000;
									while (rs2.next()) {

										// System.out.println("Test : " +
										// query2);
										swipeTime = rs2.getString("accTime");
										accStatus = rs2.getString("accStatus");
										accTime = rs2.getString("swipeTime");
										SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", lc);

										if (Integer.parseInt(engine_status) == 1) {

											try {

												swipeTimeD = dateformat.parse(swipeTime);
												accTimeD = dateformat.parse(accTime);

												System.out.println("accStatus : " + rs.getString("DevIDNO") + " " + accStatus.equals("16"));
												System.out.println("swipeTimeD.before(accTimeD) : " + rs.getString("DevIDNO") + " " + swipeTimeD.before(accTimeD));
												System.out.println("IF : " + rs.getString("DevIDNO") + " " + (accStatus.equals(16) && swipeTimeD.before(accTimeD)));

												if (accStatus.equals("16") && swipeTimeD.before(accTimeD)) {

													System.out.println("before : " + rs.getString("DevIDNO") + " " + swipeTimeD.before(accTimeD));

													driverId = rs2.getString("driver_id");
													license = rs2.getString("license");
												}
											}
											catch (Exception e) {
												// TODO: handle exception

											}
										}
										else {

											driverId = "";
											license = "";
										}

										try {
											if (license.length() >= 110) {
												license = license.substring(0, 110);
											}
										}
										catch (Exception e) {
											license = "";
											driverId = "";
										}
										unitId = rs2.getString("unitId");
										max_speed = rs2.getInt("max_speed");

										json = "{ \"driver_id\": \"" + driverId + "\", \"unit_id\": \"" + unitId + "\", \"seq\": 0, \"utc_ts\": \"" + utc_ts + "\", \"recv_utc_ts\": \"" + recv_utc
												+ "\", \"lat\": " + lat + ", \"lon\": " + lon + ", \"alt\": 0, \"speed\": " + speed + ", \"engine_status\": " + engine_status
												+ ", \"fix\": 1, \"license\": \"" + license
												+ "\", \"course\": 102, \"hdop\": 2, \"num_sats\": 8, \"gsm_cell\": 0, \"gsm_loc\": 0, \"gsm_rssi\": 0, \"mileage\": " + eMileage
												+ ", \"ext_power_status\": 1, \"ext_power\": 24, \"high_acc_count\": 0, \"high_de_acc_count\": 0, \"over_speed_count\": 0, \"max_speed\": " + max_speed
												+ " } ";

										String timeUpdate = "";

										timeUpdate = df.format(dateNow);

										System.out.println("utc_ts : "+utc_ts);
										System.out.println("recv_utc : "+recv_utc);
										System.out.println("timeUpdate : "+timeUpdate);
										
										if (((Math.abs((Math.abs(df.parse(timeUpdate).getTime()) - Math.abs(df.parse(utc_ts).getTime())) / min)) <= 5)) {

											try {

												String uri = serverIP.getMysql();
												Class.forName("com.mysql.jdbc.Driver");
												connect2 = DriverManager.getConnection(uri);
												stmt5 = connect2.createStatement();

												String sql = "UPDATE `distar_dlt`.`master_file` SET `dlt_update_time` = '" + dateformat.format(dateNow) + "' WHERE `unit_id` = '" + unitId + "';";
												stmt5.executeUpdate(sql);

												connect2.close();

											}
											catch (Exception e) {
												// TODO: handle exception
												System.out.println("sql exception : " + e.getMessage());
											}

											data.add(json);
										}
										else {

										}

										tmp_utc_ts = utc_ts;
										tmp_recv_utc = recv_utc;
										int tmpMaxSpeed = 0;
										String selectMaxSpeed = "SELECT `max_speed` FROM `driver_log`  WHERE DevIDNO = '" + rs.getString("DevIDNO") + "'";
										ResultSet resSelectMaxspeed = stmtSelect.executeQuery(selectMaxSpeed);
										while (resSelectMaxspeed.next()) {
											tmpMaxSpeed = resSelectMaxspeed.getInt("max_speed");
										}
										maxSpeed = speed > tmpMaxSpeed ? speed : tmpMaxSpeed;
									}
									String updateMaxSpeed = "UPDATE driver_log SET `max_speed` = '" + maxSpeed + "' WHERE `DevIDNO` = '" + rs.getString("DevIDNO") + "'";
									stmtUpdate.executeUpdate(updateMaxSpeed);
									maxSpeed = 0;

								}

								// rs.close();

							}
							connect.close();

							stmt.close();
							rsDev.close();
							byte[] encodedBytes = Base64.encodeBase64((byte[]) serverIP.getUserPassGPS().getBytes());
							DefaultHttpClient httpClient = new DefaultHttpClient();
							HttpPost post = new HttpPost(serverIP.getUrlRealTime() + "/gps/add/locations");
							String jsonInit = "{ \"vender_id\": \"043\", \"locations_count\": " + data.size() + ", \"locations\": ";
							Gson gson = new Gson();
							String jsonNew = String.valueOf(jsonInit) + gson.toJson(data).replace("\\", "").replace("[\"", "[").replace("\"]", "]").replace("\",\"", ",") + "}";
							System.out.println(jsonNew);
							StringEntity postingString = new StringEntity(jsonNew);
							HttpResponse res = null;
							post.setHeader("Authorization", "basic " + new String(encodedBytes));
							post.addHeader("Content-Type", "application/json");
							if (data.size() > 0) {
								post.setEntity((HttpEntity) postingString);
								res = httpClient.execute((HttpUriRequest) post);

								String responseAsString = EntityUtils.toString(res.getEntity());
								System.out.println("MDVR2 response : " + responseAsString);

								String uri = serverIP.getMysql();
								Class.forName("com.mysql.jdbc.Driver");
								connect = DriverManager.getConnection(uri);
								stmt = connect.createStatement();

								SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", lc);
								TimeZone tz = TimeZone.getTimeZone("GMT+07:00");

								dateformat.setTimeZone(tz);
								Date dateNow = new Date();
								String timeUpdate = "";

								timeUpdate = dateformat.format(dateNow);

								try {
									String update = "UPDATE `distar_dlt`.`realtime_status` SET `exception` = '" + String.valueOf(res) + "', `status` = '"
											+ String.valueOf(res.getStatusLine().getStatusCode()) + "', `time_update` = '" + timeUpdate + "', `data_log` = '" + jsonNew + "' WHERE `device` = '6';";
									stmt.executeUpdate(update);
								}
								catch (Exception e) {
									String sql = "INSERT INTO `distar_dlt`.`realtime_status`  (`device`,`status`,`exception`,`time_update`,`data_log`) VALUES ('6','"
											+ String.valueOf(res.getStatusLine().getStatusCode()) + "','" + String.valueOf(res) + "','" + timeUpdate + "','" + jsonNew + "');";
									stmt.executeUpdate(sql);
								}

								System.out.println("MDVR2 status : " + res.getStatusLine().getStatusCode());
							}
							data.clear();
						}
					}
					catch (Exception e) {
						e.printStackTrace();
					}
					try {
						connect.close();
						connect2.close();
						connect3.close();
						connect4.close();
						connect5.close();
					}
					catch (SQLException e) {
						e.printStackTrace();
					}
				}
			};
			timer.scheduleAtFixedRate((TimerTask) task, initialDelay, (long) period);
			// return new ModelAndView("status", "status", "ok");
			return new ModelAndView("redirect:/member.htm", "status", "100");
		}
		return new ModelAndView("redirect:/");
	}

	public ModelAndView addHistoryForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (super.chkSession(request)) {
			GpsBackupForm gpsBackupForm = new GpsBackupForm();
			// gpsBackupForm.setDate_time_start("2016-01-11T07:36:13.939Z");
			gpsBackupForm.setTime_end("08:30:00");

			ModelMap modelMap = new ModelMap();
			modelMap.addAttribute("gpsBackupForm", gpsBackupForm);

			return new ModelAndView("/service/gpsBackupForm", modelMap);
		}
		return new ModelAndView("redirect:/");
	}

	public ModelAndView addHistoryDTK3G(HttpServletRequest request, HttpServletResponse response, GpsBackupForm gpsBackupForm) throws Exception {
		if (super.chkSession(request)) {
			Connection connect;
			Statement stmt;

			String json;
			ArrayList<String> data;

			connect = null;
			stmt = null;
			json = null;
			data = new ArrayList();

			Date d = new Date();
			// timer send gps data to DLT

			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			TimeZone tz = TimeZone.getTimeZone("UTC");
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'");
			df.setTimeZone(tz);

			String initLic = "";
			String date_start = gpsBackupForm.getDate_start();
			String time_start = gpsBackupForm.getTime_start();
			String unitId = gpsBackupForm.getUnitId();
			String lat = gpsBackupForm.getLat();
			String lon = gpsBackupForm.getLon();
			String utc_ts = "2016-01-18T11:00:13.939Z";
			String recv_utc = "2016-01-18T11:00:15.939Z";
			String imei_id = "";
			String rfid = "";
			String driverLic = "";
			String gsm_station_id = "";
			int count = gpsBackupForm.getValue_record();
			int course = 0;
			int altitude = 0;
			int gps_valid = 1;
			int satellite_number = 12;
			int hdop = 5;
			int gsm_signal = 31;
			int engine_status = 0;
			int speed = 0;
			int max_speed = 0;
			boolean powerStatus = false;
			boolean bat = false;
			int extBat = 24;
			String ad5 = "";
			String rfidTime = null;
			int ext_power_status = 1;
			String gpsLastRfid = "";
			String gpsLastAlarmId = "";
			String alarmHistoryId = "";
			int loginStatus = 0;

			utc_ts = df.format(formatter.parse(date_start + " " + time_start));

			try {
				String url = serverIP.getTrackingServer();
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				connect = DriverManager.getConnection(url);
				stmt = connect.createStatement();

				if (connect != null) {
					System.out.println("Connected!!");

					for (int i = 0; i < count; i++) {

						String[] spTime = time_start.split(":");
						int sec = Integer.parseInt(spTime[2]) + 32;
						int min = Integer.parseInt(spTime[1]);
						int hour = Integer.parseInt(spTime[0]);

						TimeZone tz2 = TimeZone.getTimeZone("GMT+7:00");

						SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss");
						df.setTimeZone(tz2);

						String timeTracker2 = timeFormat.format(timeFormat.parse(hour + ":" + min + ":" + sec));

						String timeTracker = df.format(formatter.parse(date_start + " " + timeTracker2));

						String timeReceive2 = timeFormat.format(timeFormat.parse(hour + ":" + min + ":" + (sec + 3)));

						String timeReceive = df.format(formatter.parse(date_start + " " + timeReceive2));

						utc_ts = timeTracker;
						recv_utc = timeReceive;

						json = "{ \"driver_id\": \"" + driverLic + "\", \"unit_id\": \"" + unitId + "\", \"seq\": 0, \"utc_ts\": \"" + utc_ts + "\", \"recv_utc_ts\": \"" + recv_utc + "\", \"lat\": "
								+ lat + ", \"lon\": " + lon + ", \"alt\": " + altitude + ", \"speed\": " + speed + ", \"engine_status\": " + engine_status + ", \"fix\": " + gps_valid
								+ ", \"license\": \"\", \"course\": " + course + ", \"hdop\": " + hdop + ", \"num_sats\": " + satellite_number + ", \"gsm_cell\": 0, \"gsm_loc\": 0, \"gsm_rssi\": "
								+ gsm_signal + ", \"mileage\": " + "0" + ", \"ext_power_status\": " + ext_power_status + ", \"ext_power\": " + extBat + ", \"high_acc_count\": " + null
								+ ", \"high_de_acc_count\":" + 0 + ", \"over_speed_count\": " + 0 + ", \"max_speed\": " + 0 + " } ";

						data.add(json);

						time_start = timeTracker2;
					}

				}

				byte[] encodedBytes = Base64.encodeBase64((byte[]) serverIP.getUserPassGPS().getBytes());
				DefaultHttpClient httpClient = new DefaultHttpClient();
				HttpPost post = new HttpPost(String.valueOf(serverIP.getUrlRealTime()) + "/gps/backup/add/locations");
				String jsonInit = "{ \"vender_id\": \"043\", \"locations_count\": " + data.size() + ", \"locations\": ";
				Gson gson = new Gson();
				String jsonNew = String.valueOf(jsonInit) + gson.toJson(data).replace("\\", "").replace("[\"", "[").replace("\"]", "]").replace("\",\"", ",") + "}";
				// System.out.println(jsonNew);
				StringEntity postingString = new StringEntity(jsonNew);
				HttpResponse res = null;
				post.setHeader("Authorization", "basic " + new String(encodedBytes));
				post.addHeader("Content-Type", "application/json");
				if (data.size() > 0) {
					post.setEntity((HttpEntity) postingString);
					res = httpClient.execute((HttpUriRequest) post);
					System.out.println("History 3G : " + String.valueOf(res.getStatusLine().getStatusCode()) + "\n");
					data.clear();
					return new ModelAndView("redirect:/member.htm", "status", res.getStatusLine().getStatusCode());
				}
			}
			catch (Exception e) {
				System.out.println("DB connection fail!!");
				e.printStackTrace();
				data.clear();
			}
			return new ModelAndView("redirect:/member.htm", "status", "102");
		}
		return new ModelAndView("redirect:/");
	}

	public ModelAndView searchForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (super.chkSession(request)) {
			SearchForm searchForm = new SearchForm();
			// gpsBackupForm.setDate_time_start("2016-01-11T07:36:13.939Z");

			ModelMap modelMap = new ModelMap();
			modelMap.addAttribute("searchForm", searchForm);

			return new ModelAndView("/service/searchTracker", modelMap);
		}
		return new ModelAndView("redirect:/");
	}

	public ModelAndView resSearchForm(HttpServletRequest request, HttpServletResponse response, SearchForm searchForm) throws Exception {
		if (super.chkSession(request)) {
			Connection connect;
			Statement stmt;

			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			connect = DriverManager.getConnection(serverIP.getTrackingServer());
			stmt = connect.createStatement();

			if (searchForm.getImei().length() > 0) {
				String query = "SELECT [sn_imei_id],[tracker_serial],[tracker_name],[tracker_sim_number],[tracker_register_time],[tracker_expired_time] FROM [ms03].[dbo].[Tracker] where [sn_imei_id] ='"
						+ searchForm.getImei() + "'";

				// System.out.println("query : "+query);
				ResultSet rs = stmt.executeQuery(query);
				int i = 0;
				while (rs.next()) {
					searchForm.setImei(rs.getString("sn_imei_id"));
					searchForm.setTrackerName(rs.getString("tracker_name"));
					searchForm.setTrackerSimNumber(rs.getString("tracker_sim_number"));
					searchForm.setTrackerRegisterTime(rs.getString("tracker_register_time"));
					searchForm.setTrackerExpiredTime(rs.getString("tracker_expired_time"));
					i++;
				}
				if (i == 0) {

					// System.out.println(searchForm.getStatus());

					return new ModelAndView("redirect:/service/searchForm.htm");
				}

				ModelMap modelMap = new ModelMap();
				modelMap.addAttribute("searchForm", searchForm);

				return new ModelAndView("/service/resSearchTracker", modelMap);

			}
			else if (searchForm.getSerialNum().length() > 0) {
				String query = "SELECT [sn_imei_id],[tracker_serial],[tracker_name],[tracker_sim_number],[tracker_register_time],[tracker_expired_time] FROM [ms03].[dbo].[Tracker] where [tracker_serial] ='"
						+ searchForm.getSerialNum() + "'";
				ResultSet rs = stmt.executeQuery(query);
				int i = 0;
				while (rs.next()) {
					searchForm.setImei(rs.getString("sn_imei_id"));
					searchForm.setSerialNum(rs.getString("tracker_serial"));
					searchForm.setTrackerName(rs.getString("tracker_name"));
					searchForm.setTrackerSimNumber(rs.getString("tracker_sim_number"));
					searchForm.setTrackerRegisterTime(rs.getString("tracker_register_time"));
					searchForm.setTrackerExpiredTime(rs.getString("tracker_expired_time"));
					i++;
				}
				if (i == 0) {
					return new ModelAndView("redirect:/service/searchForm.htm");
				}

				ModelMap modelMap = new ModelMap();
				modelMap.addAttribute("searchForm", searchForm);
				return new ModelAndView("/service/resSearchTracker", modelMap);
			}
			else {
				searchForm = new SearchForm();

				ModelMap modelMap = new ModelMap();
				modelMap.addAttribute("searchForm", searchForm);

				return new ModelAndView("/service/searchTracker", modelMap);
			}
		}
		return new ModelAndView("redirect:/");
	}

	public ModelAndView updateTrackerForm(HttpServletRequest request, HttpServletResponse response, SearchForm searchForm) throws Exception {
		if (super.chkSession(request)) {
			Connection connect;
			Statement stmt;
			Statement stmt2;

			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			connect = DriverManager.getConnection(serverIP.getTrackingServer());
			stmt = connect.createStatement();
			stmt2 = connect.createStatement();

			int i = 0;

			if (searchForm.getSerialNum().length() > 0) {
				String query = "SELECT [sn_imei_id] FROM [ms03].[dbo].[Tracker] where [tracker_serial] = \"" + searchForm.getSerialNum() + "\"";
				ResultSet rs = stmt.executeQuery(query);
				System.out.println(query);

				while (rs.next()) {
					i++;
					return new ModelAndView("redirect:/member.htm", "status", "103");
				}
			}

			if (i == 0 && searchForm.getSerialNum().length() > 0) {

				String updateSerial = "UPDATE [ms03].[dbo].[Tracker] SET [tracker_serial] = \"" + searchForm.getSerialNum() + "\" WHERE [sn_imei_id] = \"" + searchForm.getImei() + "\"";
				System.out.println(updateSerial);
				stmt2.executeUpdate(updateSerial);

				return new ModelAndView("redirect:/member.htm", "status", "200");
			}
			else {
				return new ModelAndView("redirect:/member.htm", "status", "104");
			}
		}
		return new ModelAndView("redirect:/");

	}

	public ModelAndView addRealTimeMHD(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String benz = request.getParameter("benz");
		System.out.println("Benz : " + benz);

		if (super.chkSession(request) || benz.equals("OK")) {
			Timer timer = new Timer();
			int initialDelay = 5000;
			int period = 30000;
			TimerTask task = new TimerTask() {
				Connection connect;
				Statement stmt;
				Connection connect2;
				Statement stmt5;
				Statement stmt2;
				Statement stmt3;
				Statement stmt4;
				Statement stmt6;
				String json;
				ArrayList<String> data;
				String tmp_utc_ts;
				String tmp_recv_utc;

				
				public void run() {
					connect = null;
					stmt = null;
					stmt2 = null;
					stmt3 = null;
					stmt4 = null;
					json = null;
					data = new ArrayList();
					tmp_utc_ts = null;
					tmp_recv_utc = null;

					Date d = new Date();
					// timer send gps data to DLT

					try {
						String url = serverIP.getMhdServer();
						// System.out.println("serverIP : " +
						// serverIP.getMhdServer());
						Class.forName("com.mysql.jdbc.Driver");
						connect = DriverManager.getConnection(url);
						stmt = connect.createStatement();
						stmt2 = connect.createStatement();
						stmt3 = connect.createStatement();
						stmt4 = connect.createStatement();

						if (connect != null) {
							System.out.println("Connected!!");

							Locale lc = new Locale("en", "EN");
							SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'", lc);
							SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", lc);

							String initLic = "000000000000000000000000000000000000000000000";
							String driverId = "";
							String license = "";
							String unitId = "";
							String lat = "";
							String lon = "";
							String utc_ts = "2016-01-18T11:00:13.939Z";
							String recv_utc = "2016-01-18T11:00:15.939Z";
							String imei_id = "";
							String rfid = "";
							String driverLic = "";
							String gsm_station_id = "";
							int seq = 0;
							int course = 0;
							int altitude = 0;
							int gps_valid = 0;
							int satellite_number = 0;
							int hdop = 0;
							int gsm_signal = 0;
							int engine_status = 0;
							int speed = 0;
							int max_speed = 0;
							boolean powerStatus = false;
							boolean bat = false;
							int extBat = 0;
							int gsm_cell = 0;
							int gsm_loc = 0;
							int mileage = 0;
							int high_acc_count = 0;
							int high_de_acc_count = 0;
							int over_speed_count = 0;
							int login_status = 0;
							String ad5 = "";
							String rfidTime = null;
							int ext_power_status = 0;
							String gpsLastRfid = "";
							String gpsLastAlarmId = "";
							String alarmHistoryId = "";
							int loginStatus = 0;


							String query = "SELECT  `CarLicence`,`DeviceID`,`Unit_id` FROM `wcms4`.`vehicledevice` WHERE `Unit_id` IS NOT NULL OR `Unit_id` NOT LIKE ''";
							ResultSet rs = stmt.executeQuery(query);
							while (rs.next()) {

								String selectLic = "SELECT `ID`, `DeviceID`, `CardID`, `Type`, `SwipeTime`, `AllStr`, `LicenseNo`, `Sex`, `LicenseType`, `CityNo`, `Acc` "
										+ "FROM `wcms4`.`swipecardlog` WHERE `DeviceID` = '" + rs.getString("DeviceID") + "' ORDER BY `SwipeTime` DESC LIMIT 0, 1;";

								ResultSet rs2 = stmt2.executeQuery(selectLic);
								while (rs2.next()) {

									// System.out.println("DeviceID : " + rs.getString("DeviceID"));

									login_status = (rs2.getInt("type"));
									try {

										String[] allStr = rs2.getString("AllStr").replace("+", "/").split("/");
										driverId = rs2.getString("AllStr").replace(" ", "");

										engine_status = (rs2.getInt("Acc"));

										if (login_status == 0 || engine_status == 0) {
											driverId = "";
											license = "";

										}
										else {

											driverId = allStr[1].replace("?", "").trim().replace(" ", "");
											license = initLic + allStr[1].replace("?", "").replace(" ", "0").trim();
											license = license.substring(0, 110);
											// System.out.println("license2 : "	+ license);

										}
									}
									catch (Exception e) {
										// TODO: handle exception
									}

								}
								String gpsData = getGPSData(rs.getString("DeviceID"));

								String[] arrData = gpsData.split(",");

								String[] arrOnlineLast = getOnlineLast(rs.getString("DeviceID")).split(":");

								Date dateNow = new Date();
								try {
									TimeZone tz = TimeZone.getTimeZone("UTC");
									df.setTimeZone(tz);
									
									unitId = rs.getString("Unit_id");
									String[] arrTime = arrData[16].replace("}]", "").replace("\"", "").split(":");
									String recvTime = arrTime[1] + ":" + arrTime[2] + ":" + arrTime[3];

									String[] arrGPSTime = arrData[8].replace("}]", "").replace("\"", "").split(":");
									String recvGPSTime = arrGPSTime[1] + ":" + arrGPSTime[2] + ":" + arrGPSTime[3];
									utc_ts = df.format(formatter.parse(recvGPSTime));
									recv_utc = df.format(formatter.parse(recvTime));

									lat = arrData[6].split(":")[1];
									lon = arrData[7].split(":")[1];
									altitude = Integer.parseInt(arrData[0].replace("[{", "").split(":")[1]);
									speed = Integer.parseInt(arrData[13].split(":")[1]);
								}
								catch (Exception e) {
									// TODO: handle exception
								}

								int min = 60000;

								if (((Math.abs((Math.abs(df.parse(df.format(new Date())).getTime()) - Math.abs(df.parse(utc_ts).getTime())) / min)) <= 3)) {

									json = "{ \"driver_id\": \"" + driverId + "\", \"unit_id\": \"" + unitId + "\", \"seq\": 0, \"utc_ts\": \"" + utc_ts + "\", \"recv_utc_ts\": \"" + recv_utc
											+ "\", \"lat\": " + lat + ", \"lon\": " + lon + ", \"alt\": " + altitude + ", \"speed\": " + speed + ", \"engine_status\": " + engine_status + ", \"fix\": "
											+ gps_valid + ", \"license\": \"" + license + "\", \"course\": " + course + ", \"hdop\": " + hdop + ", \"num_sats\": " + satellite_number
											+ ", \"gsm_cell\": 0, \"gsm_loc\": 0, \"gsm_rssi\": " + gsm_signal + ", \"mileage\": " + "0" + ", \"ext_power_status\": " + ext_power_status
											+ ", \"ext_power\": " + extBat + ", \"high_acc_count\": null, \"high_de_acc_count\": 0, \"over_speed_count\": 0, \"max_speed\": " + max_speed + " } ";

									try {

										String uri = serverIP.getMysql();
										Class.forName("com.mysql.jdbc.Driver");
										connect2 = DriverManager.getConnection(uri);
										stmt5 = connect2.createStatement();

										String sql = "UPDATE `distar_dlt`.`master_file` SET `dlt_update_time` = '" + formatter.format(new Date()) + "' WHERE `unit_id` = '" + unitId + "';";
										stmt5.executeUpdate(sql);

										connect2.close();

									}
									catch (Exception e) {
										// TODO: handle exception
										System.out.println("sql exception : " + e.getMessage());
									}

									data.add(json);
								}

							}
							boolean tmpMaxSpeed = false;
							byte[] encodedBytes = Base64.encodeBase64((byte[]) serverIP.getUserPassGPS().getBytes());
							DefaultHttpClient httpClient = new DefaultHttpClient();
							HttpPost post = new HttpPost(String.valueOf(serverIP.getUrlRealTime()) + "/gps/add/locations");
							String jsonInit = "{ \"vender_id\": \"043\", \"locations_count\": " + data.size() + ", \"locations\": ";
							Gson gson = new Gson();
							String jsonNew = String.valueOf(jsonInit) + gson.toJson(data).replace("\\", "").replace("[\"", "[").replace("\"]", "]").replace("\",\"", ",") + "}";
//							System.out.println(jsonNew);
							StringEntity postingString = new StringEntity(jsonNew);
							HttpResponse res = null;
							post.setHeader("Authorization", "basic " + new String(encodedBytes));
							post.addHeader("Content-Type", "application/json");

							if (data.size() > 0) {
								post.setEntity((HttpEntity) postingString);
								res = httpClient.execute((HttpUriRequest) post);

								String responseAsString = EntityUtils.toString(res.getEntity());
								System.out.println("MHD response : " + responseAsString);

								connect.close();
								stmt.close();

								String uri = serverIP.getMysql();
								Class.forName("com.mysql.jdbc.Driver");
								connect = DriverManager.getConnection(uri);
								stmt = connect.createStatement();
								
								responseAsString = EntityUtils.toString(res.getEntity());
								System.out.println("response2 : " + responseAsString);

								try {
									String update = "UPDATE `distar_dlt`.`realtime_status` SET `exception` = '" + responseAsString + "', `status` = '"
											+ String.valueOf(res.getStatusLine().getStatusCode()) + "', `time_update` = '" + formatter.format(new Date()) + "', `data_log` = '" + jsonNew + "' WHERE `device` = '4';";
									stmt.executeUpdate(update);
								}
								catch (Exception e) {
									String sql = "INSERT INTO `distar_dlt`.`realtime_status`  (`device`,`status`,`exception`,`time_update`,`data_log`) VALUES ('4','"
											+ String.valueOf(res.getStatusLine().getStatusCode()) + "','" + responseAsString + "','" + formatter.format(new Date()) + "','" + jsonNew + "');";
									stmt.executeUpdate(sql);
								}

								System.out.println("Realtime MHD : " + String.valueOf(res.getStatusLine().getStatusCode()) + "\n");
								data.clear();
							}
						}
					}
					catch (Exception e) {
						System.out.println("DB connection fail!!");
						e.printStackTrace();
						data.clear();
					}

				}
			};
			timer.scheduleAtFixedRate((TimerTask) task, initialDelay, (long) period);
			return new ModelAndView("redirect:/member.htm", "status", "100");

		}
		return new ModelAndView("redirect:/");
	}

	public String getGPSData(String serial) {

		String status = "";
		HttpResponse res = null;

		try {

			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost post = new HttpPost("http://122.155.210.90:12040/gps/last");
//			HttpPost post = new HttpPost("http://202.183.221.87:12040/gps/last");
			post.addHeader("Content-Type", "application/json; charset=UTF-8");
			String requestString = "{\"terminals\":\"" + serial + "\",\"key\":\"123\"}";
			byte[] requestStringBytes = requestString.getBytes("UTF-8");

			StringEntity postingString = new StringEntity(requestString);
			post.setEntity((HttpEntity) postingString);
			res = httpClient.execute((HttpUriRequest) post);

			StringBuffer sb = new StringBuffer();

			String readLine;
			BufferedReader responseReader;
			responseReader = new BufferedReader(new InputStreamReader(res.getEntity().getContent(), "UTF-8"));
			while ((readLine = responseReader.readLine()) != null) {
				sb.append(readLine).append("\n");
			}

//			 System.out.println(sb.toString());
			status = sb.toString();

		}
		catch (Exception e) {
			System.out.println("connection fail!!");
			e.printStackTrace();
		}

		return status;
	}

	public String getOnlineLast(String serial) {
		String status = "";
		HttpResponse res = null;

		try {

			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost post = new HttpPost("http://122.155.210.90:12040/dev/online/last");
//			HttpPost post = new HttpPost("http://202.183.221.87:12040/dev/online/last");
			post.addHeader("Content-Type", "application/json; charset=UTF-8");
			String requestString = "{\"terminals\":\"" + serial + "\",\"key\":\"123\"}";
			byte[] requestStringBytes = requestString.getBytes("UTF-8");

			StringEntity postingString = new StringEntity(requestString);
			post.setEntity((HttpEntity) postingString);
			res = httpClient.execute((HttpUriRequest) post);

			StringBuffer sb = new StringBuffer();

			String readLine;
			BufferedReader responseReader;
			responseReader = new BufferedReader(new InputStreamReader(res.getEntity().getContent(), "UTF-8"));
			while ((readLine = responseReader.readLine()) != null) {
				sb.append(readLine).append("\n");
			}

			// System.out.println(sb.toString());
			status = sb.toString();

		}
		catch (Exception e) {
			System.out.println("connection fail!!");
			e.printStackTrace();
		}

		return status;
	}

	public ModelAndView addMasterFileImport(HttpServletRequest request, HttpServletResponse response, MasterFileForm masterFileForm) throws Exception {
		if (super.chkSession(request)) {

			// User userLogin = (User)
			// request.getSession().getAttribute(UsableController.USER_LOGIN);

			List<ExcelImport> listExcel = excelImportDAO.findAll();

			for (ExcelImport excel : listExcel) {

				String json = "{\"vender_id\": 43 ,\"unit_id\": \"" + excel.getUnit_id() + "\" , \"vehicle_id\" : \"" + excel.getVehicle_id() + "\" , \"vehicle_type\" : \"" + excel.getVehicle_type()
						+ "\"" + " , \"vehicle_chassis_no\": \"" + excel.getVehicle_chassis_no().trim() + "\"" + ", \"vehicle_register_type\": "
						+ excel.getVehicle_register_type().getVehicleRegisterType() + " , \"card_reader\": " + excel.getCard_reader() + " , \"province_code\": "
						+ excel.getProvince_code().getProvinceCode() + "}";

				byte[] encodedBytes = Base64.encodeBase64((byte[]) serverIP.getUserPassMaster().getBytes());
				String url = String.valueOf(serverIP.getUrlMesterFile()) + "/masterfile/add";

				DefaultHttpClient httpClient = new DefaultHttpClient();
				HttpPost post = new HttpPost(url);
				StringEntity postingString = new StringEntity(json);

				final X509Certificate[] _AcceptedIssuers = new X509Certificate[]{};
				SSLContext ctx = SSLContext.getInstance("TLS");
				X509TrustManager tm = new X509TrustManager() {
					
					public X509Certificate[] getAcceptedIssuers() {
						return _AcceptedIssuers;
					}

					
					public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
					}

					
					public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
					}
				};
				ctx.init(null, new TrustManager[]{tm}, new SecureRandom());
				SSLSocketFactory ssf = new SSLSocketFactory(ctx, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
				ClientConnectionManager ccm = httpClient.getConnectionManager();
				SchemeRegistry sr = ccm.getSchemeRegistry();
				sr.register(new Scheme("https", 443, (SchemeSocketFactory) ssf));
				post.setHeader("Authorization", "basic " + new String(encodedBytes));
				post.addHeader("Content-Type", "application/json");
				post.setEntity((HttpEntity) postingString);

				MasterFile masterFile;

				try {
					masterFile = masterFileDAO.findByUnitId(excel.getUnit_id());
					System.out.println("Try : " + masterFile.getUnitId());
				}
				catch (Exception e) {
					// TODO: handle exception
					masterFile = new MasterFile();
					System.out.println("catch");
				}
				// System.out.println("excel.getCard_reader() : " +
				// excel.getCard_reader());

				masterFile.setCardReader(excel.getCard_reader());
				masterFile.setCustomerName(excel.getCustomerName());
				masterFile.setSaleName(excel.getSaleName());
				masterFile.setGpsModel(excel.getGps_model());
				masterFile.setUnitId(excel.getUnit_id());
				masterFile.setProvinceCode(excel.getProvince_code());
				masterFile.setVehicleChassisNo(excel.getVehicle_chassis_no());
				masterFile.setVehicleId(excel.getVehicle_id());
				masterFile.setVehicleRegisterType(excel.getVehicle_register_type());
				masterFile.setVehicleType(excel.getVehicle_type());
				masterFile.setImei(excel.getImei());

				masterFileDAO.merge(masterFile);

				HttpResponse res = httpClient.execute((HttpUriRequest) post);

				String responseAsString = EntityUtils.toString(res.getEntity());
				System.out.println("Master response : " + responseAsString);

				System.out.println("json " + json);

				if (res.getStatusLine().getStatusCode() == 200) {

					ExcelImport excelimport = excelImportDAO.findByUnitId(excel.getUnit_id());
					excelImportDAO.remove(excelimport);

					Connection connect;
					Statement stmt;

					String urlMSSQL = serverIP.getTrackingServer();
					Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
					connect = DriverManager.getConnection(urlMSSQL);
					stmt = connect.createStatement();

					if (excel.getGps_model().equals("0430003")) {

						String query = "update [ms03].[dbo].[GPS_Last] set [unit_id] = '" + excel.getUnit_id() + "' WHERE [sn_imei_id] = '" + excel.getImei() + "'";
						stmt.executeUpdate(query);
					}
					else if (excel.getGps_model().equals("0430005")) {
						try {
							// System.out.println("VehicleId :
							// "+excel.getImei());
							LTYUnitId ltyUnitId = ltyUnitIdDAO.findByVehicleId(excel.getImei());
							ltyUnitId.setUnitId(excel.getUnit_id());
							ltyUnitIdDAO.merge(ltyUnitId);
							// System.out.println("merge");

						}
						catch (Exception e) {

						}

					}
					else {
						System.out.println("gpsModel : " + excel.getGps_model());
					}
				}

				System.out.println("Master file import : " + String.valueOf(res.getStatusLine().getStatusCode()) + "\n");

				Connection connect;
				Statement stmt;

				String uri = serverIP.getMysql();
				Class.forName("com.mysql.jdbc.Driver");
				connect = DriverManager.getConnection(uri);
				stmt = connect.createStatement();

				SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				TimeZone tz = TimeZone.getTimeZone("GMT+07:00");

				dateformat.setTimeZone(tz);
				Date dateNow = new Date();
				String timeUpdate = "";

				try {
					timeUpdate = dateformat.format(dateNow);
					timeUpdate = timeUpdate.replace("2559", "2016");
					timeUpdate = timeUpdate.replace("2560", "2017");

				}
				catch (Exception e) {
					timeUpdate = dateformat.format(dateNow);
				}

				String sql = "INSERT INTO `distar_dlt`.`realtime_status`  (`device`,`status`,`exception`,`time_update`,`data_log`) VALUES ('2','" + String.valueOf(res.getStatusLine().getStatusCode())
						+ "','" + String.valueOf(res) + "','" + timeUpdate + "','" + json + "');";
				stmt.executeUpdate(sql);

			}

			return new ModelAndView("redirect:/member.htm", "status", "200");
		}
		return new ModelAndView("redirect:/");

	}

	public ModelAndView addRealTimeLty(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String benz = null;
		benz = request.getParameter("benz");
		System.out.println("Benz : " + benz);

		if (super.chkSession(request) || benz.equals("OK")) {

			Timer timer = new Timer();
			int initialDelay = 5000;
			int period = 30000;

			Connection connect2;
			final Statement stmt2;

			String uri = serverIP.getMysql();
			Class.forName("com.mysql.jdbc.Driver");
			connect2 = DriverManager.getConnection(uri);
			stmt2 = connect2.createStatement();

			TimerTask task = new TimerTask() {

				String json;
				ArrayList<String> data;
				String initLic = "00000000000000000000000000000000000";
				String driverId = "";
				String license = "";
				String unitId = "";
				String lat = "";
				String lon = "";
				String utc_ts = "2016-01-18T11:00:13.939Z";
				String recv_utc = "2016-01-18T11:00:15.939Z";
				String imei_id = "";
				String rfid = "";
				String driverLic = "";
				String gsm_station_id = "";
				int seq = 0;
				int course = 0;
				int altitude = 0;
				int gps_valid = 0;
				int satellite_number = 0;
				int hdop = 0;
				int gsm_signal = 0;
				String engine_status = "";
				int speed = 0;
				int max_speed = 0;
				boolean powerStatus = false;
				boolean bat = false;
				int extBat = 0;
				int gsm_cell = 0;
				int gsm_loc = 0;
				int mileage = 0;
				int high_acc_count = 0;
				int high_de_acc_count = 0;
				int over_speed_count = 0;
				int login_status = 0;
				String ad5 = "";
				String rfidTime = null;
				int ext_power_status = 0;
				String gpsLastRfid = "";
				String gpsLastAlarmId = "";
				String alarmHistoryId = "";
				int loginStatus = 0;

				
				public void run() {

					System.out.println("Start time : " + new Date());
					data = new ArrayList();
					try {
						String line = getGPSDataLTY();
						SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						// TimeZone tz = TimeZone.getTimeZone("UTC");

						Locale lc = new Locale("en", "EN");
						SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'", lc);
						SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'", lc);
						df2.setTimeZone(TimeZone.getTimeZone("GMT+1400"));
						int min = 60000;
						Date dateNow = new Date();

						String dDLT = "";

						dDLT = df.format(dateNow);

						int i = 1;

						JsonNode arrNode = new ObjectMapper().readTree(line).get("locations");
						if (arrNode.isArray()) {

							for (JsonNode objNode : arrNode) {

								unitId = objNode.get("unitId").getTextValue();
								driverId = objNode.get("driverId").getTextValue();
								altitude = Integer.parseInt(objNode.get("alt").getTextValue());
								lat = objNode.get("lat").getTextValue();
								lon = objNode.get("lon").getTextValue();
								utc_ts = objNode.get("utcTs").getTextValue();
								recv_utc = objNode.get("recvUtcTs").getTextValue();
								speed = Integer.parseInt(objNode.get("speed").getTextValue());
								license = objNode.get("license").getTextValue();
								mileage = Integer.parseInt(objNode.get("mileage").getTextValue());
								engine_status = objNode.get("engineStatus").getTextValue();
								course = Integer.parseInt(objNode.get("course").getTextValue());

								if (unitId.length() > 1) {
									 System.out.println("Time : "+df2.parse(dDLT) + " utc_ts :"+formatter.parse(utc_ts));
									 System.out.println("Line : "+line);
									if ((Math.abs((Math.abs(df2.parse(dDLT).getTime()) - Math.abs(formatter.parse(utc_ts).getTime())) / min)) < 5) {

										try {
											if (!license.equals(null) || !driverId.equals(null)) {

											}
											else {
												driverId = "";
												license = "";
											}
										}
										catch (Exception e) {
											driverId = "";
											license = "";
										}

										try {
											Integer.parseInt(engine_status);
										}
										catch (Exception e) {
											engine_status = "0";

										}

										try {
											SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
											TimeZone tz = TimeZone.getTimeZone("GMT+07:00");

											dateformat.setTimeZone(tz);
											Date dateNow2 = new Date();
											String timeUpdate = "";

											String select = "SELECT * FROM `lty_unitid` WHERE LENGTH(`unit_id`) > 10 AND `vehicle_id` = '" + unitId + "'";
											ResultSet rs = stmt2.executeQuery(select);

											while (rs.next()) {

												if (objNode.get("engineStatus").getTextValue().equals("0")) {
													driverId = "";
													license = "";
												}
												else {
													try {

														license = license.substring(0, 110);
														driverId.replace("null", "");
														license.replace("null", "");

													}
													catch (Exception e) {

													}
												}
												json = "{ \"driver_id\": \"" + driverId + "\", \"unit_id\": \"" + rs.getString("unit_id") + "\", \"seq\": 0, \"utc_ts\": \""
														+ df.format(formatter.parse(utc_ts)) + "\", \"recv_utc_ts\": \"" + df.format(formatter.parse(recv_utc)) + "\", \"lat\": " + lat + ", \"lon\": "
														+ lon + ", \"alt\": " + altitude + ", \"speed\": " + speed + ", \"engine_status\": " + objNode.get("engineStatus").getTextValue()
														+ ", \"fix\": " + gps_valid + ", \"license\": \"" + license + "\", \"course\": " + course + ", \"hdop\": " + hdop + ", \"num_sats\": "
														+ satellite_number + ", \"gsm_cell\": 0, \"gsm_loc\": 0, \"gsm_rssi\": " + gsm_signal + ", \"mileage\": " + mileage + ", \"ext_power_status\": "
														+ ext_power_status + ", \"ext_power\": " + extBat
														+ ", \"high_acc_count\": null, \"high_de_acc_count\": 0, \"over_speed_count\": 0, \"max_speed\": " + max_speed + " } ";
												data.add(json);
											}
											rs.close();
											Connection connect3;
											Statement stmt3;

											TimeZone tz2 = TimeZone.getTimeZone("GMT+07:00");

											SimpleDateFormat dateformat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

											dateformat2.setTimeZone(tz2);

											try {

												String uri = serverIP.getMysql();
												Class.forName("com.mysql.jdbc.Driver");
												connect3 = DriverManager.getConnection(uri);
												stmt3 = connect3.createStatement();

												String sql = "UPDATE `distar_dlt`.`master_file` SET `dlt_update_time` = '" + dateformat.format(dateNow) + "' WHERE `imei` = '" + unitId + "';";
												stmt3.executeUpdate(sql);
												// System.out.println("sql :
												// "+sql);
												connect3.close();

											}
											catch (Exception e) {
												// TODO: handle exception
											}

										}

										catch (Exception e) {
											// TODO: handle exception
											System.out.println("catch add data : " + e.getMessage());
										}
									}
								}

							}

							byte[] encodedBytes = Base64.encodeBase64((byte[]) serverIP.getUserPassGPS().getBytes());
							DefaultHttpClient httpClient = new DefaultHttpClient();
							HttpPost post = new HttpPost(String.valueOf(serverIP.getUrlRealTime()) + "/gps/add/locations");
							String jsonInit = "{ \"vender_id\": \"043\", \"locations_count\": " + data.size() + ", \"locations\": ";
							Gson gson = new Gson();
							String jsonNew = String.valueOf(jsonInit) + gson.toJson(data).replace("\\", "").replace("[\"", "[").replace("\"]", "]").replace("\",\"", ",") + "}";
							// System.out.println(jsonNew);
							StringEntity postingString = new StringEntity(jsonNew);
							HttpResponse res = null;
							post.setHeader("Authorization", "basic " + new String(encodedBytes));
							post.addHeader("Content-Type", "application/json");

							if (data.size() > 0) {
								post.setEntity((HttpEntity) postingString);
								res = httpClient.execute((HttpUriRequest) post);

								System.out.println("Realtime LTY : " + String.valueOf(res.getStatusLine().getStatusCode()) + " Time : " + new Date() + " Data size :" + data.size());

								String responseAsString = EntityUtils.toString(res.getEntity());
								System.out.println("BMTA response : " + responseAsString);

								Connection connect;
								Statement stmt;

								String uri = serverIP.getMysql();
								Class.forName("com.mysql.jdbc.Driver");
								connect = DriverManager.getConnection(uri);
								stmt = connect.createStatement();

								SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", lc);
								TimeZone tz = TimeZone.getTimeZone("GMT+07:00");

								dateformat.setTimeZone(tz);
								Date dateNow2 = new Date();
								String timeUpdate = "";

								timeUpdate = dateformat.format(dateNow2);

								try {
									String update = "UPDATE `distar_dlt`.`realtime_status` SET `exception` = '" + String.valueOf(res) + "', `status` = '"
											+ String.valueOf(res.getStatusLine().getStatusCode()) + "', `time_update` = '" + timeUpdate + "', `data_log` = '" + jsonNew + "' WHERE `device` = '5';";
									stmt.executeUpdate(update);
									connect.close();
								}
								catch (Exception e) {
									String sql = "INSERT INTO `distar_dlt`.`realtime_status`  (`device`,`status`,`exception`,`time_update`,`data_log`) VALUES ('5','"
											+ String.valueOf(res.getStatusLine().getStatusCode()) + "','" + String.valueOf(res) + "','" + timeUpdate + "','" + jsonNew + "');";
									stmt.executeUpdate(sql);
									connect.close();
								}

								data.clear();
							}
						}

					}
					catch (Exception e) {
						System.out.println("catch ext : " + e.getMessage());
					}

				}

			};
			timer.scheduleAtFixedRate((TimerTask) task, initialDelay, (long) period);
			return new ModelAndView("redirect:/member.htm", "status", "100");

		}
		return new ModelAndView("redirect:/");

	}

	public String getGPSDataLTY() {

		String status = "";
		HttpResponse res = null;

		try {

			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost post = new HttpPost(serverIP.getBmtaServer() + "gavernment/getAllDate?key=ge0XQO3zSUmMc3g1OP");
			post.addHeader("Content-Type", "application/json; charset=UTF-8");

			res = httpClient.execute((HttpUriRequest) post);

			StringBuffer sb = new StringBuffer();

			String readLine;
			BufferedReader responseReader;
			responseReader = new BufferedReader(new InputStreamReader(res.getEntity().getContent(), "UTF-8"));
			while ((readLine = responseReader.readLine()) != null) {
				sb.append(readLine).append("\n");
			}

//			 System.out.println(sb.toString());
			status = sb.toString();

		}
		catch (Exception e) {
			System.out.println("connection fail!!");
			e.printStackTrace();
		}

		return status;
	}

	public ModelAndView checkMasterFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (super.chkSession(request)) {
			List<MasterFile> masterFiles = masterFileDAO.findAll();

			Connection connect;
			Statement stmt;

			String urlMSSQL = serverIP.getTrackingServer();
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			connect = DriverManager.getConnection(urlMSSQL);
			stmt = connect.createStatement();

			for (MasterFile mf : masterFiles) {

				if (mf.getGpsModel().equals("0430003")) {

					String update = "UPDATE [dbo].[GPS_Last] SET [unit_id] = '" + mf.getUnitId() + "' WHERE sn_imei_id = '" + mf.getImei() + "' and l_datetime < '2017-03-21 09:00:00.000'";
					stmt.executeUpdate(update);

					System.out.println("IMEI : " + mf.getImei());
				}

			}

			return new ModelAndView("redirect:/member.htm", "status", "200");
		}

		return new ModelAndView("redirect:/");
	}

	public void refreshUnitId(HttpServletRequest request, HttpServletResponse response) {

		String unitId = request.getParameter("unitId");

		try {
			MasterFile masterFile = masterFileDAO.findByUnitId(unitId);

			String urlMSSQL = serverIP.getTrackingServer();
			Connection connect;
			Statement stmt;
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			connect = DriverManager.getConnection(urlMSSQL);
			stmt = connect.createStatement();

			String update = "UPDATE [dbo].[GPS_Last] SET [unit_id] = '" + masterFile.getUnitId() + "' WHERE sn_imei_id = '" + masterFile.getImei() + "'";
			stmt.executeUpdate(update);
			System.out.println(masterFile.getUnitId() + " updated");
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String getGPSDataTP2() {

		String serial = "1004363294";
		String status = "";
		HttpResponse res = null;

		try {

			DefaultHttpClient httpClient = new DefaultHttpClient();
			// HttpPost post = new
			// HttpPost("http://58.247.51.62:52905/EasyManager/gps/last");
			HttpPost post = new HttpPost("http://202.183.221.94:48080/EasyManager/gps/last");
			post.addHeader("Content-Type", "application/json; charset=UTF-8");
			String requestString = "{\"terminals\":\"" + serial + "\",\"key\":\"123\"}";
			byte[] requestStringBytes = requestString.getBytes("UTF-8");

			StringEntity postingString = new StringEntity(requestString);
			post.setEntity((HttpEntity) postingString);
			res = httpClient.execute((HttpUriRequest) post);

			StringBuffer sb = new StringBuffer();

			String readLine;
			BufferedReader responseReader;
			responseReader = new BufferedReader(new InputStreamReader(res.getEntity().getContent(), "UTF-8"));
			while ((readLine = responseReader.readLine()) != null) {
				sb.append(readLine).append("\n");
			}

			System.out.println("getGPSDataTP2 : " + sb.toString());
			status = sb.toString();

		}
		catch (Exception e) {
			System.out.println("connection fail!!");
			e.printStackTrace();
		}

		return status;
	}

	public String getSwipeCardTP2() {

		String serial = "1004363294";
		String status = "";
		HttpResponse res = null;

		try {

			DefaultHttpClient httpClient = new DefaultHttpClient();

			// HttpPost post = new
			// HttpPost("http://58.247.51.62:52905/EasyManager/swipelog/scard");
			HttpPost post = new HttpPost("http://202.183.221.94:48080/EasyManager/swipelog/scard");

			// HttpPost post = new
			// HttpPost("http://58.247.51.62:38080/EasyManager/swipelog/scard");
			post.addHeader("Content-Type", "application/json; charset=UTF-8");
			String requestString = "{\"terminals\":\"" + serial + "\",\"key\":\"123\"}";
			byte[] requestStringBytes = requestString.getBytes("UTF-8");

			StringEntity postingString = new StringEntity(requestString);
			post.setEntity((HttpEntity) postingString);
			res = httpClient.execute((HttpUriRequest) post);

			StringBuffer sb = new StringBuffer();

			String readLine;
			BufferedReader responseReader;
			responseReader = new BufferedReader(new InputStreamReader(res.getEntity().getContent(), "UTF-8"));
			while ((readLine = responseReader.readLine()) != null) {
				sb.append(readLine).append("\n");
			}

			status = sb.toString();

			status = status.replace(",null", "");
			status = status.replace("null,", "");

			System.out.println(status);

			// ============================================
			List<List<Object>> table = new ArrayList<List<Object>>();
			List<Object> row = new ArrayList<Object>();

			int i = 1;
			try {
				JsonNode arrNode = new ObjectMapper().readTree(status.toString()).get("data");
				if (arrNode.isArray()) {

					// for (JsonNode objNode : arrNode) {
					// System.out.println("objNode.size() : " +
					// objNode.size());
					// System.out.println("unit_id :
					// "+objNode.get("unit_id").getTextValue());

					// row.add(objNode.get("altitude").getDoubleValue());
					// row.add(objNode.get("cardID").getTextValue());
					// row.add(objNode.get("deviceID").getTextValue());
					// row.add(objNode.get("rideType").getIntValue());
					// row.add(objNode.get("swipeTime").getTextValue());
					//
					//
					//
					// System.out.println("altitude :
					// "+objNode.get("altitude").getDoubleValue());
					// System.out.println("cardID :
					// "+objNode.get("cardID").getTextValue());
					// System.out.println("deviceID :
					// "+objNode.get("deviceID").getTextValue());
					// System.out.println("rideType :
					// "+objNode.get("rideType").getIntValue());
					// System.out.println("swipeTime :
					// "+objNode.get("swipeTime").getTextValue());
					//
					// i++;
					// }
					// table.add(row);
					// System.out.println("i : "+i);
				}
			}

			catch (Exception e) {
				System.out.println("Null");
			}
			// ============================================

		}
		catch (Exception e) {
			System.out.println("connection fail!!");
			e.printStackTrace();
		}

		return status;
	}

	public ModelAndView addRealTimeTP2(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String benz = null;
		benz = request.getParameter("benz");
		System.out.println("Benz : " + benz);

		if (super.chkSession(request) || benz.equals("OK")) {
			Timer timer = new Timer();
			int initialDelay = 5000;
			int period = 55000;
			TimerTask task = new TimerTask() {
				Connection connect;
				Statement stmt;
				Statement stmt2;
				Statement stmt3;
				Statement stmt4;
				String json;
				ArrayList<String> data;
				String tmp_utc_ts;
				String tmp_recv_utc;

				
				public void run() {

					json = null;
					data = new ArrayList();
					tmp_utc_ts = null;
					tmp_recv_utc = null;

					Date d = new Date();
					// timer send gps data to DLT

					try {
						SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						// TimeZone tz = TimeZone.getTimeZone("UTC");
						SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'");

						// TimeZone zone = TimeZone.getTimeZone("UTC-1400");
						formatter.setTimeZone(TimeZone.getTimeZone("GMT+1400"));

						String initLic = "00000000000000000000000000000000000";
						String driverId = "";
						String license = "";
						String unitId = "043000700000000001004363294";
						String lat = "";
						String lon = "";
						String utc_ts = "2016-01-18T11:00:13.939Z";
						String recv_utc = "2016-01-18T11:00:15.939Z";
						String imei_id = "";
						String rfid = "";
						String driverLic = "";
						String gsm_station_id = "";
						int seq = 0;
						int course = 0;
						int altitude = 0;
						int gps_valid = 0;
						int satellite_number = 0;
						int hdop = 0;
						int gsm_signal = 0;
						int engine_status = 0;
						int speed = 0;
						int max_speed = 0;
						boolean powerStatus = false;
						boolean bat = false;
						int extBat = 0;
						int gsm_cell = 0;
						int gsm_loc = 0;
						int mileage = 0;
						int high_acc_count = 0;
						int high_de_acc_count = 0;
						int over_speed_count = 0;
						int login_status = 0;
						String ad5 = "";
						String rfidTime = null;
						int ext_power_status = 0;
						String gpsLastRfid = "";
						String gpsLastAlarmId = "";
						String alarmHistoryId = "";
						int loginStatus = 0;

						String lic;
						String lastLic;
						try {

							// ============================================

							try {
								int i = 1;
								JsonNode arrNode = new ObjectMapper().readTree(getGPSDataTP2().toString()).get("data");
								if (arrNode.isArray()) {

									for (JsonNode objNode : arrNode) {
										Date dateUtc_ts;
										Date dateRecv_utc;
										dateUtc_ts = formatter.parse(objNode.get("gpsTime").getTextValue());
										dateRecv_utc = formatter.parse(objNode.get("time").getTextValue());

										Double altitudeD = objNode.get("altitude").getDoubleValue();
										altitude = altitudeD.intValue();
										course = objNode.get("direction").getIntValue();
										lat = objNode.get("gpsLat").getDoubleValue() + "";
										lon = objNode.get("gpsLng").getDoubleValue() + "";
										utc_ts = df.format(dateUtc_ts);
										recv_utc = df.format(dateRecv_utc);
										max_speed = objNode.get("recordSpeed").getIntValue();
										speed = objNode.get("speed").getIntValue();

										System.out.println("State : " + objNode.get("state").getIntValue());
										// System.out.println(Integer.toBinaryString(objNode.get("state").getIntValue()));

										if (Integer.toBinaryString(objNode.get("state").getIntValue()).length() > 1) {
											engine_status = Integer.parseInt(Integer.toBinaryString(objNode.get("state").getIntValue()).subSequence(12, 13) + "");
											System.out.println("engine_status : " + Integer.toBinaryString(objNode.get("state").getIntValue()).subSequence(12, 13));
										}
										i++;
									}
									// System.out.println("i : "+i);
								}

								int j = 1;
								JsonNode arrNodeSwipeCard = new ObjectMapper().readTree(getSwipeCardTP2().toString()).get("data");
								if (arrNode.isArray()) {

									for (JsonNode objNode2 : arrNodeSwipeCard) {
										driverId = objNode2.get("cardID").getTextValue();
										loginStatus = objNode2.get("rideType").getIntValue();

										initLic = "00000000000000000000000000000000000000000000000";
										// lic = rfid.replace(" ", "0");
										lastLic = "0000000000000000000000";
										login_status = objNode2.get("rideType").getIntValue();

										System.out.println("rideType : " + objNode2.get("rideType").getIntValue());

										if (driverId.length() == 15) {

											license = driverId.subSequence(0, 2) + "000000000000" + driverId.subSequence(2, 3) + "000000000000" + driverId.subSequence(3, 10) + "00"
													+ driverId.subSequence(10, 15);
											license = initLic + license + lastLic;
											license = license.substring(0, 110);

										}
										else if (driverId.length() == 18) {

											license = driverId.subSequence(0, 4) + "000000000000" + driverId.subSequence(4, 5) + "000000000000" + driverId.subSequence(5, 13) + "00"
													+ driverId.subSequence(13, 18);
											license = initLic + license + lastLic;
											license = license.substring(0, 110);
										}
										if (Double.parseDouble(lat) > 0) {
											gps_valid = 1;
										}

										if (login_status == 0 || engine_status == 0) {
											license = "";
											driverId = "";
										}

										j++;
									}

								}
							}

							catch (Exception e) {
								System.out.println("Exception : " + e.getMessage());
							}
							// ============================================

						}

						catch (Exception e) {
							// System.out.println("Null");
						}

						json = "{ \"driver_id\": \"" + driverId + "\", \"unit_id\": \"" + unitId + "\", \"seq\": 0, \"utc_ts\": \"" + utc_ts + "\", \"recv_utc_ts\": \"" + utc_ts + "\", \"lat\": "
								+ lat + ", \"lon\": " + lon + ", \"alt\": " + altitude + ", \"speed\": " + speed + ", \"engine_status\": " + engine_status + ", \"fix\": " + gps_valid
								+ ", \"license\": \"" + license + "\", \"course\": " + course + ", \"hdop\": " + hdop + ", \"num_sats\": " + satellite_number
								+ ", \"gsm_cell\": 0, \"gsm_loc\": 0, \"gsm_rssi\": " + gsm_signal + ", \"mileage\": " + "0" + ", \"ext_power_status\": " + ext_power_status + ", \"ext_power\": "
								+ extBat + ", \"high_acc_count\": null, \"high_de_acc_count\": 0, \"over_speed_count\": 0, \"max_speed\": " + max_speed + " } ";

						data.add(json);
						// System.out.println("Server IP :
						// "+serverIP.getUrlRealTime());
						boolean tmpMaxSpeed = false;
						byte[] encodedBytes = Base64.encodeBase64((byte[]) serverIP.getUserPassGPS().getBytes());
						DefaultHttpClient httpClient = new DefaultHttpClient();
						HttpPost post = new HttpPost(String.valueOf(serverIP.getUrlRealTime()) + "/gps/add/locations");
						String jsonInit = "{ \"vender_id\": \"043\", \"locations_count\": " + data.size() + ", \"locations\": ";
						Gson gson = new Gson();
						String jsonNew = String.valueOf(jsonInit) + gson.toJson(data).replace("\\", "").replace("[\"", "[").replace("\"]", "]").replace("\",\"", ",") + "}";
						System.out.println(jsonNew);
						StringEntity postingString = new StringEntity(jsonNew);
						HttpResponse res = null;
						post.setHeader("Authorization", "basic " + new String(encodedBytes));
						post.addHeader("Content-Type", "application/json");
						if (data.size() > 0) {
							post.setEntity((HttpEntity) postingString);
							res = httpClient.execute((HttpUriRequest) post);

							String responseAsString = EntityUtils.toString(res.getEntity());
							System.out.println("TP2 response : " + responseAsString);

							System.out.println("Realtime TP2 : " + String.valueOf(res.getStatusLine().getStatusCode()) + "\n");
							data.clear();

						}
					}
					catch (Exception e) {
						System.out.println("connection fail!!");
						e.printStackTrace();
						data.clear();
					}

				}
			};
			timer.scheduleAtFixedRate((TimerTask) task, initialDelay, (long) period);
			return new ModelAndView("redirect:/member.htm", "status", "100");
		}
		return new ModelAndView("redirect:/");
	}

	public ModelAndView updateUnitIdLty(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String data = getGPSDataLTY();
		String status = "";
		String engineStatus = "";
		String unitId = "";
		String utc_ts = "2016-01-18T11:00:13.939Z";
		int speed = 0;
		String driverId = "";

		int loginStatus = 0;

		try {
			JsonNode arrNode = new ObjectMapper().readTree(data).get("locations");

			Connection connect;
			Connection connect2;
			Statement stmt;
			Statement stmt2;

			String uri = serverIP.getMysql();
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection(uri);
			connect2 = DriverManager.getConnection(uri);
			stmt = connect.createStatement();
			stmt2 = connect2.createStatement();

			if (arrNode.isArray()) {

				for (JsonNode objNode : arrNode) {
					unitId = objNode.get("unitId").getTextValue();
					utc_ts = objNode.get("utcTs").getTextValue();
					speed = Integer.parseInt(objNode.get("speed").getTextValue());
					driverId = objNode.get("driverId").getTextValue();
					engineStatus = objNode.get("engineStatus").getTextValue();
					try {
						Integer.parseInt(engineStatus);
					}
					catch (Exception e) {
						engineStatus = "0";
					}

					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					TimeZone tz = TimeZone.getTimeZone("GMT+7");
					TimeZone tz2 = TimeZone.getTimeZone("GMT+14");
					df.setTimeZone(tz);
					df2.setTimeZone(tz2);

					try {
						String select = "SELECT * FROM `lty_unitid` WHERE `vehicle_id` = '" + unitId + "'";
						ResultSet rs = stmt2.executeQuery(select);

						int i = 0;
						while (rs.next()) {

							String sql = "UPDATE `distar_dlt`.`lty_unitid` SET `time_update` = '" + df2.format(df.parse(utc_ts)) + "',`speed` = '" + speed + "',`driver_id` = '" + driverId
									+ "',`engine_status` = '" + engineStatus + "' WHERE `vehicle_id` = '" + unitId + "'";
							stmt2.executeUpdate(sql);
							i++;
						}

						if (i == 0) {
							String sql = "INSERT INTO `distar_dlt`.`lty_unitid` (`time_update`,`vehicle_id`,`speed`,`driver_id`,`engine_status`) VALUES ('" + df2.format(df.parse(utc_ts)) + "','"
									+ unitId + "','" + speed + "','" + driverId + "','" + engineStatus + "');";
							stmt.executeUpdate(sql);
						}

					}
					catch (Exception e) {
						System.out.println("Exception 1 : " + e.getMessage());
					}
				}
			}
		}
		catch (Exception e) {
			System.out.println("Exception out 1 : " + e.getMessage());
		}
		return new ModelAndView("redirect:/member.htm", "status", "200");

	}

	private String printBinaryform(int number) {
		int remainder;
		String status = "";

		int binary[] = new int[25];
		int index = 0;
		while (number > 0) {
			binary[index++] = number % 2;
			number = number / 2;
		}
		for (int i = index - 1; i >= 0; i--) {
			System.out.print(binary[i]);
		}

		// status = "binary";

		return status;

	}

	public ModelAndView setNullStatus(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (super.chkSession(request) || request.getParameter("benz").equals("OK")) {
			Connection connect;
			Statement stmt;

			String uri = serverIP.getMysql();
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection(uri);
			stmt = connect.createStatement();

			String sql = "UPDATE `distar_dlt`.`realtime_status` SET `data_log` = NULL WHERE `id` > 0;";
			stmt.executeUpdate(sql);

		}
		return new ModelAndView("redirect:/");

	}

	public ModelAndView checkDTKUser(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Connection connect;
		Statement stmt;

		String uri = "jdbc:sqlserver://122.155.210.74;databaseName=ms03;user=distartech;password=Password@distar";
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		connect = DriverManager.getConnection(uri);
		stmt = connect.createStatement();

		String select = "SELECT [user_account] ,[user_password] ,[user_name]  ,[user_address] ,[user_register_time] ,[user_expired_time] ,[user_remark] FROM [ms03].[dbo].[User_Info]";
		ResultSet rs = stmt.executeQuery(select);

		List<List<Object>> table = new ArrayList<List<Object>>();
		List<Object> row = new ArrayList<Object>();

		int i = 0;
		while (rs.next()) {
			i++;

			row.add(i);
			row.add(rs.getString("user_account"));
			row.add(rs.getString("user_password"));
			row.add(rs.getString("user_name"));
			row.add(rs.getString("user_address"));
			row.add(rs.getString("user_register_time"));
			row.add(rs.getString("user_expired_time"));
			row.add(rs.getString("user_remark"));

		}

		table.add(row);

		ModelMap modelMap = new ModelMap();
		modelMap.addAttribute("table", table);
		modelMap.addAttribute("row", row);
		modelMap.addAttribute("count", i);

		// System.out.println("user size : "+ i);

		// return new ModelAndView("redirect:/service/updateUnitIdLty.htm");
		return new ModelAndView("/service/listDTKUser", modelMap);
	}

	public ModelAndView editDevice(HttpServletRequest request, HttpServletResponse response) throws Exception {

		if (super.chkSession(request)) {

			String imei = request.getParameter("imei");

			Connection connect;
			Statement stmt;

			String uri = serverIP.getTrackingServer();
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			connect = DriverManager.getConnection(uri);
			stmt = connect.createStatement();

			String select = "SELECT [sn_imei_id] ,[tracker_password] ,[tracker_name] ,[tracker_sim_number] ,[tracker_register_time] ,[tracker_expired_time] FROM [ms03].[dbo].[Tracker] where [sn_imei_id] = '"
					+ imei + "'";
			ResultSet rs = stmt.executeQuery(select);

			MasterFileForm masterFileForm = new MasterFileForm();

			while (rs.next()) {

				masterFileForm.setImei(rs.getString("sn_imei_id"));
				masterFileForm.setTracker_password(rs.getString("tracker_password"));
				masterFileForm.setTracker_name(rs.getString("tracker_name"));
				masterFileForm.setTracker_sim_number(rs.getString("tracker_sim_number"));
				masterFileForm.setTracker_register_time(rs.getString("tracker_register_time"));
				masterFileForm.setTracker_expired_time(rs.getString("tracker_expired_time"));
				masterFileForm.setTimeBefore(rs.getString("tracker_expired_time"));
			}

			ModelMap modelMap = new ModelMap();
			modelMap.addAttribute("masterFileForm", masterFileForm);

			// System.out.println("user size : "+ i);

			// return new ModelAndView("redirect:/service/updateUnitIdLty.htm");
			return new ModelAndView("/service/editDevice", modelMap);
		}
		return new ModelAndView("redirect:/");

	}

	public ModelAndView resEditDevice(HttpServletRequest request, HttpServletResponse response, MasterFileForm masterFileForm) throws Exception {

		if (super.chkSession(request)) {

			User userLogin = (User) request.getSession().getAttribute(UsableController.USER_LOGIN);

			MALog maLog = new MALog();

			// System.out.println("TimeBefore : " +
			// masterFileForm.getTimeBefore());
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Date dateBefore = null;
			Date dateAfter = null;
			String timeBefore = null;
			String timeExpire = null;
			try {
				System.out.println("dateBefore : " + masterFileForm.getTimeBefore());
				System.out.println("dateAfter : " + masterFileForm.getTracker_expired_time());

				// dateBefore =
				// df.parse(df.format(masterFileForm.getTimeBefore()));
				// dateAfter =
				// df.parse(df.format(masterFileForm.getTracker_expired_time()));
				// System.out.println("installDate2 :
				// "+dateformat.parse(masterFileForm.getDateInstall()));

				String[] dateBef = masterFileForm.getTimeBefore().toString().split("-");
				String[] dateAft = masterFileForm.getTracker_expired_time().toString().split("-");
				int yearBef = Integer.parseInt(dateBef[0]);
				int yearAft = Integer.parseInt(dateAft[0]);

				if (dateBef[0].startsWith("20") || dateBef[0].startsWith("20")) {

					yearBef += 543;
					yearAft += 543;

					timeBefore = yearBef + "-" + dateBef[1] + "-" + dateBef[2];
					timeExpire = yearAft + "-" + dateAft[1] + "-" + dateAft[2];

					dateBefore = df.parse(timeBefore);
					dateAfter = df.parse(timeExpire);

				}
				else if (dateBef[0].startsWith("14") || dateBef[0].startsWith("14")) {

					yearBef += 1086;
					yearAft += 1086;

					timeBefore = yearBef + "-" + dateBef[1] + "-" + dateBef[2];
					timeExpire = yearAft + "-" + dateAft[1] + "-" + dateAft[2];

					dateBefore = df.parse(timeBefore);
					dateAfter = df.parse(timeExpire);

				}
				else if (dateBef[0].startsWith("25") || dateBef[0].startsWith("25")) {

					yearBef -= 543;
					yearAft -= 543;

					timeBefore = yearBef + "-" + dateBef[1] + "-" + dateBef[2];
					timeExpire = yearAft + "-" + dateAft[1] + "-" + dateAft[2];

					dateBefore = df.parse(timeBefore);
					dateAfter = df.parse(timeExpire);

				}

			}
			catch (Exception e) {
				// TODO: handle exception
				System.out.println("installDate2 exception: " + e.getMessage());

				timeBefore = df.format(masterFileForm.getTimeBefore());
				timeExpire = df.format(masterFileForm.getTracker_expired_time());
			}

			// 2017-06-08 10:14:59
			Date tBefore = null;
			Date texpire = null;
			try {
				tBefore = df.parse(timeBefore);
				texpire = df.parse(timeExpire);

			}
			catch (Exception e) {
				e.printStackTrace();
			}

			maLog.setImei(masterFileForm.getImei());
			maLog.setTimeBefore(tBefore);
			maLog.setTimeExpire(texpire);
			maLog.setTimeUpdate(new Date());
			maLog.setUserUpdate(userLogin);
			maLog.setTrackerName(masterFileForm.getTracker_name());
			maLogDAO.persist(maLog);

			Connection connect;
			Statement stmt;

			String uri = serverIP.getTrackingServer();
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			connect = DriverManager.getConnection(uri);
			stmt = connect.createStatement();

			String update = "UPDATE [dbo].[Tracker]  SET [tracker_expired_time] = '" + masterFileForm.getTracker_expired_time() + "' WHERE sn_imei_id = '" + masterFileForm.getImei() + "'";

			stmt.executeUpdate(update);

			ModelMap modelMap = new ModelMap();
			modelMap.addAttribute("masterFileForm", masterFileForm);

			return new ModelAndView("redirect:../service/checkDTKDevice.htm");
		}
		return new ModelAndView("redirect:/");

	}

	public ModelAndView checkDTKDevice(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Connection connect;
		Statement stmt;

		String uri = serverIP.getTrackingServer();
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		connect = DriverManager.getConnection(uri);
		stmt = connect.createStatement();

		String select = "SELECT [sn_imei_id] ,[tracker_password] ,[tracker_name] ,[tracker_sim_number] ,[tracker_register_time] ,[tracker_expired_time] FROM [ms03].[dbo].[Tracker] where tracker_type_id = '81'";
		ResultSet rs = stmt.executeQuery(select);

		List<List<Object>> table = new ArrayList<List<Object>>();
		List<Object> row = new ArrayList<Object>();

		int i = 0;
		while (rs.next()) {
			i++;

			row.add(i);
			row.add(rs.getString("sn_imei_id"));
			row.add(rs.getString("tracker_password"));
			row.add(rs.getString("tracker_name"));
			row.add(rs.getString("tracker_sim_number"));
			row.add(rs.getString("tracker_register_time"));
			row.add(rs.getString("tracker_expired_time"));
		}

		table.add(row);

		ModelMap modelMap = new ModelMap();
		modelMap.addAttribute("table", table);
		modelMap.addAttribute("row", row);
		modelMap.addAttribute("count", i);

		// System.out.println("user size : "+ i);

		// return new ModelAndView("redirect:/service/updateUnitIdLty.htm");
		return new ModelAndView("/service/listDTKDevice", modelMap);
	}

	public ModelAndView checkMHDStatus(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Connection connect;
		Statement stmt;

		Connection connect2;
		Statement stmt2;

		String uri = serverIP.getMhdServer();
		Class.forName("com.mysql.jdbc.Driver");
		connect = DriverManager.getConnection(uri);
		stmt = connect.createStatement();

		connect2 = DriverManager.getConnection(uri);
		stmt2 = connect2.createStatement();

		String select = "SELECT `CarLicence`, `DeviceID`, `Unit_id` FROM `vehicledevice` ";
		ResultSet rs = stmt.executeQuery(select);

		List<List<Object>> table = new ArrayList<List<Object>>();
		List<Object> row = new ArrayList<Object>();
		String allStr = "";

		int i = 0;

		while (rs.next()) {
			i++;
			int j = 0;
			String selectLicense = "SELECT   `DeviceID`, `Type`, `SwipeTime`, `Acc`, `Valid`, `AllStr` FROM `swipecardlog` WHERE `DeviceID` = '" + rs.getString("DeviceID")
					+ "' ORDER BY `SwipeTime` DESC LIMIT 0,1; ";
			ResultSet rs2 = stmt2.executeQuery(selectLicense);

			row.add(i);
			row.add(rs.getString("CarLicence"));
			row.add(rs.getString("DeviceID"));

			while (rs2.next()) {
				allStr = rs2.getString("AllStr");
				// System.out.println("allStr : "+allStr);
				try {
					String name = allStr.split(";")[0].replace("^", "").replace("%", "").replace("?", "").replace("$", ";");
					String license = allStr.split(";")[1];
					// System.out.println("driverName :
					// "+name.split(";")[2].trim()
					// +name.split(";")[1]+name.split(";")[0]);
					// System.out.println("license : "+license.replace("?",
					// ";").split(";")[1].replace("+", "").replace(" ",
					// "").trim());
					String driverName = name.split(";")[2].trim() + name.split(";")[1] + name.split(";")[0];

					row.add(driverName);
					row.add(license.replace("?", ";").split(";")[1].replace("+", "").replace(" ", "").trim());
				}
				catch (Exception e) {
					// TODO: handle exception
					System.out.println("license catch: " + e.getMessage());
				}
				j = 1;
				row.add(rs2.getString("Type"));
				row.add(rs2.getString("SwipeTime"));
			}
			if (j == 0) {
				row.add("");
				row.add("");
				row.add("");
				row.add("");
			}
		}

		table.add(row);

		ModelMap modelMap = new ModelMap();
		modelMap.addAttribute("table", table);
		modelMap.addAttribute("row", row);
		modelMap.addAttribute("count", i);

		// System.out.println("user size : "+ i);

		// return new ModelAndView("redirect:/service/updateUnitIdLty.htm");
		return new ModelAndView("/service/listMHDDevice", modelMap);
	}

	public ModelAndView checkBDStatus(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Connection connect;
		Statement stmt;

		Connection connect2;
		Statement stmt2;

		String uri = serverIP.getMdvrServer();
		Class.forName("com.mysql.jdbc.Driver");
		connect = DriverManager.getConnection(uri);
		stmt = connect.createStatement();

		connect2 = DriverManager.getConnection(uri);
		stmt2 = connect2.createStatement();

		String select = "SELECT `DevIDNO`,`ArmDesc`,`driver_id`,`unitId`,`status`,`swipeTime`,`accStatus`,`accTime` FROM `1010gps`.`driver_log` WHERE `unitId` IS NOT NULL ";
		ResultSet rs = stmt.executeQuery(select);

		List<List<Object>> table = new ArrayList<List<Object>>();
		List<Object> row = new ArrayList<Object>();
		String allStr = "";
		String deviceName = "";
		String swipeTime = null;
		String accStatus = null;
		String accTime = null;
		Date swipeTimeD = null;
		Date accTimeD = null;

		int i = 0;

		while (rs.next()) {
			String selectAccount = "SELECT `Name`,`Account` FROM `1010gps`.`account` WHERE `Account` = '" + rs.getString("DevIDNO") + "'";
			ResultSet rs2 = stmt2.executeQuery(selectAccount);
			while (rs2.next()) {
				deviceName = rs2.getString("Name");
			}

			i++;
			int j = 0;
			allStr = rs.getString("ArmDesc");

			row.add(i);
			row.add(deviceName);
			row.add(rs.getString("DevIDNO") + " / " + rs.getString("unitId"));

			// System.out.println("Engine status : " +
			// rs.getString("accStatus"));

			try {
				String name = allStr.replace("^^?", "-").split("-")[0].replace("^", "").replace("$", ";");

				// System.out.println("");
				// System.out.print("DevIDNO : " + rs.getString("DevIDNO") +"
				// ");

				String license = allStr.split("=")[2].replace(" ", "").replace("?", "");

				// System.out.println("license: " + license);

				try {
					String driverName = name.split(";")[2].trim() + name.split(";")[1] + name.split(";")[0];

					// System.out.println("driverName: " + driverName);

					row.add(driverName);
				}
				catch (Exception e) {
					row.add("");
				}

				try {
					if (license.length() > 18) {
						row.add(license.substring(0, 15));
					}
					else if (license.length() == 18 || license.length() == 15) {
						row.add(license);
					}
				}
				catch (Exception e) {
					row.add("");
				}
			}
			catch (Exception e) {
				// TODO: handle exception
				row.add("");
				row.add("");
				// System.out.println("license catch: " + e.getMessage());
			}

			swipeTime = rs.getString("swipeTime");
			accStatus = rs.getString("accStatus");
			accTime = rs.getString("accTime");

			SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			swipeTimeD = dateformat.parse(swipeTime);
			accTimeD = dateformat.parse(accTime);

			String status = "0";
			if (accStatus.equals("16") && swipeTimeD.after(accTimeD)) {

				status = "1";

				row.add(status);
				row.add(rs.getString("SwipeTime"));

			}
			else {
				row.add(status);
				row.add(rs.getString("SwipeTime"));
			}

		}

		table.add(row);

		ModelMap modelMap = new ModelMap();
		modelMap.addAttribute("table", table);
		modelMap.addAttribute("row", row);
		modelMap.addAttribute("count", i);

		// System.out.println("user size : "+ i);

		// return new ModelAndView("redirect:/service/updateUnitIdLty.htm");
		return new ModelAndView("/service/listMDVRDevice", modelMap);
	}

	public ModelAndView checkDriverLTY(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// updateDriverLTY();

		SearchForm searchForm = new SearchForm();
		ModelMap modelMap = new ModelMap();
		modelMap.addAttribute("searchForm", searchForm);

		// return new ModelAndView("redirect:/service/updateUnitIdLty.htm");
		return new ModelAndView("/service/checkDriverLTY", modelMap);
	}

	public String updateDriverLTY() {

		String data = getGPSDataLTY();
		String status = "";
		String engineStatus = "";
		String unitId = "";
		String utc_ts = "2016-01-18T11:00:13.939Z";
		int speed = 0;
		String driverId = "";

		int loginStatus = 0;

		try {
			JsonNode arrNode = new ObjectMapper().readTree(data).get("locations");

			Connection connect;
			Connection connect2;
			Statement stmt;
			Statement stmt2;

			String uri = serverIP.getMysql();
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection(uri);
			connect2 = DriverManager.getConnection(uri);
			stmt = connect.createStatement();
			stmt2 = connect2.createStatement();

			if (arrNode.isArray()) {

				for (JsonNode objNode : arrNode) {
					connect = DriverManager.getConnection(uri);
					connect2 = DriverManager.getConnection(uri);
					stmt = connect.createStatement();
					stmt2 = connect2.createStatement();

					unitId = objNode.get("unitId").getTextValue();
					utc_ts = objNode.get("utcTs").getTextValue();
					speed = Integer.parseInt(objNode.get("speed").getTextValue());
					driverId = objNode.get("driverId").getTextValue();
					engineStatus = objNode.get("engineStatus").getTextValue();
					try {
						Integer.parseInt(engineStatus);
					}
					catch (Exception e) {
						engineStatus = "0";
					}

					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					TimeZone tz = TimeZone.getTimeZone("GMT+7");
					TimeZone tz2 = TimeZone.getTimeZone("GMT+14");
					df.setTimeZone(tz);
					df2.setTimeZone(tz2);

					try {
						String select = "SELECT * FROM `lty_unitid` WHERE `vehicle_id` = '" + unitId + "'";
						ResultSet rs = stmt2.executeQuery(select);

						int i = 0;
						while (rs.next()) {

							String sql = "UPDATE `distar_dlt`.`lty_unitid` SET `time_update` = '" + df2.format(df.parse(utc_ts)) + "',`speed` = '" + speed + "',`driver_id` = '" + driverId
									+ "',`engine_status` = '" + engineStatus + "' WHERE `vehicle_id` = '" + unitId + "'";
							stmt2.executeUpdate(sql);
							i++;
						}

						if (i == 0) {
							String sql = "INSERT INTO `distar_dlt`.`lty_unitid` (`time_update`,`vehicle_id`,`speed`,`driver_id`,`engine_status`) VALUES ('" + df2.format(df.parse(utc_ts)) + "','"
									+ unitId + "','" + speed + "','" + driverId + "','" + engineStatus + "');";
							stmt.executeUpdate(sql);
						}

						status = "ok";

						connect.close();
						connect2.close();
						stmt.close();
						stmt2.close();

					}
					catch (Exception e) {
						System.out.println("Exception : " + e.getMessage());
					}
				}
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			System.out.println("Exception out : " + e.getMessage());
		}

		System.out.println("status : " + status);

		return status;
	}

//	public ModelAndView resCheckDriverLTY(HttpServletRequest request, HttpServletResponse response, SearchForm searchForm) throws Exception {
//
//		String status = "";
//		// updateDriverLTY();
//		 System.out.println("Vehicle num : "+searchForm.getImei() + " : "+updateDriverLTY());
//		LTYUnitId ltyUnitId = null;
//		String data = getGPSDataLTY();
////		 System.out.println( "data : "+ data);
//		String engineStatus = "";
//		String unitId = "";
//		String utc_ts = "2016-01-18T11:00:13.939Z";
//		int speed = 0;
//		String driverId = "";
//		String lat = "0.0";
//		String lon = "0.0";
//		String numStas = "0";
//		
//		System.out.println("searchForm.getImei() : "+searchForm.getImei());
//
//		try {
//			JsonNode arrNode = new ObjectMapper().readTree(data).get("locations");
//
//			if (arrNode.isArray()) {
//
//				for (JsonNode objNode : arrNode) {
//
//					unitId = objNode.get("unitId").getTextValue();
//
//					if (searchForm.getImei().equals(unitId)) {
//						utc_ts = objNode.get("utcTs").getTextValue();
//						speed = Integer.parseInt(objNode.get("speed").getTextValue());
//						driverId = objNode.get("driverId").getTextValue();
//						engineStatus = objNode.get("engineStatus").getTextValue();
//						lat = objNode.get("lat").getTextValue();
//						lon = objNode.get("lon").getTextValue();
//						numStas = objNode.get("numStas").getTextValue();
//						System.out.println("numStas : "+numStas);
//						try {
//							Integer.parseInt(engineStatus);
//						}
//						catch (Exception e) {
//							engineStatus = "0";
//						}
//					}
//
//				}
//			}
//		}
//		catch (Exception e) {
//			// TODO: handle exception
//			System.out.println("Exception out : " + e.getMessage());
//		}
//
//		ModelMap modelMap = new ModelMap();
//
//		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		TimeZone tz = TimeZone.getTimeZone("GMT+7");
//		TimeZone tz2 = TimeZone.getTimeZone("GMT+14");
//		df.setTimeZone(tz);
//		df2.setTimeZone(tz2);
//
//		// System.out.println("VehicleNum : "+searchForm.getImei() + " : "+
//		// unitId);
//
//		try {
//			ltyUnitId = ltyUnitIdDAO.findByVehicleId(searchForm.getImei());
//
//			modelMap.addAttribute("status", status);
//			modelMap.addAttribute("vehicle", searchForm.getImei());
//			modelMap.addAttribute("driverId", driverId);
//			modelMap.addAttribute("engineStatus", engineStatus);
//			modelMap.addAttribute("timeUpdate", df2.format(df.parse(utc_ts)));
//			modelMap.addAttribute("searchForm", searchForm);
//			modelMap.addAttribute("lat", lat);
//			modelMap.addAttribute("lon", lon);
//			modelMap.addAttribute("numStas", numStas);
//		}
//		catch (Exception e) {
//			System.out.println("Exception : " + e.getMessage());
//
//			modelMap.addAttribute("status", "ไม่พบข้อมูล");
//			modelMap.addAttribute("vehicle", "");
//			modelMap.addAttribute("driverId", "");
//			modelMap.addAttribute("engineStatus", "");
//			modelMap.addAttribute("timeUpdate", "");
//			modelMap.addAttribute("searchForm", searchForm);
//			modelMap.addAttribute("lat", lat);
//			modelMap.addAttribute("lon", lon);
//			modelMap.addAttribute("numStas", numStas);
//
//		}
//
//		// return new ModelAndView("redirect:/service/updateUnitIdLty.htm");
//		return new ModelAndView("/service/checkDriverLTY", modelMap);
//	}

	public ModelAndView getDataTG(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String benz = request.getParameter("benz");
		System.out.println("Benz : " + benz);

		if (super.chkSession(request) || benz.equals("OK")) {

			HttpResponse res = null;

			try {
				Timestamp timestamp = new Timestamp(System.currentTimeMillis());
				System.out.println("timestamp : " + timestamp.getTime());

				String password = md5Str("888888");
				String token = decryptToken("V5lyF+M5m698xfzfcIOqvJHra6qk/ljPkgV8D33kfnB4VtXe9DhBC82NQEraDndj", 1521441213);
				String sign = getUrlSign("http://www.tgtrack.com/pass/datamotor/api/car", token, 1521427353);
				String status = "";

				System.out.println("sign : " + new Timestamp(timestamp.getTime()));

				DefaultHttpClient httpClient = new DefaultHttpClient();
				HttpPost post = new HttpPost("http://www.tgtrack.com/pass/datamotor/api/car?uid=e1b2b96d-a7f0-4a7c-b8e3-f36ffb911a3f&time=1521448482&sign=" + sign);
				// HttpPost post = new HttpPost(sign);
				post.addHeader("Content-Type", "application/json; charset=UTF-8");
				// String requestString = "{\"uid\":\"e1b2b96d-a7f0-4a7c-b8e3-
				// f36ffb911a3f}";
				// byte[] requestStringBytes = requestString.getBytes("UTF-8");

				// StringEntity postingString = new StringEntity(requestString);
				// post.setEntity((HttpEntity) postingString);
				res = httpClient.execute((HttpUriRequest) post);

				System.out.println("res : " + res.toString());

				StringBuffer sb = new StringBuffer();

				String readLine;
				BufferedReader responseReader;
				responseReader = new BufferedReader(new InputStreamReader(res.getEntity().getContent(), "UTF-8"));
				while ((readLine = responseReader.readLine()) != null) {
					sb.append(readLine).append("\n");
				}

				System.out.println("getGPSDataTG : " + sb.toString());
				status = sb.toString();

			}
			catch (Exception e) {
				System.out.println("connection fail!!");
				e.printStackTrace();
			}

			// return status;

		}
		return new ModelAndView("redirect:/");
	}

	public static byte[] encryptAES(String content, String skey) {
		try {
			byte[] keybyte = Arrays.copyOf(skey.getBytes("utf-8"), 16);
			SecretKeySpec key = new SecretKeySpec(keybyte, "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			// Using CBC mode, a vector iv is needed to increase the strength of
			// the encryption algorithm ECB does not need the following line
			IvParameterSpec iv = new IvParameterSpec("0192939495969798".getBytes());
			cipher.init(Cipher.ENCRYPT_MODE, key, iv);
			byte[] result = cipher.doFinal(content.getBytes("utf-8"));
			return result;

		}
		catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		catch (NoSuchPaddingException e) {
			e.printStackTrace();
		}
		catch (InvalidKeyException e) {
			e.printStackTrace();
		}
		catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		}
		catch (BadPaddingException e) {
			e.printStackTrace();
		}
		catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Decrypt 128 bits
	 * 
	 * @param content
	 *            To decrypt the content
	 * @param password
	 *            Decryption key
	 * @return
	 */
	public static byte[] decryptAES(byte[] content, String skey) {
		try {
			SecretKeySpec key = new SecretKeySpec(Arrays.copyOf(skey.getBytes("utf-8"), 16), "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			// CBC needs iv, ECB does not need
			IvParameterSpec iv = new IvParameterSpec("0192939495969798".getBytes());
			cipher.init(Cipher.DECRYPT_MODE, key, iv);// initialization
			byte[] result = cipher.doFinal(content);
			return result; // encryption
		}
		catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		catch (NoSuchPaddingException e) {
			e.printStackTrace();
		}
		catch (InvalidKeyException e) {
			e.printStackTrace();
		}
		catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		}
		catch (BadPaddingException e) {
			e.printStackTrace();
		}
		catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Simple encryption
	 * 
	 * @param text
	 * @param key
	 * @return
	 */
	public static String encryptToken(String text, long key) {
		String skey = String.valueOf(key);
		// Simple Encryption +1 in plaintext, greater than 9 = 0
		// System.out.println("clear text:" + text);
		StringBuilder str = new StringBuilder(text);
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			// Dealing only with number types
			if (Character.isDigit(c)) {
				int x = Integer.parseInt(String.valueOf(c));
				x++;
				// Greater than 9, change to 0
				if (x > 9)
					x = 0;
				char ch1 = (char) (x + 48);
				str.setCharAt(i, ch1);
			}
		}
		text = str.toString();
		// System.out.println("Shortcut:" + text + ",key=" + skey);
		// use aes encryption
		byte[] encryptResult = encryptAES(text, skey);
		String asB64 = Base64.encodeBase64String(encryptResult);
		return asB64;

	}

	/**
	 * Simple decryption
	 * 
	 * @param text
	 * @param key
	 * @return
	 */
	public static String decryptToken(String text, long key) {
		String skey = String.valueOf(key);
		// System.out.println("Ciphertext:" + text);
		byte[] decryptFrom = Base64.decodeBase64(text);
		// Decryption
		byte[] decryptResult = decryptAES(decryptFrom, skey);
		String ret = new String(decryptResult);
		// System.out.println("Brief:" + ret);
		// Simple decryption Put all the numbers in the ciphertext -1, equal to
		// 0 = 9
		StringBuilder str = new StringBuilder(ret);
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			// Dealing only with number types
			if (Character.isDigit(c)) {
				int x = Integer.parseInt(String.valueOf(c));
				x--;
				// Less than 0, change to 9
				if (x < 0)
					x = 9;
				char ch1 = (char) (x + 48);
				str.setCharAt(i, ch1);
			}
		}
		ret = str.toString();
		// System.out.println("Plaintext:" + ret);

		return ret;
	}

	/**
	 * Convert binary to hexadecimal
	 * 
	 * @param buf
	 * @return
	 */
	public static String parseByte2HexStr(byte buf[]) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}

	/**
	 * Convert hex to binary
	 * 
	 * @param hexStr
	 * @return
	 */
	public static byte[] parseHexStr2Byte(String hexStr) {
		if (hexStr.length() < 1)
			return null;
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}

	/**
	 * 32-bit MD5 value, hexadecimal string
	 * 
	 * @param text
	 * @return
	 */
	public static String md5Str(String text) {
		MessageDigest md5;
		try {
			md5 = MessageDigest.getInstance("MD5");
			byte[] md5byte = md5.digest(text.getBytes("utf-8"));
			return parseByte2HexStr(md5byte).toUpperCase();
		}
		catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * Return URL signature
	 * 
	 * @param url
	 *            URL
	 * @param token
	 *            Token
	 * @param time
	 *            Timestamps
	 * @return URL signature
	 */
	public static String getUrlSign(String url, String token, long time) {
		String _url = url + "&token=" + token + "&time=" + String.valueOf(time);
		return md5Str(_url);
	}

}
