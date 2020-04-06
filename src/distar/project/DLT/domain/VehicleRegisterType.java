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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "vehicle_register_type")
public class VehicleRegisterType {
	private Long id;
	private Long vehicleRegisterType;
	private String decription;

	
	@Column(name = "id")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	@Id
	@Column(name = "vehicle_register_type")
	public Long getVehicleRegisterType() {
		return vehicleRegisterType;
	}
	public void setVehicleRegisterType(Long vehicleRegisterType) {
		this.vehicleRegisterType = vehicleRegisterType;
	}
	public String getDecription() {
		return decription;
	}
	public void setDecription(String decription) {
		this.decription = decription;
	}

}
