package distar.project.DLT.repository;

import distar.project.DLT.domain.DevAlarm;
import distar.project.DLT.repository.DevAlarmDAO;
import distar.project.dao.GenericHibernateDAO;
import java.util.List;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class DevAlarmDAOImpl
extends GenericHibernateDAO<DevAlarm, String>
implements DevAlarmDAO {
    @Override
    public DevAlarm findByIDNO(String devIdno) {
        DetachedCriteria criteria = this.createDetachedCriteria();
        criteria.add((Criterion)Restrictions.eq((String)"devIDNO", (Object)devIdno));
        criteria.add((Criterion)Restrictions.eq((String)"armType", (Object)113));
        criteria.add((Criterion)Restrictions.eq((String)"armInfo", (Object)4));
        criteria.addOrder(Order.desc((String)"armTime"));
        List devAlarms = this.findByCriteria(criteria);
        if (devAlarms.size() > 0) {
            return (DevAlarm)devAlarms.get(0);
        }
        return null;
    }
}
