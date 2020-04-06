/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  distar.project.DLT.system.domain.DevAlarm
 *  distar.project.dao.GenericDAO
 */
package distar.project.DLT.repository;

import java.util.List;

import distar.project.DLT.domain.VehicleRegisterType;
import distar.project.dao.GenericDAO;

public interface VehicleRegisterTypeDAO
extends GenericDAO<VehicleRegisterType, Long> {

	List<VehicleRegisterType> orderByVehicleRegisterType();
	
}
