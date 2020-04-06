/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  distar.project.DLT.system.domain.DevStatus
 *  distar.project.dao.GenericHibernateDAO
 *  org.hibernate.criterion.Criterion
 *  org.hibernate.criterion.DetachedCriteria
 *  org.hibernate.criterion.Restrictions
 */
package distar.project.DLT.repository;

import distar.project.DLT.domain.DevStatus;
import distar.project.DLT.repository.DevStatusDAO;
import distar.project.dao.GenericHibernateDAO;
import java.util.List;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

public class DevStatusDAOImpl
extends GenericHibernateDAO<DevStatus, Integer>
implements DevStatusDAO {
    @Override
    public List<DevStatus> findDevIDNO() {
        DetachedCriteria criteria = this.createDetachedCriteria();
        criteria.add((Criterion)Restrictions.eq((String)"online", (Object)1));
        criteria.add((Criterion)Restrictions.gt((String)"weiDu", (Object)0));
        return this.findByCriteria(criteria);
    }

    @Override
    public DevStatus findByIDNO(String devIdno) {
        DetachedCriteria criteria = this.createDetachedCriteria();
        criteria.add((Criterion)Restrictions.eq((String)"devIDNO", (Object)devIdno));
        List devStatus = this.findByCriteria(criteria);
        if (devStatus.size() > 0) {
            return (DevStatus)devStatus.get(0);
        }
        return null;
    }
}
