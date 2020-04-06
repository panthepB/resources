package distar.project.DLT.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.distar.dtwr.company.domain.User;

@Entity
@Table(name = "master_file")
public class MasterFile implements Serializable {
	private Long id;
	private String unitId;
	private String vehicleId;
	private String vehicleType;
	private String vehicleChassisNo;
	private VehicleRegisterType vehicleRegisterType;
	private ProvinceDLT provinceCode;
	private int cardReader;
	private String imei;
	private String gpsModel;
	private String customerName;
	private String saleName;
	private Date dateCreated;
	private Date dateUpdated;
	private User userCreate;
	private User userUpdate;
	private Date dltUpdateTime;
	private Date installDate;
	private int dltStatus;
	private String remark;
	private String remark2;
	private String trackerName;
	private String trackerSimNumber;
	private Date trackerRegisterTime;
	private Date trackerExpiredTime;
	private int moveStatus;
	private String unitDummy;
	private int codeGen;
	private int deleteStatus;
	private int status;
	private String customerTel;
	
	
	
	public String getCustomerTel() {
		return customerTel;
	}

	public void setCustomerTel(String customerTel) {
		this.customerTel = customerTel;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getDeleteStatus() {
		return deleteStatus;
	}

	public void setDeleteStatus(int deleteStatus) {
		this.deleteStatus = deleteStatus;
	}

	public String getUnitDummy() {
		return unitDummy;
	}

	public void setUnitDummy(String unitDummy) {
		this.unitDummy = unitDummy;
	}



	public int getCodeGen() {
		return codeGen;
	}

	public void setCodeGen(int codeGen) {
		this.codeGen = codeGen;
	}

	public int getMoveStatus() {
		return moveStatus;
	}

	public void setMoveStatus(int moveStatus) {
		this.moveStatus = moveStatus;
	}

	public String getRemark2() {
		return remark2;
	}

	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Id
	@GeneratedValue
	@Column(name = "master_file_id")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
	}

	public String getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	public String getVehicleChassisNo() {
		return vehicleChassisNo;
	}

	public void setVehicleChassisNo(String vehicleChassisNo) {
		this.vehicleChassisNo = vehicleChassisNo;
	}

	@ManyToOne
	@JoinColumn(name = "vehicle_register_type")
	public VehicleRegisterType getVehicleRegisterType() {
		return vehicleRegisterType;
	}

	public void setVehicleRegisterType(VehicleRegisterType vehicleRegisterType) {
		this.vehicleRegisterType = vehicleRegisterType;
	}

//	@ManyToOne
//	@JoinColumn(name = "province_code")

	@ManyToOne(optional = false)
	@JoinColumn(name = "province_code", insertable = false, updatable = false)
	public ProvinceDLT getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(ProvinceDLT provinceCode) {
		this.provinceCode = provinceCode;
	}

	public int getCardReader() {
		return cardReader;
	}

	public void setCardReader(int cardReader) {
		this.cardReader = cardReader;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getGpsModel() {
		return gpsModel;
	}

	public void setGpsModel(String gpsModel) {
		this.gpsModel = gpsModel;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDateUpdated() {
		return dateUpdated;
	}

	public void setDateUpdated(Date dateUpdated) {
		this.dateUpdated = dateUpdated;
	}

	@ManyToOne
	@JoinColumn(name = "user_create")
	public User getUserCreate() {
		return userCreate;
	}

	public void setUserCreate(User userCreate) {
		this.userCreate = userCreate;
	}

	@ManyToOne
	@JoinColumn(name = "user_update")
	public User getUserUpdate() {
		return userUpdate;
	}

	public void setUserUpdate(User userUpdate) {
		this.userUpdate = userUpdate;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getSaleName() {
		return saleName;
	}

	public void setSaleName(String saleName) {
		this.saleName = saleName;
	}

	public Date getDltUpdateTime() {
		return dltUpdateTime;
	}

	public void setDltUpdateTime(Date dltUpdateTime) {
		this.dltUpdateTime = dltUpdateTime;
	}

	public Date getInstallDate() {
		return installDate;
	}

	public void setInstallDate(Date installDate) {
		this.installDate = installDate;
	}

	public int getDltStatus() {
		return dltStatus;
	}

	public void setDltStatus(int dltStatus) {
		this.dltStatus = dltStatus;
	}

	public String getTrackerName() {
		return trackerName;
	}

	public void setTrackerName(String trackerName) {
		this.trackerName = trackerName;
	}

	public String getTrackerSimNumber() {
		return trackerSimNumber;
	}

	public void setTrackerSimNumber(String trackerSimNumber) {
		this.trackerSimNumber = trackerSimNumber;
	}

	public Date getTrackerRegisterTime() {
		return trackerRegisterTime;
	}

	public void setTrackerRegisterTime(Date trackerRegisterTime) {
		this.trackerRegisterTime = trackerRegisterTime;
	}

	public Date getTrackerExpiredTime() {
		return trackerExpiredTime;
	}

	public void setTrackerExpiredTime(Date trackerExpiredTime) {
		this.trackerExpiredTime = trackerExpiredTime;
	}

}
