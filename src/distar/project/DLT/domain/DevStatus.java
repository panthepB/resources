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
@Table(name="dev_status")
public class DevStatus {
    private int ID;
    private String devIDNO;
    private int online;
    private int weiDu;
    private int jingDu;
    private int speed;
    private Date GPSTime;
    private Date updateTime;

    @Id
    @Column(name="ID")
    public int getID() {
        return this.ID;
    }

    public void setID(int iD) {
        this.ID = iD;
    }

    @Column(name="DevIDNO")
    public String getDevIDNO() {
        return this.devIDNO;
    }

    public void setDevIDNO(String devIDNO) {
        this.devIDNO = devIDNO;
    }

    @Column(name="Online")
    public int getOnline() {
        return this.online;
    }

    public void setOnline(int online) {
        this.online = online;
    }

    @Column(name="WeiDu")
    public int getWeiDu() {
        return this.weiDu;
    }

    public void setWeiDu(int weiDu) {
        this.weiDu = weiDu;
    }

    @Column(name="JingDu")
    public int getJingDu() {
        return this.jingDu;
    }

    public void setJingDu(int jingDu) {
        this.jingDu = jingDu;
    }

    @Column(name="Speed")
    public int getSpeed() {
        return this.speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    @Column(name="GPSTime")
    public Date getGPSTime() {
        return this.GPSTime;
    }

    public void setGPSTime(Date gPSTime) {
        this.GPSTime = gPSTime;
    }

    @Column(name="UpdateTime")
    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
