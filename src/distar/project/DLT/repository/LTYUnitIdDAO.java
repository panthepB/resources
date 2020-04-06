/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  distar.project.DLT.system.domain.Test
 *  distar.project.dao.GenericDAO
 */
package distar.project.DLT.repository;

import java.util.List;

import distar.project.DLT.domain.LTYUnitId;
import distar.project.DLT.domain.RealtimeStatus;
import distar.project.dao.GenericDAO;

public interface LTYUnitIdDAO
extends GenericDAO<LTYUnitId, Long> {

	LTYUnitId findByUnitId(String unitId);

	List<LTYUnitId> findUnitId();

	LTYUnitId findByVehicleId(String vehicleId);
}
