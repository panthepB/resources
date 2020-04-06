package distar.project.DLT.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.distar.dtwr.company.domain.User;

@Entity
@Table(name = "excel_import")
public class ExcelImport {

	private Long Id;
	private String imei;
	private String unit_id;
	private String vehicle_chassis_no;
	private String vehicle_id;
	private String vehicle_type;
	private VehicleRegisterType vehicle_register_type;
	private String gps_model;
	private ProvinceDLT province_code;
	private int card_reader;
	private String distar_sn;
	private String customerName;
	private String saleName;
	
	 @Id
	    @Column(name="id")
	public Long getId() {
		return Id;
	}
	public void setId(Long id) {
		Id = id;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public String getUnit_id() {
		return unit_id;
	}
	public void setUnit_id(String unit_id) {
		this.unit_id = unit_id;
	}
	public String getVehicle_chassis_no() {
		return vehicle_chassis_no;
	}
	public void setVehicle_chassis_no(String vehicle_chassis_no) {
		this.vehicle_chassis_no = vehicle_chassis_no;
	}
	public String getVehicle_id() {
		return vehicle_id;
	}
	public void setVehicle_id(String vehicle_id) {
		this.vehicle_id = vehicle_id;
	}
	public String getVehicle_type() {
		return vehicle_type;
	}
	public void setVehicle_type(String vehicle_type) {
		this.vehicle_type = vehicle_type;
	}
	
	
	@ManyToOne
	@JoinColumn(name = "vehicle_register_type")
	public VehicleRegisterType getVehicle_register_type() {
		return vehicle_register_type;
	}
	public void setVehicle_register_type(VehicleRegisterType vehicle_register_type) {
		this.vehicle_register_type = vehicle_register_type;
	}
	public String getGps_model() {
		return gps_model;
	}
	public void setGps_model(String gps_model) {
		this.gps_model = gps_model;
	}
	
	@ManyToOne
	@JoinColumn(name = "province_code")
	public ProvinceDLT getProvince_code() {
		return province_code;
	}
	public void setProvince_code(ProvinceDLT province_code) {
		this.province_code = province_code;
	}
	public int getCard_reader() {
		return card_reader;
	}
	public void setCard_reader(int card_reader) {
		this.card_reader = card_reader;
	}
	public String getDistar_sn() {
		return distar_sn;
	}
	public void setDistar_sn(String distar_sn) {
		this.distar_sn = distar_sn;
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
	

}
