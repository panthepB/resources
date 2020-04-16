/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  distar.project.DLT.system.domain.DevAlarm
 *  distar.project.dao.GenericHibernateDAO
 *  org.hibernate.criterion.Criterion
 *  org.hibernate.criterion.DetachedCriteria
 *  org.hibernate.criterion.Order
 *  org.hibernate.criterion.Restrictions
 */
package distar.project.DLT.repository;

import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.distar.dtwr.company.domain.User;

import distar.project.DLT.domain.MasterFile;
import distar.project.dao.GenericHibernateDAO;

public class MasterFileDAOImpl extends GenericHibernateDAO<MasterFile, Long> implements MasterFileDAO {
	@Override
	public MasterFile findByIMEI(String imei) {
		DetachedCriteria criteria = this.createDetachedCriteria();
		criteria.add((Criterion) Restrictions.eq("imei", imei));
//		criteria.add((Criterion) Restrictions.eq("dltStatus", 1));
		List masterFiles = this.findByCriteria(criteria);
		if (masterFiles.size() > 0) {
			return (MasterFile) masterFiles.get(0);
		}
		return null;
	}
	
	@Override
	public MasterFile findByUnitId(String unitId) {
		DetachedCriteria criteria = this.createDetachedCriteria();
		criteria.add((Criterion) Restrictions.like("unitId", "%"+unitId+"%"));
		List masterFiles = this.findByCriteria(criteria);
		if (masterFiles.size() > 0) {
			return (MasterFile) masterFiles.get(0);
		}
		return null;
	}
	
	
	@Override
	public List<MasterFile> findBMTA() {
		DetachedCriteria criteria = createDetachedCriteria();
		criteria.add((Criterion) Restrictions.eq("gpsModel", "0430005"));
		return findByCriteria(criteria);
	}
	
	
	@Override
	public List<MasterFile> findMHD() {
		DetachedCriteria criteria = createDetachedCriteria();
		criteria.add(Restrictions.disjunction()
		        .add(Restrictions.eq("gpsModel", "0430006"))
		        .add(Restrictions.eq("gpsModel", "0430008"))
		    );
		return findByCriteria(criteria);
	}
	
	@Override
	public List<MasterFile> findMDVR() {
		DetachedCriteria criteria = createDetachedCriteria();
		criteria.add(Restrictions.disjunction()
		        .add(Restrictions.eq("gpsModel", "0430001"))
		        .add(Restrictions.eq("gpsModel", "0430002"))
		    );
		return findByCriteria(criteria);
	}
	
	@Override
	public List<MasterFile> findTracking() {
		DetachedCriteria criteria = createDetachedCriteria();
		criteria.add((Criterion) Restrictions.eq("gpsModel", "0430003"));
		return findByCriteria(criteria);
	}
	
	@Override
	public List<MasterFile> findOther() {
		DetachedCriteria criteria = createDetachedCriteria();
		criteria.add((Criterion) Restrictions.ne("gpsModel", "0430001"));
		criteria.add((Criterion) Restrictions.ne("gpsModel", "0430002"));
		criteria.add((Criterion) Restrictions.ne("gpsModel", "0430003"));
		criteria.add((Criterion) Restrictions.ne("gpsModel", "0430005"));
		criteria.add((Criterion) Restrictions.ne("gpsModel", "0430006"));
		criteria.add((Criterion) Restrictions.ne("gpsModel", "0430007"));
		criteria.add((Criterion) Restrictions.ne("gpsModel", "0430008"));
		
		return findByCriteria(criteria);
	}
	
	@Override
	public List<MasterFile> findSendMasterfile(int status) {
		DetachedCriteria criteria = createDetachedCriteria();
		criteria.add((Criterion) Restrictions.eq("dltStatus", status));
		return findByCriteria(criteria);
	}
	
	@Override
	public List<MasterFile> findByStatus(int status,int delStatus) {
		DetachedCriteria criteria = createDetachedCriteria();
		criteria.add((Criterion) Restrictions.eq("status", status));
		criteria.add((Criterion) Restrictions.eq("deleteStatus", delStatus));
		return findByCriteria(criteria);
	}
	
	@Override
	public List<MasterFile> findByStatus(int status) {
		DetachedCriteria criteria = createDetachedCriteria();
		criteria.add((Criterion) Restrictions.eq("status", status));
		return findByCriteria(criteria);
	}
	
	@Override
	public List<MasterFile> findByStatusAndUser(int status,User userId,int delStatus) {
		DetachedCriteria criteria = createDetachedCriteria();
		criteria.add((Criterion) Restrictions.eq("deleteStatus", delStatus));
		criteria.add((Criterion) Restrictions.eq("status", status));
		criteria.add((Criterion) Restrictions.eq("userCreate", userId));
		return findByCriteria(criteria);
	}
	

	@Override
	public MasterFile findByImeiAndChass(String imei,String chass) {
		DetachedCriteria criteria = this.createDetachedCriteria();
		criteria.add((Criterion) Restrictions.eq("imei", imei));
		criteria.add((Criterion) Restrictions.eq("vehicleChassisNo", chass));
//		criteria.add((Criterion) Restrictions.eq("dltStatus", 1));
		List masterFiles = this.findByCriteria(criteria);
		if (masterFiles.size() > 0) {
			return (MasterFile) masterFiles.get(0);
		}
		return null;
	}
	
	@Override
	public MasterFile findByChass(String chass) {
		DetachedCriteria criteria = this.createDetachedCriteria();
		criteria.add((Criterion) Restrictions.eq("vehicleChassisNo", chass));
//		criteria.add((Criterion) Restrictions.eq("dltStatus", 1));
		List masterFiles = this.findByCriteria(criteria);
		if (masterFiles.size() > 0) {
			return (MasterFile) masterFiles.get(0);
		}
		return null;
	}
	
	@Override
	public MasterFile findByUIDAndChass(String uid,String chass) {
		DetachedCriteria criteria = this.createDetachedCriteria();
		criteria.add((Criterion) Restrictions.eq("unitId", uid));
		criteria.add((Criterion) Restrictions.eq("vehicleChassisNo", chass));
//		criteria.add((Criterion) Restrictions.eq("dltStatus", 1));
		List masterFiles = this.findByCriteria(criteria);
		if (masterFiles.size() > 0) {
			return (MasterFile) masterFiles.get(0);
		}
		return null;
	}
}
