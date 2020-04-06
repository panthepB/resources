/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  distar.project.DLT.system.domain.DevAlarm
 *  distar.project.dao.GenericDAO
 */
package distar.project.DLT.repository;

import java.util.List;

import distar.project.DLT.domain.APIMasterfile;
import distar.project.dao.GenericDAO;

public interface APIMasterfileDAO extends GenericDAO<APIMasterfile, Long> {
	public APIMasterfile findByIMEI(String imei);

	public APIMasterfile findByUnitId(String unitId);

	public List<APIMasterfile> findBMTA();
	
	public List<APIMasterfile> findMHD();
	
	public List<APIMasterfile> findMDVR();
	
	public List<APIMasterfile> findTracking();
	
	public List<APIMasterfile> findOther();

	public List<APIMasterfile> findSendMasterfile(int status);

}
