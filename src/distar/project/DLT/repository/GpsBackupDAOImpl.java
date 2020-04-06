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
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import distar.project.DLT.domain.GpsBackup;
import distar.project.DLT.domain.RealtimeStatus;
import distar.project.dao.GenericHibernateDAO;

public class GpsBackupDAOImpl
extends GenericHibernateDAO<GpsBackup, Long>
implements GpsBackupDAO {
	@Override
	public GpsBackup findByUnitId(String unit_id) {
		DetachedCriteria criteria = this.createDetachedCriteria();
		criteria.add((Criterion) Restrictions.eq("unit_id", unit_id));
		List gpsBackups = this.findByCriteria(criteria);
		if (gpsBackups.size() > 0) {
			return (GpsBackup) gpsBackups.get(0);
		}
		return null;
	}
	
}
