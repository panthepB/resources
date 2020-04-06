/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  distar.project.DLT.system.domain.DriverLog
 *  distar.project.dao.GenericDAO
 */
package distar.project.DLT.repository;

import distar.project.DLT.domain.DriverLog;
import distar.project.dao.GenericDAO;

public interface DriverLogDAO
extends GenericDAO<DriverLog, Integer> {
    public DriverLog findByIDNO(String var1);
}
