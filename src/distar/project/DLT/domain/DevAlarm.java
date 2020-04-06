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
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="dev_alarm")
public class DevAlarm {
    private String Guid;
    private String devIDNO;
    private int armType;
    private int armInfo;
    private String armDesc;
    private Date armTime;

    @Id
    @Column(name="Guid")
    public String getGuid() {
        return this.Guid;
    }

    public void setGuid(String guid) {
        this.Guid = guid;
    }

    @Column(name="DevIDNO")
    public String getDevIDNO() {
        return this.devIDNO;
    }

    public void setDevIDNO(String devIDNO) {
        this.devIDNO = devIDNO;
    }

    @Column(name="ArmType")
    public int getArmType() {
        return this.armType;
    }

    public void setArmType(int armType) {
        this.armType = armType;
    }

    @Column(name="ArmInfo")
    public int getArmInfo() {
        return this.armInfo;
    }

    public void setArmInfo(int armInfo) {
        this.armInfo = armInfo;
    }

    @Column(name="ArmDesc")
    public String getArmDesc() {
        return this.armDesc;
    }

    public void setArmDesc(String armDesc) {
        this.armDesc = armDesc;
    }

    @Column(name="ArmTime")
    public Date getArmTime() {
        return this.armTime;
    }

    public void setArmTime(Date armTime) {
        this.armTime = armTime;
    }
}
