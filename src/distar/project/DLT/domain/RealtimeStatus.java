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
@Table(name = "realtime_status")
public class RealtimeStatus {
	private Long id;
	private Date timeUpdate;
	private int device; // 0 = ListMasterFile;
						// 1 = DTK3G-100T ; 
						// 2 = AddMasterFile ; 
						// 3 = MDVR ; 
						// 4 = MHD ;
						// 5 = LTY ; 
						// 6 = TP2 ;
						// 7 = TG ;
						// 9 = SCCC ;
						// 99 = Thaipost ;
	private String status;
	private String exception;
	private String dataLog;
	private int  size;
	

	@Id
	@GeneratedValue
	// @Column(name="PROVINCE_ID")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getTimeUpdate() {
		return timeUpdate;
	}
	public void setTimeUpdate(Date timeUpdate) {
		this.timeUpdate = timeUpdate;
	}
	public int getDevice() {
		return device;
	}
	public void setDevice(int device) {
		this.device = device;
	}
	public String getException() {
		return exception;
	}
	public void setException(String exception) {
		this.exception = exception;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDataLog() {
		return dataLog;
	}
	public void setDataLog(String dataLog) {
		this.dataLog = dataLog;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}

	

	
}
