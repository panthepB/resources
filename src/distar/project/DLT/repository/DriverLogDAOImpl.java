/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  distar.project.DLT.system.domain.DriverLog
 *  distar.project.dao.GenericHibernateDAO
 *  org.hibernate.criterion.Criterion
 *  org.hibernate.criterion.DetachedCriteria
 *  org.hibernate.criterion.Restrictions
 */
package distar.project.DLT.repository;

import distar.project.DLT.domain.DriverLog;
import distar.project.DLT.repository.DriverLogDAO;
import distar.project.dao.GenericHibernateDAO;
import java.util.List;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

public class DriverLogDAOImpl
extends GenericHibernateDAO<DriverLog, Integer>
implements DriverLogDAO {
    @Override
    public DriverLog findByIDNO(String devIdno) {
        DetachedCriteria criteria = this.createDetachedCriteria();
        criteria.add((Criterion)Restrictions.eq((String)"devIDNO", (Object)devIdno));
        List driverLogs = this.findByCriteria(criteria);
        if (driverLogs.size() > 0) {
            return (DriverLog)driverLogs.get(0);
        }
        return null;
    }
}
