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

import distar.project.DLT.domain.ExcelImport;
import distar.project.dao.GenericHibernateDAO;

public class ExcelImportDAOImpl
extends GenericHibernateDAO<ExcelImport, Long>
implements ExcelImportDAO {
   
	@Override
	public ExcelImport findByUnitId(String unit_id) {
		DetachedCriteria criteria = this.createDetachedCriteria();
		criteria.add((Criterion) Restrictions.eq("unit_id", unit_id));
		List masterFiles = this.findByCriteria(criteria);
		if (masterFiles.size() > 0) {
			return (ExcelImport) masterFiles.get(0);
		}
		return null;
	}
}
