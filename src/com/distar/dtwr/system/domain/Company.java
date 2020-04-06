package com.distar.dtwr.system.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.distar.dtwr.company.domain.Notice;

@Entity
@Table(name = "sys_company")
public class Company implements Serializable {
	private Long id;
	private String code;
	private String name;
	private String establish;
	private String address;
	private int moo;
	private String road;
	private String postcode;
	private String tel;
	private String fax;
	private String email;
	private String website;
	private int status;
	private String tumbol;
	private String amphur;
	private String text;
	private String paName;
	private List<Task> tasks;
	private List<Notice> notices;

	@Id
	@GeneratedValue
	@Column(name = "comp_id")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEstablish() {
		return establish;
	}

	public void setEstablish(String establish) {
		this.establish = establish;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getMoo() {
		return moo;
	}

	public void setMoo(int moo) {
		this.moo = moo;
	}

	public String getRoad() {
		return road;
	}

	public void setRoad(String road) {
		this.road = road;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getTumbol() {
		return tumbol;
	}

	public void setTumbol(String tumbol) {
		this.tumbol = tumbol;
	}

	public String getAmphur() {
		return amphur;
	}

	public void setAmphur(String amphur) {
		this.amphur = amphur;
	}

	@Column(name = "comp_txt", columnDefinition = "text")
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getPaName() {
		return paName;
	}

	public void setPaName(String paName) {
		this.paName = paName;
	}

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "sys_company_tasks", joinColumns = { @JoinColumn(name = "comp_id") }, inverseJoinColumns = { @JoinColumn(name = "task_id") })
	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	@OneToMany
	@JoinColumn(name = "comp_fk")
	public List<Notice> getNotices() {
		return notices;
	}

	public void setNotices(List<Notice> notices) {
		this.notices = notices;
	}

	@Transient
	public String getFullAddress() {
		StringBuffer sb = new StringBuffer();
		sb.append(address);
		if (road != null && road.length() > 0)
			sb.append(" ถ.").append(road);
		if (moo != 0)
			sb.append(" ม.").append(moo);
		sb.append(" ต.").append(tumbol);
		sb.append(" อ.").append(amphur);
		sb.append(" ").append(postcode);
		return sb.toString();
	}
}
