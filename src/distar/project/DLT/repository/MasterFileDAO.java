/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  distar.project.DLT.system.domain.DevAlarm
 *  distar.project.dao.GenericDAO
 */
package distar.project.DLT.repository;

import java.util.List;

import com.distar.dtwr.company.domain.User;

import distar.project.DLT.domain.MasterFile;
import distar.project.dao.GenericDAO;

public interface MasterFileDAO extends GenericDAO<MasterFile, Long> {
	public MasterFile findByIMEI(String imei);

	public MasterFile findByUnitId(String unitId);

	public List<MasterFile> findBMTA();
	
	public List<MasterFile> findMHD();
	
	public List<MasterFile> findMDVR();
	
	public List<MasterFile> findTracking();
	
	public List<MasterFile> findOther();

	public List<MasterFile> findSendMasterfile(int status);

	public List<MasterFile> findByStatus(int status);

	public MasterFile findByImeiAndChass(String imei, String chass);

	public MasterFile findByChass(String chass);

	public MasterFile findByUIDAndChass(String imei, String chass);

	public List<MasterFile> findByStatus(int status, int delStatus);

	public List<MasterFile> findByStatusAndUser(int status, User userId, int delStatus);


}
