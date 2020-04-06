package distar.project.DLT.repository;

import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import distar.project.DLT.domain.DriverIDLog;
import distar.project.dao.GenericHibernateDAO;

public class DriverIDLogDAOImpl
extends GenericHibernateDAO<DriverIDLog, Long>
implements DriverIDLogDAO {
	
	@Override
	public DriverIDLog findByVehicleId(String vehicleId) {
		DetachedCriteria criteria = this.createDetachedCriteria();
		criteria.add((Criterion) Restrictions.eq("vehicleId", vehicleId));
		List masterFiles = this.findByCriteria(criteria);
		if (masterFiles.size() > 0) {
			return (DriverIDLog) masterFiles.get(0);
		}
		return null;
	}
	
	
	
}
