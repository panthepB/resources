/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  distar.project.DLT.system.domain.DevAlarm
 *  distar.project.dao.GenericDAO
 */
package distar.project.DLT.repository;

import distar.project.DLT.domain.ExcelImport;
import distar.project.DLT.domain.LTYUnitId;
import distar.project.dao.GenericDAO;

public interface ExcelImportDAO
extends GenericDAO<ExcelImport, Long> {

	ExcelImport findByUnitId(String unit_id);
    
}
