/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  distar.project.DLT.system.domain.Test
 *  distar.project.dao.GenericDAO
 */
package distar.project.DLT.repository;

import distar.project.DLT.domain.DriverIDLog;
import distar.project.dao.GenericDAO;

public interface DriverIDLogDAO
extends GenericDAO<DriverIDLog, Long> {

	DriverIDLog findByVehicleId(String vehicleId);
}
