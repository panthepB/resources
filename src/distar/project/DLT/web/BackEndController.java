
package distar.project.DLT.web;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import distar.project.DLT.service.DecriptionForm;
import distar.project.service.ServerIP;
import distar.project.web.UsableController;

public class BackEndController extends UsableController {

	private ServerIP serverIP;

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
	
	public ModelAndView checkDTKDevice(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Locale lc = new Locale("en","EN");
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", lc);
		TimeZone tz = TimeZone.getTimeZone("GMT");
		df.setTimeZone(tz);
		
		SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", lc);
		TimeZone tz2 = TimeZone.getTimeZone("GMT+7");
		df2.setTimeZone(tz2);
		
		System.out.println(df2.format(new Timestamp(System.currentTimeMillis())));
		
		String dateTime = "";
		String condition = ">";
		Calendar cal = Calendar.getInstance(); // creates calendar
	    cal.setTime(new Date()); // sets calendar time/date
	    
	    cal.add(Calendar.HOUR_OF_DAY, -1); // adds one hour
		dateTime = df.format(cal.getTime());
		
		cal.add(Calendar.HOUR_OF_DAY, -1); // adds one hour
		String dateNow = df.format(cal.getTime());
		
		String time = request.getParameter("time");
		
		try{
		if(!time.isEmpty()){
			cal.clear();
			cal.setTime(new Date());
			 
			if(time.equals("12")){
				cal.add(Calendar.HOUR_OF_DAY, -12); // adds one hour
				dateTime = df.format(cal.getTime());
				condition = ">";
//			    System.out.println("time : "+df2.format(cal.getTime()));
			}else if(time.equals("24")){
				cal.add(Calendar.HOUR_OF_DAY, -24); // adds one hour
				dateTime = df.format(cal.getTime());
				condition = ">";
//			    System.out.println("time : "+df2.format(cal.getTime()));
			}else if(time.equals("3")){
				cal.add(Calendar.HOUR_OF_DAY, (-24*3)); // adds one hour
				dateTime = df.format(cal.getTime());
				condition = ">";
//			    System.out.println("time : "+df2.format(cal.getTime()));
			}else if(time.equals("5")){
				cal.add(Calendar.HOUR_OF_DAY, (-24*5)); // adds one hour
				dateTime = df.format(cal.getTime());
				condition = ">";
//			    System.out.println("time : "+df2.format(cal.getTime()));
			}
			
			else if(time.equals("7")){
				cal.add(Calendar.HOUR_OF_DAY, (-24*7)); // adds one hour
				dateTime = df.format(cal.getTime());
				dateNow = dateTime;
				condition = "<";
//			    System.out.println("time : "+df2.format(cal.getTime()));
			}else if(time.equals("0")){
				cal.add(Calendar.MINUTE, -5); // adds one hour
				dateTime = df.format(cal.getTime());
				dateNow = dateTime;
				condition = "<";
//			    System.out.println("time : "+df2.format(cal.getTime()));
			}
			
//			System.out.println("date now : "+df2.format(new Date()));
		}
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		
		Connection connect;
		Statement stmt;

		String uri = serverIP.getTrackingServer();
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		connect = DriverManager.getConnection(uri);
		stmt = connect.createStatement();
		
		String select = " SELECT [GPS_Last].[sn_imei_id] as sn_imei_id,[Tracker].[tracker_name] as tracker_name,[User_Tracker_Relation].[user_account] as user_account ,[User_Relation].[user_father] as user_father"
				+ "  ,[user_tracker_permission] ,[alarm_id] ,[l_datetime] ,[latitude] ,[longitude] ,[speed] "
			    + "  ,[altitude] ,[gps_valid] ,[satellite_number] ,[hdop] ,[gsm_signal] ,[ad4] ,[ad5], [decription] "
			    + " FROM [ms03].[dbo].[GPS_Last] "
			    + " LEFT JOIN Tracker ON GPS_Last.sn_imei_id = Tracker.sn_imei_id "
			    + " LEFT JOIN [User_Tracker_Relation] on [User_Tracker_Relation].sn_imei_id = GPS_Last.sn_imei_id "
			    +" LEFT JOIN [User_Relation] on [User_Relation].user_child = [User_Tracker_Relation].user_account and [User_Relation].user_relation_rule = '99' "

			    +" where ([GPS_Last].l_datetime "+condition+" '"+dateTime+"' and [GPS_Last].l_datetime < '"+dateNow+"' ) and [User_Tracker_Relation].user_tracker_permission = '99' ORDER BY l_datetime";
		
		

//		String select = "SELECT [sn_imei_id] ,[tracker_password] ,[tracker_name] ,[tracker_sim_number] ,[tracker_register_time] ,[tracker_expired_time] FROM [ms03].[dbo].[Tracker] where tracker_type_id = '81'";
		ResultSet rs = stmt.executeQuery(select);

		List<List<Object>> table = new ArrayList<List<Object>>();
		List<Object> row = new ArrayList<Object>();

		
		
		int i = 0;
		while (rs.next()) {
			i++;
			row.add(i);
			row.add(rs.getString("sn_imei_id"));
			row.add(rs.getString("tracker_name"));
			row.add(rs.getString("user_account"));
			row.add(rs.getString("user_father"));
			row.add(df2.format(df.parse(rs.getString("l_datetime"))));
			//row.add(rs.getString("satellite_number"));
			
			try{
				row.add(rs.getString("decription"));
			}catch (Exception e) {
				System.out.println("Error : "+e.getMessage());
				row.add("");
			}
	
		}
		rs.close();
		connect.close();

		table.add(row);

		ModelMap modelMap = new ModelMap();
		modelMap.addAttribute("table", table);
		modelMap.addAttribute("row", row);
		modelMap.addAttribute("count", i);

		// System.out.println("user size : "+ i);

		// return new ModelAndView("redirect:/service/updateUnitIdLty.htm");
		return new ModelAndView("/backend/listDTKDevice", modelMap);
	}
	
	public ModelAndView checkGPSCut(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Connection connect;
		Statement stmt;

		String url = serverIP.getMysql();
		Class.forName("com.mysql.jdbc.Driver");
		connect = DriverManager.getConnection(url);
		stmt = connect.createStatement();
		
		String select = "select `sn_imei_id`, `user_account`, `user_father`, `tracker_name`, `times`, `model` from `distar_dlt`.`gps_cut_statistics` WHERE times > 1 and alarm_id = '28' ORDER BY times desc";
		
		ResultSet rs = stmt.executeQuery(select);

		List<List<Object>> table = new ArrayList<List<Object>>();
		List<Object> row = new ArrayList<Object>();

		int i = 0;
		while (rs.next()) {
			i++;

			row.add(i);
			row.add(rs.getString("sn_imei_id"));
			row.add(rs.getString("tracker_name"));
			row.add(rs.getString("user_account"));
			row.add(rs.getString("user_father"));
			row.add(rs.getString("times"));
			row.add(rs.getString("model"));
		}
		rs.close();
		connect.close();
		
		table.add(row);

		ModelMap modelMap = new ModelMap();
		modelMap.addAttribute("table", table);
		modelMap.addAttribute("row", row);
		modelMap.addAttribute("count", i);

		return new ModelAndView("/backend/listGPSCut", modelMap);
	}
	
	public ModelAndView checkGPSBlindAlarm(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Connection connect;
		Statement stmt;

		String url = serverIP.getMysql();
		Class.forName("com.mysql.jdbc.Driver");
		connect = DriverManager.getConnection(url);
		stmt = connect.createStatement();
		
		String select = "select `sn_imei_id`, `user_account`, `user_father`, `tracker_name`, `times`, `model` from `distar_dlt`.`gps_cut_statistics` WHERE times > 1 and alarm_id = '24' ORDER BY times desc";
		
		ResultSet rs = stmt.executeQuery(select);

		List<List<Object>> table = new ArrayList<List<Object>>();
		List<Object> row = new ArrayList<Object>();

		int i = 0;
		while (rs.next()) {
			i++;

			row.add(i);
			row.add(rs.getString("sn_imei_id"));
			row.add(rs.getString("tracker_name"));
			row.add(rs.getString("user_account"));
			row.add(rs.getString("user_father"));
			row.add(rs.getString("times"));
			row.add(rs.getString("model"));
		}
		rs.close();
		connect.close();

		table.add(row);

		ModelMap modelMap = new ModelMap();
		modelMap.addAttribute("table", table);
		modelMap.addAttribute("row", row);
		modelMap.addAttribute("count", i);

		return new ModelAndView("/backend/listGPSBlind", modelMap);
	}
	
	public ModelAndView updateAlarm(HttpServletRequest request, HttpServletResponse response) throws Exception {

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		String dateTime = "";

		Calendar cal = Calendar.getInstance(); // creates calendar
	    cal.setTime(new Date()); // sets calendar time/date
	    
	    cal.add(Calendar.HOUR_OF_DAY, (-24*3)); // adds one hour
		dateTime = df.format(cal.getTime());

		System.out.println("updateAlarm");
		
		Connection connect;
		Connection connect2;
		Statement stmt;
		Statement stmt2;
		
		String url = serverIP.getMysql();
		Class.forName("com.mysql.jdbc.Driver");
		//connect = DriverManager.getConnection(uri);
		connect2 = DriverManager.getConnection(url);
		stmt2 = connect2.createStatement();
		

		String uri = serverIP.getTrackingServer();
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		connect = DriverManager.getConnection(uri);
		stmt = connect.createStatement();
		
		String truncate = "TRUNCATE TABLE `distar_dlt`.`gps_cut_statistics`;";
		stmt2.executeUpdate(truncate);
		
		
		
		
		System.out.println("TRUNCATE");
		
		String select = "SELECT DISTINCT( User_Tracker_Relation.sn_imei_id) as sn_imei_id ,User_Tracker_Relation.user_account as user_account ,User_Relation.user_father as user_father ,Tracker.tracker_name as tracker_name, Alarm_History.alarm_id as alarm_id"   				
					  +" FROM ms03.dbo.User_Tracker_Relation"
					  +" left join User_Relation on User_Relation.user_child = User_Tracker_Relation.user_account and User_Relation.user_relation_rule = '99'"
					  +" left join Alarm_History on Alarm_History.sn_imei_id = User_Tracker_Relation.sn_imei_id"
					  +" left join Tracker on User_Tracker_Relation.sn_imei_id = Tracker.sn_imei_id"
					  +" where user_tracker_permission = '99' and (Alarm_History.alarm_id = '28' or Alarm_History.alarm_id = '24') and Alarm_History.l_datetime > '"+dateTime+"'";
		
		ResultSet rs = stmt.executeQuery(select);
		
		

		int i = 0;
		
		while (rs.next()) {
			i++;
			String insert = "INSERT INTO `distar_dlt`.`gps_cut_statistics` ( `sn_imei_id`, `user_account`, `user_father`, `tracker_name`, `model`,`alarm_id`) "
							+"VALUES ('"+rs.getString("sn_imei_id")+"','"+rs.getString("user_account")+"', '"+rs.getString("user_father")+"','"+rs.getString("tracker_name")+"','DTK-3G100T' , '"+rs.getString("alarm_id")+"') ;";
			stmt2.executeUpdate(insert);
		}
		rs.close();
		connect.close();
		connect2.close();
		
		System.out.println("total : "+i);
		
		Connection connect3;
		Connection connect4;
		Statement stmt3;
		Statement stmt4;
		
		String url2 = serverIP.getMysql();
		Class.forName("com.mysql.jdbc.Driver");
		connect3 = DriverManager.getConnection(url2);
		stmt3 = connect3.createStatement();

		String uri2 = serverIP.getTrackingServer();
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		connect4 = DriverManager.getConnection(uri2);
		stmt4 = connect4.createStatement();
		
		
		String countGPSCut = "SELECT count(Alarm_History.sn_imei_id) as times, Alarm_History.sn_imei_id as imei"
							+" FROM Alarm_History"
							+ " where ([Alarm_History].alarm_id = '28' or [Alarm_History].alarm_id = '24') and Alarm_History.l_datetime > '"+dateTime+"'"
							+" GROUP BY Alarm_History.sn_imei_id";
		
		ResultSet rs2 = stmt4.executeQuery(countGPSCut);
		
		
		while (rs2.next()) {
			
			String update = "UPDATE `distar_dlt`.`gps_cut_statistics` SET `times` = '"+rs2.getInt("times")+"' WHERE `sn_imei_id` = '"+rs2.getString("imei")+"' ;";
			stmt3.executeUpdate(update);
		}
		rs2.close();
		connect3.close();
		connect4.close();
		
		System.out.println("UPDATED");

		return new ModelAndView("redirect:/backend/checkGPSCut.htm");
	}
	
	
	
	public ModelAndView updateMasterFile(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Locale lc = new Locale("en","EN");
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.zzz" , lc);
		TimeZone tz = TimeZone.getTimeZone("GMT");
		df.setTimeZone(tz);
		
		String dateTime = "";

		Calendar cal = Calendar.getInstance(); // creates calendar
	    cal.setTime(new Date()); // sets calendar time/date
	    
	    cal.add(Calendar.HOUR_OF_DAY, (-24*3)); // adds one hour
		dateTime = df.format(cal.getTime());

		System.out.println("updateAlarm");
		
		Connection connect;
		Connection connect2;
		Statement stmt;
		Statement stmt2;
		
		String url = serverIP.getMysql();
		Class.forName("com.mysql.jdbc.Driver");
		//connect = DriverManager.getConnection(uri);
		connect = DriverManager.getConnection(url);
		stmt = connect.createStatement();

		String uri = serverIP.getTrackingServer();
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		connect2 = DriverManager.getConnection(uri);
		stmt2 = connect2.createStatement();
		
		
		String select = "SELECT card_reader, gps_model, imei, province_code, unit_id, vehicle_chassis_no, vehicle_id, vehicle_register_type, vehicle_type,"
						+" customer_name, sale_name, date_created, date_updated, user_create, user_update, dlt_update_time, dlt_status, install_date "
						+" FROM distar_dlt.master_file";
		
		ResultSet rs = null;
			
		try{
			rs = stmt.executeQuery(select);
			System.out.println("select : "+select);
		}catch (Exception e) {
			System.out.println("rs : "+e.getMessage());
		}

		int i = 0;
		
		while (rs.next()) {
			i++;
			String insert = "INSERT INTO [dbo].[distar_master_file]"
					+ "([card_reader] ,[gps_model] ,[imei] ,[province_code] ,[unit_id] ,[vehicle_chassis_no] ,[vehicle_id]"
					+ ",[vehicle_register_type] ,[vehicle_type] ,[customer_name] ,[sale_name] ,[date_created] ,[date_updated]"
					+ ",[user_create] ,[user_update] ,[dlt_update_time] ,[dlt_status] ,[install_date])"
					+ "VALUES ('"+rs.getString("card_reader")+"' ,'"+rs.getString("gps_model")+"' ,'"+rs.getString("imei")+"' ,'"+rs.getString("province_code")
					+ "','"+rs.getString("unit_id")+"' ,'"+rs.getString("vehicle_chassis_no")+"','"+rs.getString("vehicle_id")+"' ,'"+rs.getString("vehicle_register_type")
					+ "','"+rs.getString("vehicle_type")+"' ,'"+rs.getString("customer_name")+"' ,'"+rs.getString("sale_name")+"','"+(rs.getDate("date_created"))
					+ "','"+(rs.getDate("date_updated"))+"' ,'"+rs.getString("user_create")+"' ,'"+rs.getInt("user_update")+"' ,'"+rs.getDate("dlt_update_time")
					+ "','"+rs.getString("dlt_status")+"' ,'"+(rs.getDate("install_date"))+"')";
			
			try{
				System.out.println("insert : "+insert);
				stmt2.executeUpdate(insert);
				System.out.println("date_created : "+(rs.getDate("date_created")));
			}catch (Exception e) {
				System.out.println("Exception : "+e.getMessage());
			}
			
			
			
		}
		System.out.println("total : "+i);
		


		return new ModelAndView("redirect:/backend/checkGPSCut.htm");
	}
	
	public ModelAndView decriptionForm(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String imei = request.getParameter("imei");

		Connection connect;
		Statement stmt;
		
		String url = serverIP.getTrackingServer();
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

		connect = DriverManager.getConnection(url);
		stmt = connect.createStatement();

			//String s = "Prompted";
			
			String select = " SELECT [GPS_Last].[sn_imei_id] as sn_imei_id,[Tracker].[tracker_name] as tracker_name,[User_Tracker_Relation].[user_account] as user_account ,[User_Relation].[user_father] as user_father"
					+ "  ,[user_tracker_permission] ,[alarm_id] ,[l_datetime] ,[latitude] ,[longitude] ,[speed] "
				    + "  ,[altitude] ,[gps_valid] ,[satellite_number] ,[hdop] ,[gsm_signal] ,[ad4] ,[ad5], [decription] "
				    + " FROM [ms03].[dbo].[GPS_Last] "
				    + " LEFT JOIN Tracker ON GPS_Last.sn_imei_id = Tracker.sn_imei_id "
				    + " LEFT JOIN [User_Tracker_Relation] on [User_Tracker_Relation].sn_imei_id = GPS_Last.sn_imei_id "
				    +" LEFT JOIN [User_Relation] on [User_Relation].user_child = [User_Tracker_Relation].user_account and [User_Relation].user_relation_rule = '99' "

				    +" where [GPS_Last].[sn_imei_id] ='"+imei+"'";


			
			//String insert = "UPDATE [dbo].[GPS_Last] SET [decription] = '"+s+"' WHERE sn_imei_id = '"+imei+"'";
			
			DecriptionForm decriptionForm = new DecriptionForm();

			ResultSet rs = stmt.executeQuery(select);
			int i = 0;
			while (rs.next()) {
				decriptionForm.setImei(rs.getString("sn_imei_id"));
				decriptionForm.setTrackerName(rs.getString("tracker_name"));
				decriptionForm.setUserAccount(rs.getString("user_account"));
				decriptionForm.setUserFather(rs.getString("user_father"));
				decriptionForm.setRemark(rs.getString("decription"));
				i++;
			}
			
			ModelMap modelMap = new ModelMap();
			modelMap.addAttribute("decriptionForm", decriptionForm);

			return new ModelAndView("backend/decriptionForm", modelMap);

		//return new ModelAndView("redirect:/backend/decriptionForm.htm");
	}
	
	
	public ModelAndView updateDecription(HttpServletRequest request, HttpServletResponse response, DecriptionForm decriptionForm) throws Exception {

		Connection connect;
		Statement stmt;
		
		String url = serverIP.getTrackingServer();
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

		connect = DriverManager.getConnection(url);
		stmt = connect.createStatement();
		
//		System.out.println(decriptionForm.getImei() + " : "+decriptionForm.getRemark() );	

			String s = decriptionForm.getRemark();

			
			
			String insert = "UPDATE [dbo].[GPS_Last] SET [decription] = N'"+s+"' WHERE sn_imei_id = '"+decriptionForm.getImei()+"'";
			

			try{
				stmt.executeUpdate(insert);
				connect.close();
			}catch (Exception e) {
				System.out.println("Exception : "+e.getMessage());
			}

		return new ModelAndView("redirect:/backend/checkDTKDevice.htm?time=3");
	}
	
	public ModelAndView rvmDecription(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String imei = request.getParameter("imei");

		Connection connect;
		Statement stmt;
		
		String url = serverIP.getTrackingServer();
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

		connect = DriverManager.getConnection(url);
		stmt = connect.createStatement();

			String s = "";

			
			String insert = "UPDATE [dbo].[GPS_Last] SET [decription] = '"+s+"' WHERE sn_imei_id = '"+imei+"'";
			

			try{
				stmt.executeUpdate(insert);
			}catch (Exception e) {
				System.out.println("Exception : "+e.getMessage());
			}

		return new ModelAndView("redirect:/backend/checkDTKDevice.htm?time=3");
	}


}
