/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  distar.project.DLT.system.domain.Test
 *  distar.project.dao.GenericHibernateDAO
 *  org.hibernate.criterion.DetachedCriteria
 */
package distar.project.DLT.repository;

import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import distar.project.DLT.domain.DevStatus;
import distar.project.DLT.domain.LTYUnitId;
import distar.project.dao.GenericHibernateDAO;

public class LTYUnitIdDAOImpl
extends GenericHibernateDAO<LTYUnitId, Long>
implements LTYUnitIdDAO {
	@Override
	public LTYUnitId findByUnitId(String unitId) {
		DetachedCriteria criteria = this.createDetachedCriteria();
		criteria.add((Criterion) Restrictions.eq("unitId", unitId));
		List masterFiles = this.findByCriteria(criteria);
		if (masterFiles.size() > 0) {
			return (LTYUnitId) masterFiles.get(0);
		}
		return null;
	}
	
	@Override
	public LTYUnitId findByVehicleId(String vehicleId) {
		DetachedCriteria criteria = this.createDetachedCriteria();
		criteria.add((Criterion) Restrictions.eq("vehicleId", vehicleId));
		List masterFiles = this.findByCriteria(criteria);
		if (masterFiles.size() > 0) {
			return (LTYUnitId) masterFiles.get(0);
		}
		return null;
	}
	
	@Override
    public List<LTYUnitId> findUnitId() {
        DetachedCriteria criteria = this.createDetachedCriteria();
//        criteria.add((Criterion)Restrictions.isNotNull("unitId"));
        return this.findByCriteria(criteria);
    }
	
}
