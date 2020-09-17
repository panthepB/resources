package distar.gateway.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import org.bson.Document;
//import org.springframework.context.annotation.Bean;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.Block;
import com.mongodb.Cursor;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class Util {

	static int port_no = 27017;
//	static String url = "localhost";
	static String url = "10.1.1.2";
	// Method to make a connection to the mongodb server listening on a default
	// port
	private static MongoClient getConnection() {

		MongoClient mongoClntObj = new MongoClient(url, port_no);
		return mongoClntObj;
	}

	public static Boolean searchUserInDb(String loginId, String loginPwd) {
		Boolean user_found = false;
		String db_name = "distar_gateway", db_collection_name = "comp_user";

		MongoDatabase db = getConnection().getDatabase(db_name);
		MongoCollection<Document> col = db.getCollection(db_collection_name);
		List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
		obj.add(new BasicDBObject("user", loginId));
		obj.add(new BasicDBObject("password", loginPwd));

		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("$and", obj);

		FindIterable<Document> cursor = col.find(whereQuery);
		for (Document doc : cursor) {
			user_found = true;
		}
		return user_found;
	}

	public static Integer getTotal(String platform) {
		Integer count = 0;
		String db_name = "distar_gateway", db_collection_name = "dlt_realtime_location";

		MongoDatabase db = getConnection().getDatabase(db_name);
		MongoCollection<Document> col = db.getCollection(db_collection_name);
		List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
		obj.add(new BasicDBObject("platform", platform));

		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("$and", obj);

		count = Integer.parseInt(col.count(whereQuery) + "");
		
//		System.out.println("getTotal : "+count);

		return count;
	}
	public static String getUserImg(String loginId) {
		String img = "";
		MongoClient client = new MongoClient(url, port_no);
		DB database = client.getDB("distar_gateway");
		DBCollection collection = database.getCollection("comp_user");

		BasicDBObject query = new BasicDBObject();
		query.put("user", loginId);

		Cursor cursor = collection.find(query);
		while (cursor.hasNext()) {
			BasicDBObject document = (BasicDBObject) cursor.next();
			img = document.getString("img");
		}
		return img;
	}

	public static Integer getOnline(String platform) {
		Integer count = 0;

		MongoClient client = new MongoClient(url, port_no);
		Locale lc = new Locale("en", "EN");
		DB database = client.getDB("distar_gateway");
		DBCollection collection = database.getCollection("dlt_realtime_location");

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'",lc);
		df.setTimeZone(TimeZone.getTimeZone("UTC"));

		Calendar calBe = Calendar.getInstance();
		Date dateNow = new Date();

		calBe.setTime(dateNow);
		calBe.add(Calendar.MINUTE, -5);
		Date dtBefore = calBe.getTime();

		BasicDBObject query = new BasicDBObject();
		query.put("platform", platform);
		query.put("utcTs", new BasicDBObject().append("$gte", df.format(dtBefore)));

//		System.out.println("Date now getOnline " + df.format(dtBefore) );
		
		count = Integer.parseInt(collection.find(query).count() + "");

		return count;
	}
	
	public static Integer getOnlineMDVR() {
		Integer count = 0;

		MongoClient client = new MongoClient(url, port_no);
		Locale lc = new Locale("en", "EN");
		DB database = client.getDB("distar_gateway");
		DBCollection collection = database.getCollection("dlt_realtime_location");

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'",lc);
		df.setTimeZone(TimeZone.getTimeZone("UTC"));

		Calendar calBe = Calendar.getInstance();
		Date dateNow = new Date();

		calBe.setTime(dateNow);
		calBe.add(Calendar.MINUTE, -5);
		Date dtBefore = calBe.getTime();

		BasicDBObject query = new BasicDBObject();
		query.put("imei", new BasicDBObject()
                .append("$lt", "15")
        );
		query.put("platform", "TG");
		query.put("utcTs", new BasicDBObject().append("$gte", df.format(dtBefore)));

		System.out.println("Date now getOnline " + df.format(dtBefore) );
		
		count = Integer.parseInt(collection.find(query).count() + "");

		return count;
	}
	
	public static Integer getMDVROnlineToday() {
		Integer count = 0;
		Locale lc = new Locale("en", "EN");
		MongoClient client = new MongoClient(url, port_no);

		DB database = client.getDB("distar_gateway");
		DBCollection collection = database.getCollection("dlt_realtime_location");

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'",lc);
		SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd",lc);
		SimpleDateFormat df2 = new SimpleDateFormat("HH:mm:ss",lc);
		df.setTimeZone(TimeZone.getTimeZone("UTC"));

		Calendar calBe = Calendar.getInstance();
		Date dateNow = new Date();

		calBe.setTime(dateNow);
		calBe.add(Calendar.DATE, -1);
		Date dtBefore = calBe.getTime();

		System.out.println("MDVR Date now " + df1.format(dtBefore) + "T17:00:00.000Z");

		BasicDBObject query = new BasicDBObject();
		query.put("imei", new BasicDBObject()
                .append("$lt", "15")
        );
		query.put("platform", "TG");
		query.put("utcTs", new BasicDBObject().append("$gte", df1.format(dtBefore) + "T17:00:00.000Z"));

		count = Integer.parseInt(collection.find(query).count() + "");

		return count;
	}

	public static Integer getOnlineToday(String platform) {
		Integer count = 0;
		Locale lc = new Locale("en", "EN");
		MongoClient client = new MongoClient(url, port_no);

		DB database = client.getDB("distar_gateway");
		DBCollection collection = database.getCollection("dlt_realtime_location");

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'",lc);
		SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd",lc);
		SimpleDateFormat df2 = new SimpleDateFormat("HH:mm:ss",lc);
		df.setTimeZone(TimeZone.getTimeZone("UTC"));

		Calendar calBe = Calendar.getInstance();
		Date dateNow = new Date();

		calBe.setTime(dateNow);
		calBe.add(Calendar.DATE, -1);
		Date dtBefore = calBe.getTime();

		System.out.println("Date now " + df1.format(dtBefore) + "T17:00:00.000Z");

		BasicDBObject query = new BasicDBObject();
		query.put("platform", platform);
		query.put("utcTs", new BasicDBObject().append("$gte", df1.format(dtBefore) + "T17:00:00.000Z"));

		count = Integer.parseInt(collection.find(query).count() + "");

		return count;
	}

	public static ArrayList<String> getUserAll() {

		ArrayList<String> users = new ArrayList();
		MongoClient client = new MongoClient(url, port_no);
		DB database = client.getDB("distar_gateway");
		DBCollection collection = database.getCollection("comp_user");

		BasicDBObject query = new BasicDBObject();
		// query.put("id", loginId);

		Cursor cursor = collection.find(query);
		while (cursor.hasNext()) {
			BasicDBObject document = (BasicDBObject) cursor.next();
			users.add(document.toString());
		}
		users.toString();

		return users;
	}

	public static ArrayList<String> getMasterfileAll() {

		ArrayList<String> users = new ArrayList();
		MongoClient client = new MongoClient(url, port_no);
		DB database = client.getDB("distar_gateway");
		DBCollection collection = database.getCollection("dlt_realtime_location");

		BasicDBObject query = new BasicDBObject();
		// query.put("id", loginId);

		Cursor cursor = collection.find(query);
		while (cursor.hasNext()) {
			BasicDBObject document = (BasicDBObject) cursor.next();
			users.add(document.toString());
		}
		users.toString();

		return users;
	}

	public static String getMasterfile() {

		String masterfile = "{";
		MongoClient client = new MongoClient(url, port_no);
		DB database = client.getDB("distar_gateway");
		DBCollection collection = database.getCollection("dlt_masterfile");

		BasicDBObject query = new BasicDBObject();
		// query.put("id", loginId);

		Cursor cursor = collection.find(query);
		while (cursor.hasNext()) {
			BasicDBObject document = (BasicDBObject) cursor.next();
			masterfile += document.toString();
		}
		masterfile += "};";


		return masterfile;
	}
	
	public static String getMasterfileByUnitId(String unitId) {

		String masterfile = "";
		MongoClient client = new MongoClient(url, port_no);
		DB database = client.getDB("distar_gateway");
		DBCollection collection = database.getCollection("dlt_masterfile");

		BasicDBObject query = new BasicDBObject();
		 query.put("unit_id", unitId);
		// query.put("id", loginId);

		Cursor cursor = collection.find(query);
		while (cursor.hasNext()) {
			BasicDBObject document = (BasicDBObject) cursor.next();
			masterfile += document.toString();
		}
//		masterfile += "};";
		return masterfile;
	}
	
	public static String getTGStatus(String deviceCode) {

		Locale lc = new Locale("en", "EN");
		String data ="";
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'",lc);
		SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		df.setTimeZone(TimeZone.getTimeZone("UTC"));
		
		MongoClient client = new MongoClient(url, port_no);
		DB database = client.getDB("distar_gateway");
		DBCollection collection = database.getCollection("dlt_realtime_status");

		 BasicDBObject query = new BasicDBObject();
		 query.put("device_code", deviceCode);
         
         Cursor cursor = collection.find(query);
         while (cursor.hasNext()) {
             BasicDBObject document = (BasicDBObject) cursor.next();
//             System.out.println(document.getString("status"));
//             System.out.println(document.getString("size"));
             String tgUpdate = null;
             try {
//            	 System.out.println(df1.format(df.parse(document.getString("timeUpdate"))));
            	 tgUpdate = df1.format(df.parse(document.getString("timeUpdate")));
             }catch (Exception e) {
				// TODO: handle exception
			}
             data = document.getString("status")+"&"+document.getString("size")+"&"+ tgUpdate;
         }
		return data;

	}
	
	public static String getCustomer() {

		
		MongoClient client = new MongoClient(url, port_no);
		DB database = client.getDB("distar_gateway");
		DBCollection collection = database.getCollection("dlt_realtime_location");

		BasicDBObject query = new BasicDBObject();
		// query.put("id", loginId);
		
		List ls = collection.distinct("customer_name");
		ArrayList<String> users = new ArrayList<String>(ls);
		String luser = users.toString().replace("[", "[ \"").replace(", ", "\" , \"").replace("]", " \" ]");
		
		return luser;
	}
	
	public static String getSale() {

		MongoClient client = new MongoClient(url, port_no);
		DB database = client.getDB("distar_gateway");
		DBCollection collection = database.getCollection("dlt_realtime_location");

		BasicDBObject query = new BasicDBObject();
		// query.put("id", loginId);
		
		List ls = collection.distinct("sale_name");
		ArrayList<String> users = new ArrayList<String>(ls);

		String luser = users.toString().replace("[", "[ \"").replace(", ", "\" , \"").replace("]", " \" ]");
		
		return luser;
	}
	
	
	
	public static Map getProvince() {
		
		MongoClient client = new MongoClient(url, port_no);
		DB database = client.getDB("distar_gateway");
		DBCollection collection = database.getCollection("comp_province");

		BasicDBObject query = new BasicDBObject();
        BasicDBObject sort = new BasicDBObject();
        sort.put("PROVINCE_NAME", 1.0);
        
        Cursor cursor = collection.find(query).sort(sort);
		
		Map<String, String> province = new HashMap<String, String>();
		
		while (cursor.hasNext()) {
			BasicDBObject document = (BasicDBObject) cursor.next();
			province.put(document.getString("PROVINCE_CODE"), document.getString("PROVINCE_NAME"));
		}
		
		return province;
	}
	
public static Map getVehicleRegisterType() {
		
		MongoClient client = new MongoClient(url, port_no);
		DB database = client.getDB("distar_gateway");
		DBCollection collection = database.getCollection("comp_vehicle_register_type");

		BasicDBObject query = new BasicDBObject();
        BasicDBObject sort = new BasicDBObject();
        sort.put("vehicle_register_type", 1.0);
        
        Cursor cursor = collection.find(query).sort(sort);
		
		Map<String, String> vehicle_register_type = new HashMap<String, String>();
		
		while (cursor.hasNext()) {
			BasicDBObject document = (BasicDBObject) cursor.next();
			vehicle_register_type.put(document.getString("vehicle_register_type"), document.getString("decription"));
//			System.out.println(document.getString("vehicle_register_type") + " : " + document.getString("decription"));
		}
		
		return vehicle_register_type;
	}

	public static List<String> masterfilelist () {
		String json = "";
		final List<String> data = new ArrayList<String>();
		List<String> datatable = new ArrayList<String>();
		
		try (MongoClient client = new MongoClient(url, port_no);) {
            
            MongoDatabase database = client.getDatabase("distar_gateway");
            MongoCollection<Document> collection = database.getCollection("dlt_realtime_location");

            Document query = new Document();
            query.append("platform", "TG");
            query.append("unit_id", new Document()
                    .append("$exists", true)
            );
//            int limit = 50;
            
            Locale lc = new Locale("en", "EN");
    		final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'",lc);
    		final SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",lc);
    		df.setTimeZone(TimeZone.getTimeZone("UTC +07:00"));
    		
            Block<Document> processBlock = new Block<Document>() {
//                @Override
                int i = 1;
                
        		
                public void apply(final Document document) {
                	
                	
                	 data.add("[\""+ i + "\"");
                	 data.add("\"'"+document.getString("imei") + "\"");
                	 data.add("\""+document.getString("vehicle_type") + "\"");
                	 data.add("\"'"+document.getString("vehicle_chassis_no") + "\"");
                	 data.add("\"'"+document.getString("unit_id") + "\"");
                	 data.add("\""+document.getString("deviceName") + "\"");
                	 data.add(" \""+document.getString("vehicle_id") + "\" ");
                	 data.add(" \""+document.getString("province") + "\"");
                	 data.add(" \""+document.getString("register_type_decription") + "\"");
                	 data.add(" \"'"+document.getString("driverId") + "\" ");
                	 data.add("\""+document.get("lat") + "\"");
                	 data.add("\""+document.get("lon") + "\"");
                	 
                	 try {
						
						data.add("\""+ df2.format(df.parse(document.getString("utcTs").toString()))  + "\"");
					}
					catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
//                	 data.add("\""+ document.getString("utcTs")  + "\"");
                	 
//                	 data.add(" \""+document.getString("recvUtcTs") + "\"");
//                	 data.add("\""+document.getInteger("speed") + "\"");
                	 data.add("\""+document.getString("gsm") + "\"");
                	 data.add("\""+document.getString("satNum") + "\"");
                	 if(document.getString("DLTTimeUpdate") != null ) {
	                	 try {
	//                		 System.out.println(df.parse(document.getString("DLTTimeUpdate")));
	                		 data.add("\""+ df2.format(df.parse(document.getString("DLTTimeUpdate")))  + "\"");
	 					}
	 					catch (ParseException e) {
	 						
	 						// TODO Auto-generated catch block
	 						e.printStackTrace();
	 					}
                	 }else {
                		 data.add("\""+ "ไม่พบข้อมูล" + "\"");
                	 }
                	 
                	 data.add("\""+document.getString("sale_name") + "\"");
                	 data.add("\""+document.getString("customer_name") + "\"");
                	 data.add("\""+document.getString("remark") + "\"");
                	 data.add("\""+document.getString("remark2") + "\"");
                	 data.add("\""+document.get("dlt_status") + "\"]");
                	 i++;
//                    data.add(document.toString());
                	 
                } 
            };
//            collection.find(query).limit(limit).forEach(processBlock);
            collection.find(query).forEach(processBlock);
//            System.out.println(data );
        } catch (MongoException e) {
            // handle MongoDB exception
        }
		return data;
	}
	
	public static String getLTYLastStatus(String vehicleId) {

		String LTYLastStatus = "";
		MongoClient client = new MongoClient(url, port_no);
		DB database = client.getDB("distar_gateway");
		DBCollection collection = database.getCollection("dlt_masterfile");

		BasicDBObject query = new BasicDBObject();
		 query.put("imei", vehicleId);
		// query.put("id", loginId);

		Cursor cursor = collection.find(query);
		while (cursor.hasNext()) {
			BasicDBObject document = (BasicDBObject) cursor.next();
			LTYLastStatus += document.toString();
		}
//		masterfile += "};";
		return LTYLastStatus;
	}

}