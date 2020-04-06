package com.distar.dtwr.company.domain;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.distar.dtwr.system.domain.Company;

@Entity
@Table(name = "comp_notice")
public class Notice implements Serializable {
	private Long id;
	private String subject;
	private String desc;
	private Date createDate;
	private Time createTime;
	private Company company;
	private boolean createNew;

	@Id
	@GeneratedValue
	@Column(name = "notice_id")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	@Column(name = "description", columnDefinition = "text")
	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Time getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Time createTime) {
		this.createTime = createTime;
	}

	@ManyToOne
	@JoinColumn(name = "comp_fk")
	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	@Transient
	public boolean isCreateNew() {
		return createNew;
	}

	public void setCreateNew(boolean createNew) {
		this.createNew = createNew;
	}

	@Transient
	public String getDesc4Input() {
		return desc.replace("<br />", "\n");
	}

	@Transient
	public String getDesc4Save() {
		return desc.replace("\n", "<br />");
	}

	@Transient
	public String getReplay() {
		String tmp = desc.replace("<br />", " ");
		if (tmp.length() > 200)
			return tmp.substring(0, 200) + "...";
		return tmp;
	}

	@Transient
	public String getCreateDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("d MMM yyyy");
		return dateFormat.format(createDate) + " " + createTime;
	}

}
