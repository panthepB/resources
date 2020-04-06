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

import distar.project.DLT.domain.APIMasterfile;
import distar.project.dao.GenericHibernateDAO;

public class APIMasterfileDAOImpl extends GenericHibernateDAO<APIMasterfile, Long> implements APIMasterfileDAO {
	@Override
	public APIMasterfile findByIMEI(String imei) {
		DetachedCriteria criteria = this.createDetachedCriteria();
		criteria.add((Criterion) Restrictions.eq("imei", imei));
		List masterFiles = this.findByCriteria(criteria);
		if (masterFiles.size() > 0) {
			return (APIMasterfile) masterFiles.get(0);
		}
		return null;
	}
	
	@Override
	public APIMasterfile findByUnitId(String unitId) {
		DetachedCriteria criteria = this.createDetachedCriteria();
		criteria.add((Criterion) Restrictions.like("unitId", "%"+unitId+"%"));
		List masterFiles = this.findByCriteria(criteria);
		if (masterFiles.size() > 0) {
			return (APIMasterfile) masterFiles.get(0);
		}
		return null;
	}
	
	
	@Override
	public List<APIMasterfile> findBMTA() {
		DetachedCriteria criteria = createDetachedCriteria();
		criteria.add((Criterion) Restrictions.eq("gpsModel", "0430005"));
		return findByCriteria(criteria);
	}
	
	
	@Override
	public List<APIMasterfile> findMHD() {
		DetachedCriteria criteria = createDetachedCriteria();
		criteria.add(Restrictions.disjunction()
		        .add(Restrictions.eq("gpsModel", "0430006"))
		        .add(Restrictions.eq("gpsModel", "0430008"))
		    );
		return findByCriteria(criteria);
	}
	
	@Override
	public List<APIMasterfile> findMDVR() {
		DetachedCriteria criteria = createDetachedCriteria();
		criteria.add(Restrictions.disjunction()
		        .add(Restrictions.eq("gpsModel", "0430001"))
		        .add(Restrictions.eq("gpsModel", "0430002"))
		    );
		return findByCriteria(criteria);
	}
	
	@Override
	public List<APIMasterfile> findTracking() {
		DetachedCriteria criteria = createDetachedCriteria();
		criteria.add((Criterion) Restrictions.eq("gpsModel", "0430003"));
		return findByCriteria(criteria);
	}
	
	@Override
	public List<APIMasterfile> findOther() {
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
	public List<APIMasterfile> findSendMasterfile(int status) {
		DetachedCriteria criteria = createDetachedCriteria();
		criteria.add((Criterion) Restrictions.eq("dltStatus", status));
		return findByCriteria(criteria);
	}
}
