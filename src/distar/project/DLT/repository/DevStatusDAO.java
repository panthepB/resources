/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  distar.project.DLT.system.domain.DevStatus
 *  distar.project.dao.GenericDAO
 */
package distar.project.DLT.repository;

import distar.project.DLT.domain.DevStatus;
import distar.project.dao.GenericDAO;
import java.util.List;

public interface DevStatusDAO
extends GenericDAO<DevStatus, Integer> {
    public List<DevStatus> findDevIDNO();

    public DevStatus findByIDNO(String var1);
}
