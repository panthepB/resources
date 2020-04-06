
package distar.project.DLT.web;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import com.distar.dtwr.company.domain.User;
import com.google.gson.Gson;

import antlr.Token;
import distar.gateway.controller.Util;
import distar.project.DLT.domain.LTYUnitId;
import distar.project.DLT.domain.MALog;
import distar.project.DLT.domain.MasterFile;
import distar.project.DLT.repository.LTYUnitIdDAO;
import distar.project.DLT.repository.MALogDAO;
import distar.project.DLT.repository.MasterFileDAO;
import distar.project.DLT.service.GpsBackupForm;
import distar.project.DLT.service.MasterFileForm;
import distar.project.DLT.service.SearchForm;
import distar.project.service.ServerIP;
import distar.project.web.UsableController;

public class ServiceController extends UsableController {

	private static String token = "";
	private MasterFileDAO masterFileDAO;
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

	public ModelAndView defaultRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return new ModelAndView("redirect:/");
	}

	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) throws Exception {

		response.setContentType("application/json");
		// return new ModelAndView("status", "status", status);
		return new ModelAndView("home");
	}

	public ModelAndView updateLicDTK3G2(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String benz = null;
		benz = request.getParameter("benz");
		System.out.println("Benz : " + benz);

		if (super.chkSession(request) || benz.equals("OK")) {
			Timer timer = new Timer();
			int initialDelay = 1000;
			int period = 60000;
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

				@Override
				public void run() {

					connect = null;
					stmt = null;
					stmt2 = null;
					stmt4 = null;
					json = null;
					data = new ArrayList();

					Date d = new Date();
					// timer send gps data to DLT

					try {
						String url = serverIP.getTrackingServer();
						Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
						connect = DriverManager.getConnection(url);
						stmt = connect.createStatement();
						stmt2 = connect.createStatement();
						stmt4 = connect.createStatement();
						if (connect != null) {

							System.out.println("updateLicDTK3G!!" + formatter.format(new Date()));

							String initLic = "";
							Object driverId = "";
							String license = "";
							String unitId = "";
							String imei_id = "";
							String rfid = "";
							String driverLic = "";
							String rfidTime = null;
							String gpsLastRfid = "";
							String gpsLastAlarmId = "";
							String alarmHistoryId = "";
							int loginStatus = 0;

							String query = "SELECT sn_imei_id ,l_datetime ,r_datetime ,latitude,longitude,speed ,altitude,gps_valid,hdop ,satellite_number,gsm_signal,unit_id,gsm_station_id,azimuth,license_rfid,alarm_history_id,ad4,ad5,login_status FROM dbo.GPS_Last WITH (NOLOCK) WHERE unit_id IS NOT NULL";
							ResultSet rs = stmt.executeQuery(query);
							while (rs.next()) {
								unitId = rs.getString("unit_id");
								imei_id = rs.getString("sn_imei_id");
								// System.out.println("unitId : " + unitId);

								// if (unitId != null && !unitId.isEmpty()) {

								String driver_query = "SELECT TOP 1 alarm_info_auto_id,sn_imei_id,extend_alarm_info,l_datetime FROM [ms03].[dbo].[Alarm_History]  Where [sn_imei_id] = '" + imei_id
										+ "'  and [alarm_id] = '37'  and [extend_alarm_id] is not null ORDER BY l_datetime DESC";
								ResultSet rs2 = stmt2.executeQuery(driver_query);
								while (rs2.next()) {

									rfid = rs2.getString("extend_alarm_info");
									alarmHistoryId = rs2.getString("alarm_info_auto_id");

									// System.out.println("while lic: " + rfid +
									// "unitId : " + unitId);

									String rfidTmp;
									try {
										rfid = rfid.replace("+", "");
										rfidTmp = rfid.split("=")[2].replace("?", "");

										rfid = rfidTmp.trim();
									}
									catch (Exception e) {
										try {
											rfid = rfid.replace("?", "");
											String rfidTest = rfid.replace(" ", "").trim();

											long test = Long.parseLong(rfidTest);

										}
										catch (NumberFormatException ex) {

											if (rfid.length() > 10) {
												// System.out.println(ex.getMessage());
												System.out.println("Exception RFID : " + rfid + " imei : " + imei_id);
											}
											rfid = "";
										}

									}

									String lic;
									String sql;
									String lastLic;
									String extendAlarm = null;
									rfidTime = rs2.getString("l_datetime");
									try {
										extendAlarm = rfid;

										// System.out.println("extendAlarm : " +
										// extendAlarm);

										if (loginStatus == 0 && !gpsLastRfid.equals(rfid) && !gpsLastAlarmId.equals(alarmHistoryId)) {
											driverLic = rfid.replace(" ", "");
											driverLic = rfid.replace("|", "");
											initLic = "00000000000000000000000000000000000000000000000";
											lic = rfid.replace(" ", "0");
											lastLic = "0000000000000000000000";
											license = String.valueOf(initLic) + lic + lastLic;
											license = license.substring(0, 110);

											sql = "update [ms03].[dbo].[GPS_Last] set [license_rfid] = '" + rfid + "',alarm_history_id = '" + alarmHistoryId + "',login_status = 1 where sn_imei_id = '"
													+ imei_id + "'";
											stmt4.executeUpdate(sql);

										}
										else if (loginStatus == 0 && gpsLastRfid.equals(rfid) && !gpsLastAlarmId.equals(alarmHistoryId)) {
											driverLic = rfid.replace(" ", "");
											driverLic = rfid.replace("|", "");
											initLic = "00000000000000000000000000000000000000000000000";
											lic = rfid.replace(" ", "0");
											lastLic = "0000000000000000000000";
											license = String.valueOf(initLic) + lic + lastLic;
											license = license.substring(0, 110);

											sql = "update [ms03].[dbo].[GPS_Last] set [license_rfid] = '" + rfid + "',alarm_history_id = '" + alarmHistoryId + "',login_status = 1 where sn_imei_id = '"
													+ imei_id + "'";
											stmt4.executeUpdate(sql);

										}
										else if (loginStatus == 1 && gpsLastRfid.equals(rfid) && !gpsLastAlarmId.equals(alarmHistoryId)) {
											driverLic = "";
											license = "";
											String sql2 = "update [ms03].[dbo].[GPS_Last] set [license_rfid] = '" + rfid + "',alarm_history_id = '" + alarmHistoryId
													+ "' ,login_status = 0 where sn_imei_id = '" + imei_id + "'";
											stmt4.executeUpdate(sql2);
										}
										else if (loginStatus == 1 && !gpsLastRfid.equals(rfid) && !gpsLastAlarmId.equals(alarmHistoryId)) {
											driverLic = rfid.replace(" ", "");
											driverLic = rfid.replace("|", "");
											initLic = "00000000000000000000000000000000000000000000000";
											lic = rfid.replace(" ", "0");
											lastLic = "0000000000000000000000";
											license = String.valueOf(initLic) + lic + lastLic;
											license = license.substring(0, 110);

											sql = "update [ms03].[dbo].[GPS_Last] set [license_rfid] = '" + rfid + "',alarm_history_id = '" + alarmHistoryId + "',login_status = 1 where sn_imei_id = '"
													+ imei_id + "'";
											stmt4.executeUpdate(sql);
										}
										else if (loginStatus == 0 && gpsLastRfid.equals(rfid) && gpsLastAlarmId.equals(alarmHistoryId)) {
											driverLic = "";
											license = "";

											sql = "update [ms03].[dbo].[GPS_Last] set [license_rfid] = '',alarm_history_id = '' ,login_status = 0 where sn_imei_id = '" + imei_id + "'";
											stmt4.executeUpdate(sql);
										}
										else if (loginStatus == 1 && gpsLastRfid.equals(rfid) && gpsLastAlarmId.equals(alarmHistoryId)) {

											driverLic = rfid.replace(" ", "");
											driverLic = rfid.replace("|", "");
											initLic = "00000000000000000000000000000000000000000000000";
											lic = rfid.replace(" ", "0");
											lastLic = "0000000000000000000000";
											license = String.valueOf(initLic) + lic + lastLic;
											license = license.substring(0, 110);

											sql = "update [ms03].[dbo].[GPS_Last] set [license_rfid] = '" + rfid + "',alarm_history_id = '" + alarmHistoryId + "',login_status = 1 where sn_imei_id = '"
													+ imei_id + "'";
											stmt4.executeUpdate(sql);

										}

									}
									catch (Exception e) {

										// System.out.println("catch : " +
										// unitId + " : " + e.getMessage());
										driverLic = rfid.replace(" ", "");
										driverLic = rfid.replace("|", "");
										initLic = "00000000000000000000000000000000000000000000000";
										lic = rfid.replace(" ", "0");
										lastLic = "0000000000000000000000";
										license = String.valueOf(initLic) + lic + lastLic;

										try {
											driverLic = "";
											license = "";
										}
										catch (Exception ex) {

											System.out.println("Exception lic: " + lic);
											System.out.println("Exception license: " + license);
											license = license.substring(0, 110);
											System.out.println("Exception XXX: " + e.getMessage());

											System.out.println("imei_id Exception : " + imei_id + " RFID : " + rfid);
										}

										try {
											Integer.parseInt(rfid.replace(" ", ""));
										}
										catch (Exception ex) {
											// TODO: handle exception
											rfid = "";
										}

										sql = "update [ms03].[dbo].[GPS_Last] set [license_rfid] = '" + rfid + "',alarm_history_id = '" + alarmHistoryId + "',login_status = 1 where sn_imei_id = '"
												+ imei_id + "'";
										stmt4.executeUpdate(sql);

									}

								}
								// connect.close();
								// connect2.close();
								// rs.close();
								// rs2.close();
							}
							System.out.println("License updated!!" + new Date());
						}
					}
					catch (Exception e) {
						// TODO: handle exception
					}

				}
			};

			timer.scheduleAtFixedRate((TimerTask) task, initialDelay, (long) period);
			return new ModelAndView("redirect:/member.htm", "status", "100");

		}
		return new ModelAndView("redirect:/");
	}

	public ModelAndView updateLicDTK3G(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String benz = null;
		benz = request.getParameter("benz");
		System.out.println("Benz : " + benz);

		if (super.chkSession(request) || benz.equals("OK")) {
			Timer timer = new Timer();
			int initialDelay = 1000;
			int period = 20000;
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

				@Override
				public void run() {

					connect = null;
					stmt = null;
					stmt2 = null;
					stmt4 = null;
					json = null;
					data = new ArrayList();

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

							// System.out.println("updateLic DTK3G Start!! " +
							// formatter.format(new Date()));

							String initLic = "";
							Object driverId = "";
							String license = "";
							String unitId = "";
							String imei_id = "";
							String rfid = "";
							String driverLic = "";
							String rfidTime = null;
							String gpsLastRfid = "";
							String gpsLastAlarmId = "";
							String alarmHistoryId = "";
							int engine_status = 0;
							int loginStatus = 0;
							String accTime = "";
							String swipeTime = "";
							int count = 0;
							String extAlarm = "";

							Locale lc = new Locale("en", "EN");
							SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", lc);
							SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", lc);
							TimeZone tmz = TimeZone.getTimeZone("GMT+00:00");
							df.setTimeZone(tmz);

							Date sDate = new Date(System.currentTimeMillis() - 480 * 1000);
							Date rDate = new Date(System.currentTimeMillis() + 300 * 1000);
							String selDate = df.format(sDate);
							String selRDate = df.format(rDate);

							String query = "SELECT sn_imei_id ,l_datetime ,r_datetime ,latitude,longitude,speed ,altitude,gps_valid,hdop ,satellite_number,gsm_signal,unit_id,gsm_station_id,azimuth,license_rfid,alarm_history_id,ad4,ad5,login_status FROM dbo.GPS_Last WITH (NOLOCK) WHERE unit_id IS NOT NULL and l_datetime > '"
									+ selDate + "' and l_datetime < '" + selRDate + "'";
							ResultSet rs = stmt.executeQuery(query);

							// System.out.println("query : "+query);
							while (rs.next()) {
								unitId = rs.getString("unit_id");
								imei_id = rs.getString("sn_imei_id");
								gpsLastRfid = rs.getString("license_rfid");
								gpsLastAlarmId = rs.getString("alarm_history_id");
								loginStatus = rs.getInt("login_status");

								// System.out.println("unitId : " + unitId);

								// if (unitId != null && !unitId.isEmpty()) {

								String driver_query = "SELECT TOP 1 alarm_info_auto_id,sn_imei_id,extend_alarm_info,l_datetime FROM [ms03].[dbo].[Alarm_History] WITH (NOLOCK)  Where [sn_imei_id] = '"
										+ imei_id + "'  and [alarm_id] = '37'  and [extend_alarm_id] is not null ORDER BY l_datetime DESC";
								ResultSet rs2 = stmt2.executeQuery(driver_query);
								while (rs2.next()) {

									rfid = rs2.getString("extend_alarm_info");
									alarmHistoryId = rs2.getString("alarm_info_auto_id");
									swipeTime = rs2.getString("l_datetime");
									rfidTime = rs2.getString("l_datetime");
									extAlarm = rfid;
								}
								Date accT = null;
								Date swipeT = null;

								String selACC = "SELECT top 1 [alarm_id],[l_datetime]  FROM [ms03].[dbo].[Alarm_History] WITH (NOLOCK) where sn_imei_id ='" + imei_id
										+ "' and (alarm_id ='3' or alarm_id ='11') order by l_datetime desc";
								ResultSet rsACC = stmt3.executeQuery(selACC);

								// System.out.println("selACC : "+selACC);

								while (rsACC.next()) {

									accTime = rsACC.getString("l_datetime");

									if (rsACC.getInt("alarm_id") == 3) {
										engine_status = 1;

										// String sql = "update
										// [ms03].[dbo].[GPS_Last] set
										// engine_status = '"+engine_status+"',
										// ext_alarm = '"+extAlarm+"' where
										// sn_imei_id = '" + imei_id + "'";
										// System.out.println("engine_status :
										// "+engine_status + " count : "+count
										// +" swipe : "+ accT.before(swipeT) + "
										// sn_imei_id = '" + imei_id + "'");
										// System.out.println("If :"+sql);
										// stmt4.executeUpdate(sql);
										// System.out.println("If");
									}
									else {
										engine_status = 0;
										// String sql = "update
										// [ms03].[dbo].[GPS_Last] set
										// engine_status = '"+engine_status+"',
										// ext_alarm = '"+extAlarm+"'
										// ,login_status = 0 where sn_imei_id =
										// '" + imei_id + "'";
										// System.out.println("engine_status :
										// "+engine_status + " count : "+count
										// +" swipe : "+ accT.before(swipeT) + "
										// sn_imei_id = '" + imei_id + "'");

										// stmt4.executeUpdate(sql);
										// System.out.println("Else : "+ sql);
										// System.out.println("Else");
									}

									try {
										accT = formatter.parse(accTime);
										swipeT = formatter.parse(swipeTime);
									}
									catch (Exception e) {
										// TODO: handle exception
										System.out.println("Exception time : " + e.getMessage());
									}

									// System.out.println("engine_l_datetime :
									// "+rsACC.getString("l_datetime"));

								}
								count++;

								String rfidTmp;
								try {
									rfid = rfid.replace("+", "");
									rfidTmp = rfid.split("=")[2].replace("?", "");

									rfid = rfidTmp.trim();
								}
								catch (Exception e) {
									try {
										rfid = rfid.replace("?", "");
										String rfidTest = rfid.replace(" ", "").trim();

										long test = Long.parseLong(rfidTest);

									}
									catch (NumberFormatException ex) {

										if (rfid.length() > 10) {
											// System.out.println(ex.getMessage());
											// System.out.println("Exception
											// RFID : " + rfid + " imei : " +
											// imei_id);
										}
										rfid = "";
									}

								}

								String lic;
								String sql;
								String lastLic;
								String extendAlarm = null;

								try {
									extendAlarm = rfid;

									// System.out.println("extendAlarm : " +
									// extendAlarm);
									if (engine_status == 1) {

										if (loginStatus == 0 && !gpsLastRfid.equals(rfid) && !gpsLastAlarmId.equals(alarmHistoryId)) {
											driverLic = rfid.replace(" ", "");
											driverLic = rfid.replace("|", "");
											initLic = "00000000000000000000000000000000000000000000000";
											lic = rfid.replace(" ", "0");
											lastLic = "0000000000000000000000";
											license = String.valueOf(initLic) + lic + lastLic;
											license = license.substring(0, 110);

											sql = "update [ms03].[dbo].[GPS_Last] set [license_rfid] = '" + rfid + "',alarm_history_id = '" + alarmHistoryId
													+ "',login_status = 1, engine_status = 1 where sn_imei_id = '" + imei_id + "'";
											stmt4.executeUpdate(sql);

										}
										else if (loginStatus == 0 && gpsLastRfid.equals(rfid) && !gpsLastAlarmId.equals(alarmHistoryId)) {
											driverLic = rfid.replace(" ", "");
											driverLic = rfid.replace("|", "");
											initLic = "00000000000000000000000000000000000000000000000";
											lic = rfid.replace(" ", "0");
											lastLic = "0000000000000000000000";
											license = String.valueOf(initLic) + lic + lastLic;
											license = license.substring(0, 110);

											sql = "update [ms03].[dbo].[GPS_Last] set [license_rfid] = '" + rfid + "',alarm_history_id = '" + alarmHistoryId
													+ "',login_status = 1, engine_status = 1 where sn_imei_id = '" + imei_id + "'";
											stmt4.executeUpdate(sql);

										}
										else if (loginStatus == 1 && gpsLastRfid.equals(rfid) && !gpsLastAlarmId.equals(alarmHistoryId)) {
											driverLic = "";
											license = "";
											String sql2 = "update [ms03].[dbo].[GPS_Last] set [license_rfid] = '" + rfid + "',alarm_history_id = '" + alarmHistoryId
													+ "' ,login_status = 0, engine_status = 1 where sn_imei_id = '" + imei_id + "'";
											stmt4.executeUpdate(sql2);
										}
										else if (loginStatus == 1 && !gpsLastRfid.equals(rfid) && !gpsLastAlarmId.equals(alarmHistoryId)) {
											driverLic = rfid.replace(" ", "");
											driverLic = rfid.replace("|", "");
											initLic = "00000000000000000000000000000000000000000000000";
											lic = rfid.replace(" ", "0");
											lastLic = "0000000000000000000000";
											license = String.valueOf(initLic) + lic + lastLic;
											license = license.substring(0, 110);

											sql = "update [ms03].[dbo].[GPS_Last] set [license_rfid] = '" + rfid + "',alarm_history_id = '" + alarmHistoryId
													+ "',login_status = 1, engine_status = 1 where sn_imei_id = '" + imei_id + "'";
											stmt4.executeUpdate(sql);
										}
										else if (loginStatus == 0 && gpsLastRfid.equals(rfid) && gpsLastAlarmId.equals(alarmHistoryId)) {
											driverLic = "";
											license = "";

											sql = "update [ms03].[dbo].[GPS_Last] set [license_rfid] = '',alarm_history_id = '' ,login_status = 0, engine_status = 1 where sn_imei_id = '" + imei_id
													+ "'";
											stmt4.executeUpdate(sql);
										}
										else if (loginStatus == 1 && gpsLastRfid.equals(rfid) && gpsLastAlarmId.equals(alarmHistoryId)) {

											driverLic = rfid.replace(" ", "");
											driverLic = rfid.replace("|", "");
											initLic = "00000000000000000000000000000000000000000000000";
											lic = rfid.replace(" ", "0");
											lastLic = "0000000000000000000000";
											license = String.valueOf(initLic) + lic + lastLic;
											license = license.substring(0, 110);

											sql = "update [ms03].[dbo].[GPS_Last] set [license_rfid] = '" + rfid + "',alarm_history_id = '" + alarmHistoryId
													+ "',login_status = 1, engine_status = 1 where sn_imei_id = '" + imei_id + "'";
											stmt4.executeUpdate(sql);

										}
									}
									else {
										driverLic = "";
										license = "";

										sql = "update [ms03].[dbo].[GPS_Last] set [license_rfid] = '',alarm_history_id = '' ,login_status = 0, engine_status = 0 where sn_imei_id = '" + imei_id + "'";
										stmt4.executeUpdate(sql);
									}

								}
								catch (Exception e) {

									// System.out.println("catch : " +
									// unitId + " : " + e.getMessage());
									driverLic = rfid.replace(" ", "");
									driverLic = rfid.replace("|", "");
									initLic = "00000000000000000000000000000000000000000000000";
									lic = rfid.replace(" ", "0");
									lastLic = "0000000000000000000000";
									license = String.valueOf(initLic) + lic + lastLic;

									try {
										driverLic = "";
										license = "";
									}
									catch (Exception ex) {

										System.out.println("Exception lic: " + lic);
										System.out.println("Exception license: " + license);
										license = license.substring(0, 110);
										System.out.println("Exception XXX: " + e.getMessage());

										System.out.println("imei_id Exception : " + imei_id + " RFID : " + rfid);
									}

									try {
										Integer.parseInt(rfid.replace(" ", ""));
									}
									catch (Exception ex) {
										// TODO: handle exception
										rfid = "";
									}

									sql = "update [ms03].[dbo].[GPS_Last] set [license_rfid] = '" + rfid + "',alarm_history_id = '" + alarmHistoryId + "',login_status = 1 where sn_imei_id = '"
											+ imei_id + "'";
									stmt4.executeUpdate(sql);

								}

								// connect.close();
								// connect2.close();
								// rs.close();
								// rs2.close();
							}
							System.out.println("License updated!!" + new Date());
						}
					}
					catch (Exception e) {
						// TODO: handle exception
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

				@Override
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
									// lat = rs.getString("WeiDu");
									// lat = Float.parseFloat(lat.substring(0,
									// 2)) >= 85.0f
									// ? String.valueOf(lat.substring(0, 1)) +
									// "." + lat.substring(1, lat.length())
									// : String.valueOf(lat.substring(0, 2)) +
									// "." + lat.substring(2, lat.length());
									// lon = rs.getString("JingDu");
									// lon = Float.parseFloat(lon.substring(0,
									// 3)) >= 180.0f
									// ? String.valueOf(lon.substring(0, 2)) +
									// "." + lon.substring(2, lon.length())
									// : String.valueOf(lon.substring(0, 3)) +
									// "." + lon.substring(3, lon.length());
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

				@Override
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
									lat = Float.parseFloat(lat.substring(0, 2)) >= 85.0f
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

	public String getGPSData(String serial) {

		String status = "";
		HttpResponse res = null;

		try {

			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost post = new HttpPost("http://202.183.221.87:12040/gps/last");
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

	public String getOnlineLast(String serial) {
		String status = "";
		HttpResponse res = null;

		try {

			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost post = new HttpPost("http://202.183.221.87:12040/dev/online/last");
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

	public String getGPSDataLTY() {

		String status = "";
		HttpResponse res = null;

		try {


			String url = "http://www.bmtasmartbus.com/gavernment/getAllDate?key="+getBMTAKey();
			
			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(url);


			HttpResponse response = client.execute(post);
			System.out.println("\nSending 'POST' request to URL : " + url);
			System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			
			 
			status = result.toString();
//			System.out.println(status);
		}
		catch (Exception e) {
			System.out.println("connection fail!!");
			e.printStackTrace();
		}

		return status;
	}
	
	static String bmtaAPIKey = "";
	public String getBMTAKey () {
		Connection connect = null;
		Statement stmt = null;
		String uri = serverIP.getMysql();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection(uri);
			stmt = connect.createStatement();
			
			String getKey = "SELECT  `key` FROM  `distar_dlt`.`api_key` WHERE `key_name` = 'BmtaDLT'";
			ResultSet rs = stmt.executeQuery(getKey);
			while (rs.next()) {
				bmtaAPIKey = rs.getString("key");
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		return bmtaAPIKey;
		
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

				@Override
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
//		System.out.println("getGPSDataLTY : "+new Date());
		String data = getGPSDataLTY();
		String status = "";
		String engineStatus = "";
		String unitId = "";
		String utc_ts = "2016-01-18T11:00:13.939Z";
		int speed = 0;
		String driverId = "";
		Double lat = 0.0;
		Double lon = 0.0;

		int loginStatus = 0;
		
//		System.out.println(new Date());

		try {
			JsonNode arrNode = new ObjectMapper().readTree(data).get("locations");

			Connection connect;
			Connection connect2;
			Connection connect3;
			Statement stmt;
			Statement stmt2;
			Statement stmt3;

			String uri = serverIP.getMysql();
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection(uri);
			connect2 = DriverManager.getConnection(uri);
			connect3 = DriverManager.getConnection(uri);
			stmt = connect.createStatement();
			stmt2 = connect2.createStatement();
			stmt3 = connect3.createStatement();

			if (arrNode.isArray()) {

				for (JsonNode objNode : arrNode) {
					unitId = objNode.get("unitId").getTextValue();
					utc_ts = objNode.get("utcTs").getTextValue();
					speed = Integer.parseInt(objNode.get("speed").getTextValue());
					driverId = objNode.get("driverId").getTextValue();
					engineStatus = objNode.get("engineStatus").getTextValue();
					speed = Integer.parseInt(objNode.get("speed").getTextValue());
					lat = Double.parseDouble(objNode.get("lat").getTextValue());
					lon = Double.parseDouble(objNode.get("lon").getTextValue());
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

					String masUnit = "";

					try {

						String select = "SELECT `unit_id`,`vehicle_id` FROM `distar_dlt`.`lty_unitid` WHERE `vehicle_id` = '"+unitId+"'";
						ResultSet rs2 = stmt2.executeQuery(select);

//						int i = 0;
						while (rs2.next()) {
//							masUnit = rs2.getString("unit_id");

							String sql = "UPDATE `distar_dlt`.`lty_unitid` SET `time_update` = '" + df2.format(df.parse(utc_ts)) + "',`speed` = '" + speed + "',`driver_id` = '" + driverId
									+ "',`engine_status` = '" + engineStatus + "',`lat` = '" + lat + "',`lon` = '" + lon + "' WHERE `vehicle_id` = '" + unitId + "'";

							try {
								stmt3.executeUpdate(sql);
//								System.out.println("1 : "+unitId);
							}
							catch (Exception e) {
								// TODO: handle exception
								System.out.println("Error : " + masUnit);
							}
//							i++;

						}

						// System.out.println("222 : "+masUnit);
//						if (i == 0) {
//							String sql = "INSERT INTO `distar_dlt`.`lty_unitid` (`time_update`,`vehicle_id`,`speed`,`driver_id`,`engine_status`,`lat`,`lon`) VALUES ('" + df2.format(df.parse(utc_ts))
//									+ "','" + unitId + "','" + speed + "','" + driverId + "','" + engineStatus + "','" + lat + "','" + lon + "');";
//							stmt.executeUpdate(sql);
//							 System.out.println("2 : "+unitId);
//						}

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

	

	public ModelAndView checkUserPass(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Connection connect;
		Statement stmt;
		// jdbc:mysql://10.144.110.6:3366/motor?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC&useSSL=false
		String uri = "jdbc:mysql://103.20.204.183:6000/pass?user=root&password=1q@w3e$r5t";
		// String uri = serverIP.getMysql();
		Class.forName("com.mysql.jdbc.Driver");
		connect = DriverManager.getConnection(uri);
		stmt = connect.createStatement();

		String select = "SELECT `name`,  `password`,  `nickname`, `remark` FROM t_user WHERE `isadmin` = 0 AND `ismanager` = 0 AND `issupplier` = 0";
		ResultSet rs = stmt.executeQuery(select);

		List<List<Object>> table = new ArrayList<List<Object>>();
		List<Object> row = new ArrayList<Object>();
//		System.out.println(encrypt("888888"));
//		System.out.println(decrypt("21218CCA77804D2BA1922C33E0151105"));
		
		int i = 0;
		while (rs.next()) {
			i++;
			row.add(i);
			row.add(rs.getString("name"));
			row.add(rs.getString("password"));
//			String pass = encryptPassword(rs.getString("password"));
//			System.out.println("user size : "+ i);
			row.add(rs.getString("nickname"));
			row.add(rs.getString("remark"));

		}

		table.add(row);

		ModelMap modelMap = new ModelMap();
		modelMap.addAttribute("table", table);
		modelMap.addAttribute("row", row);
		modelMap.addAttribute("count", i);

//		 System.out.println("user size : "+ i);

		// return new ModelAndView("redirect:/service/updateUnitIdLty.htm");
		return new ModelAndView("/service/listTGUser", modelMap);
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

			String update = "UPDATE [dbo].[Tracker]  SET [tracker_register_time] = '" + masterFileForm.getTracker_register_time() + "', [tracker_expired_time] = '"
					+ masterFileForm.getTracker_expired_time() + "' WHERE sn_imei_id = '" + masterFileForm.getImei() + "'";

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

			try {
				swipeTimeD = dateformat.parse(swipeTime);
				accTimeD = dateformat.parse(accTime);
			}
			catch (Exception e) {
				// TODO: handle exception
				System.out.println("license swipeTimeD: " + swipeTime + " : " + e.getMessage());
			}

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

	public ModelAndView resCheckDriverLTY(HttpServletRequest request, HttpServletResponse response, SearchForm searchForm) throws Exception {

		String status = "";
		// updateDriverLTY();
		System.out.println("Vehicle num : "+searchForm.getImei() );
		LTYUnitId ltyUnitId = null;
		String data = getGPSDataLTY();
		String engineStatus = "";
		String unitId = "";
		String utc_ts = "2016-01-18T11:00:13.939Z";
		int speed = 0;
		String driverId = "";
		String lat = "0.0";
		String lon = "0.0";
		int numStas =0;
		
		
		try {
			Util.getLTYLastStatus(searchForm.getImei());
		}catch (Exception e) {
			// TODO: handle exception
		}

		try {
			JsonNode arrNode = new ObjectMapper().readTree(data).get("locations");

			if (arrNode.isArray()) {

				for (JsonNode objNode : arrNode) {

					unitId = objNode.get("unitId").getTextValue();

					if (searchForm.getImei().equals(unitId)) {
						utc_ts = objNode.get("utcTs").getTextValue();
						speed = Integer.parseInt(objNode.get("speed").getTextValue());
						driverId = objNode.get("driverId").getTextValue();
						engineStatus = objNode.get("engineStatus").getTextValue();
						lat = objNode.get("lat").getTextValue();
						lon = objNode.get("lon").getTextValue();
						numStas = objNode.get("numStas").getIntValue();
						System.out.println("numStas : "+numStas);
						try {
							Integer.parseInt(engineStatus);
						}
						catch (Exception e) {
							engineStatus = "0";
						}
					}

				}
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			System.out.println("Exception out : " + e.getMessage());
		}

		ModelMap modelMap = new ModelMap();

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		TimeZone tz = TimeZone.getTimeZone("GMT+7");
		TimeZone tz2 = TimeZone.getTimeZone("GMT+14");
		df.setTimeZone(tz);
		df2.setTimeZone(tz2);

		// System.out.println("VehicleNum : "+searchForm.getImei() + " : "+
		// unitId);

		try {
			ltyUnitId = ltyUnitIdDAO.findByVehicleId(searchForm.getImei());

			modelMap.addAttribute("status", status);
			modelMap.addAttribute("vehicle", searchForm.getImei());
			modelMap.addAttribute("driverId", driverId);
			modelMap.addAttribute("engineStatus", engineStatus);
			modelMap.addAttribute("timeUpdate", df2.format(df.parse(utc_ts)));
			modelMap.addAttribute("searchForm", searchForm);
			modelMap.addAttribute("lat", lat);
			modelMap.addAttribute("lon", lon);
			modelMap.addAttribute("numStas", numStas);

		}
		catch (Exception e) {
			System.out.println("Exception : " + e.getMessage());

			modelMap.addAttribute("status", "ไม่พบข้อมูล");
			modelMap.addAttribute("vehicle", "");
			modelMap.addAttribute("driverId", "");
			modelMap.addAttribute("engineStatus", "");
			modelMap.addAttribute("timeUpdate", "");
			modelMap.addAttribute("searchForm", searchForm);
			modelMap.addAttribute("lat", lat);
			modelMap.addAttribute("lon", lon);
			modelMap.addAttribute("numStas", numStas);

		}

		// return new ModelAndView("redirect:/service/updateUnitIdLty.htm");
		return new ModelAndView("/service/checkDriverLTY", modelMap);
	}
	
	public static ModelAndView sendMessage(HttpServletRequest request, HttpServletResponse response) {

		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost post = new HttpPost(String.valueOf("https://notify-api.line.me/api/notify"));

		HttpResponse res = null;
		String responseAsString = null;
		String message = request.getParameter("message");
		String token = request.getParameter("token");
		
		System.out.println("token : "+token);
		try {
			StringEntity params = new StringEntity("message=" + message);
			post.setHeader("Authorization", "Bearer "+ token );
			post.addHeader("Content-Type", "application/x-www-form-urlencoded");
			post.setEntity(params);

			res = httpClient.execute((HttpUriRequest) post);

//			System.out.println("send : " + String.valueOf(res.getStatusLine().getStatusCode()) + " Time : " + new Date() + " Data size :" + data.size());

			responseAsString = EntityUtils.toString(res.getEntity());
			System.out.println("response : " + responseAsString);

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Exception : " + e.getMessage());
		}
		ModelMap modelMap = new ModelMap();
		modelMap.addAttribute("res", responseAsString);
		
		return new ModelAndView("/service/resMessage", modelMap);
	}

}
