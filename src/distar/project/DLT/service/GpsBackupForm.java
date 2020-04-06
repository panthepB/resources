/*
 * Decompiled with CFR 0_114.
 */
package distar.project.DLT.service;

public class GpsBackupForm {

	private String unitId;
	private String lat;
	private String lon;
	private String utc_ts;
	private String recv_utc_ts;
	private int value_record;
	private String date_start;
	private String time_start;

	public String getUnitId() {
		return unitId;
	}
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getLon() {
		return lon;
	}
	public void setLon(String lon) {
		this.lon = lon;
	}
	public String getUtc_ts() {
		return utc_ts;
	}
	public void setUtc_ts(String utc_ts) {
		this.utc_ts = utc_ts;
	}
	public String getRecv_utc_ts() {
		return recv_utc_ts;
	}
	public void setRecv_utc_ts(String recv_utc_ts) {
		this.recv_utc_ts = recv_utc_ts;
	}

	public String getDate_start() {
		return date_start;
	}
	public void setDate_start(String date_start) {
		this.date_start = date_start;
	}
	public String getTime_start() {
		return time_start;
	}
	public void setTime_end(String time_start) {
		this.time_start = time_start;
	}
	public int getValue_record() {
		return value_record;
	}
	public void setValue_record(int value_record) {
		this.value_record = value_record;
	}
	public void setTime_start(String time_start) {
		this.time_start = time_start;
	}

}
