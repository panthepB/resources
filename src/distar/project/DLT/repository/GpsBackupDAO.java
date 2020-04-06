/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  distar.project.DLT.system.domain.Test
 *  distar.project.dao.GenericDAO
 */
package distar.project.DLT.repository;

import distar.project.DLT.domain.GpsBackup;
import distar.project.DLT.domain.RealtimeStatus;
import distar.project.dao.GenericDAO;

public interface GpsBackupDAO
extends GenericDAO<GpsBackup, Long> {

	GpsBackup findByUnitId(String unit_id);

}
