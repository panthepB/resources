/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  distar.project.DLT.system.domain.DevAlarm
 *  distar.project.dao.GenericDAO
 */
package distar.project.DLT.repository;

import distar.project.DLT.domain.DevAlarm;
import distar.project.dao.GenericDAO;

public interface DevAlarmDAO
extends GenericDAO<DevAlarm, String> {
    public DevAlarm findByIDNO(String var1);
}
