package distar.project.DLT.repository;

import distar.project.DLT.domain.RealtimeStatus;
import distar.project.dao.GenericDAO;
import java.util.List;

public interface RealtimeStatusDAO
extends GenericDAO<RealtimeStatus, Long> {

	RealtimeStatus findByDevice(int device);
}
