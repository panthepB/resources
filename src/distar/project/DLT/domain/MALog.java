package distar.project.DLT.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.distar.dtwr.company.domain.User;

@Entity
@Table(name = "ma_log")
public class MALog {

	private Long id;
	private String imei;
	private String trackerName;
	private Date timeUpdate;
	private Date timeBefore;
	private Date timeExpire;
	private User userUpdate;


	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public String getTrackerName() {
		return trackerName;
	}
	public void setTrackerName(String trackerName) {
		this.trackerName = trackerName;
	}
	public Date getTimeUpdate() {
		return timeUpdate;
	}
	public void setTimeUpdate(Date timeUpdate) {
		this.timeUpdate = timeUpdate;
	}
	public Date getTimeBefore() {
		return timeBefore;
	}
	public void setTimeBefore(Date timeBefore) {
		this.timeBefore = timeBefore;
	}
	public Date getTimeExpire() {
		return timeExpire;
	}
	public void setTimeExpire(Date timeExpire) {
		this.timeExpire = timeExpire;
	}
	
	@ManyToOne
	@JoinColumn(name = "user_update")
	public User getUserUpdate() {
		return userUpdate;
	}
	public void setUserUpdate(User userUpdate) {
		this.userUpdate = userUpdate;
	}


}
