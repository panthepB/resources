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

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;

import distar.project.DLT.domain.VehicleRegisterType;
import distar.project.dao.GenericHibernateDAO;

public class VehicleRegisterTypeDAOImpl
extends GenericHibernateDAO<VehicleRegisterType, Long>
implements VehicleRegisterTypeDAO {

	@Override
	public List<VehicleRegisterType> orderByVehicleRegisterType() {
		DetachedCriteria criteria = createDetachedCriteria();
		criteria.addOrder(Order.asc((String)"vehicleRegisterType"));
		return findByCriteria(criteria);
	}
	
}
