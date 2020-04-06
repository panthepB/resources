package distar.project.DLT.repository;

import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import distar.project.DLT.domain.LTYUnitId;
import distar.project.DLT.domain.RealtimeStatus;
import distar.project.dao.GenericHibernateDAO;

public class RealtimeStatusDAOImpl
extends GenericHibernateDAO<RealtimeStatus, Long>
implements RealtimeStatusDAO {
	@Override
	public RealtimeStatus findByDevice(int device) {
		DetachedCriteria criteria = this.createDetachedCriteria();
		criteria.add((Criterion) Restrictions.eq("device", device));
		criteria.addOrder(Order.desc((String)"timeUpdate"));
		try{
			List realtimeStatus = this.findByCriteria(criteria);
			if (realtimeStatus.size() > 0) {
				return (RealtimeStatus) realtimeStatus.get(0);
			}
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println("Exception : "+e.getMessage());
		}
		
		return null;
	}
	
	
}
