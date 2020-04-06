/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  javax.persistence.Column
 *  javax.persistence.Entity
 *  javax.persistence.Id
 *  javax.persistence.Table
 */
package distar.project.DLT.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "gps_backup")
public class GpsBackup {
	private Long id;
	private String driver_id;
	private String unit_id;
	private int seq;
	private String utc_ts;
	private String recv_utc_ts;
	private String lat;
	private String lon;
	private int alt;
	private int speed;
	private int engine_status;
	private int fix;
	private String license;
	private int course;
	private int hdop;
	private int num_sats;
	private int gsm_cell;
	private int gsm_loc;
	private int gsm_rssi;
	private int mileage;
	private int ext_power_status;
	private int ext_power;
	private int high_acc_count;
	private int over_speed_count;
	private int max_speed;

	@Id
	@GeneratedValue
	// @Column(name="PROVINCE_ID")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDriver_id() {
		return driver_id;
	}
	public void setDriver_id(String driver_id) {
		this.driver_id = driver_id;
	}
	public String getUnit_id() {
		return unit_id;
	}
	public void setUnit_id(String unit_id) {
		this.unit_id = unit_id;
	}

	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
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
	public int getAlt() {
		return alt;
	}
	public void setAlt(int alt) {
		this.alt = alt;
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public int getEngine_status() {
		return engine_status;
	}
	public void setEngine_status(int engine_status) {
		this.engine_status = engine_status;
	}
	public int getFix() {
		return fix;
	}
	public void setFix(int fix) {
		this.fix = fix;
	}
	public String getLicense() {
		return license;
	}
	public void setLicense(String license) {
		this.license = license;
	}
	public int getCourse() {
		return course;
	}
	public void setCourse(int course) {
		this.course = course;
	}
	public int getHdop() {
		return hdop;
	}
	public void setHdop(int hdop) {
		this.hdop = hdop;
	}
	public int getNum_sats() {
		return num_sats;
	}
	public void setNum_sats(int num_sats) {
		this.num_sats = num_sats;
	}
	public int getGsm_cell() {
		return gsm_cell;
	}
	public void setGsm_cell(int gsm_cell) {
		this.gsm_cell = gsm_cell;
	}
	public int getGsm_loc() {
		return gsm_loc;
	}
	public void setGsm_loc(int gsm_loc) {
		this.gsm_loc = gsm_loc;
	}
	public int getGsm_rssi() {
		return gsm_rssi;
	}
	public void setGsm_rssi(int gsm_rssi) {
		this.gsm_rssi = gsm_rssi;
	}
	public int getMileage() {
		return mileage;
	}
	public void setMileage(int mileage) {
		this.mileage = mileage;
	}
	public int getExt_power_status() {
		return ext_power_status;
	}
	public void setExt_power_status(int ext_power_status) {
		this.ext_power_status = ext_power_status;
	}
	public int getExt_power() {
		return ext_power;
	}
	public void setExt_power(int ext_power) {
		this.ext_power = ext_power;
	}
	public int getHigh_acc_count() {
		return high_acc_count;
	}
	public void setHigh_acc_count(int high_acc_count) {
		this.high_acc_count = high_acc_count;
	}
	public int getOver_speed_count() {
		return over_speed_count;
	}
	public void setOver_speed_count(int over_speed_count) {
		this.over_speed_count = over_speed_count;
	}
	public int getMax_speed() {
		return max_speed;
	}
	public void setMax_speed(int max_speed) {
		this.max_speed = max_speed;
	}

}
