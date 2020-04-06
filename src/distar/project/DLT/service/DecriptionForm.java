package distar.project.DLT.service;

import java.util.Date;

public class DecriptionForm {

	private String unitId;
	private String imei;
	private Date l_datetime;
	private String trackerName;
	private String remark;
	private int status;
	private String userAccount;
	private String userFather;
	
	
	
	public String getUnitId() {
		return unitId;
	}
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public Date getL_datetime() {
		return l_datetime;
	}
	public void setL_datetime(Date l_datetime) {
		this.l_datetime = l_datetime;
	}
	public String getTrackerName() {
		return trackerName;
	}
	public void setTrackerName(String trackerName) {
		this.trackerName = trackerName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getUserAccount() {
		return userAccount;
	}
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
	public String getUserFather() {
		return userFather;
	}
	public void setUserFather(String userFather) {
		this.userFather = userFather;
	}

}
