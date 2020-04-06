/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  javax.persistence.Column
 *  javax.persistence.Entity
 *  javax.persistence.GeneratedValue
 *  javax.persistence.Id
 *  javax.persistence.Table
 */
package distar.project.DLT.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="driver_log")
public class DriverLog {
    private int Id;
    private String devIDNO;
    private String armDesc;
    private String license;
    private String driver_id;
    private String unitId;
    private int max_speed;
    private String armId;
    private int status;

    @Id
    @GeneratedValue
    public int getId() {
        return this.Id;
    }

    public void setId(int id) {
        this.Id = id;
    }

    @Column(name="DevIDNO")
    public String getDevIDNO() {
        return this.devIDNO;
    }

    public void setDevIDNO(String devIDNO) {
        this.devIDNO = devIDNO;
    }

    @Column(name="ArmDesc")
    public String getArmDesc() {
        return this.armDesc;
    }

    public void setArmDesc(String armDesc) {
        this.armDesc = armDesc;
    }

    public String getLicense() {
        return this.license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getDriver_id() {
        return this.driver_id;
    }

    public void setDriver_id(String driver_id) {
        this.driver_id = driver_id;
    }

    public String getUnitId() {
        return this.unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    @GeneratedValue
    public int getMax_speed() {
        return this.max_speed;
    }

    public void setMax_speed(int max_speed) {
        this.max_speed = max_speed;
    }

    public String getArmId() {
        return this.armId;
    }

    public void setArmId(String armId) {
        this.armId = armId;
    }

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
    
    
}
