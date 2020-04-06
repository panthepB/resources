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

import distar.project.DLT.domain.ProvinceDLT;
import distar.project.dao.GenericHibernateDAO;

public class ProvinceDLTDAOImpl
extends GenericHibernateDAO<ProvinceDLT, Long>
implements ProvinceDLTDAO {

	@Override
	public List<ProvinceDLT> orderByProvinceName() {
		DetachedCriteria criteria = createDetachedCriteria();
		criteria.addOrder(Order.asc((String)"provinceName"));
		return findByCriteria(criteria);
	}
	
}
