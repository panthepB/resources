package com.distar.dtwr.company.domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.distar.dtwr.system.domain.Company;

@Entity
@Table(name = "comp_user")
public class User implements Serializable {
	private Long id;
	private String username;
	private String password;
	private Position position;
	private Company company;
	private EmpData empData;
	private String fileName;

	@Id
	@GeneratedValue
	@Column(name = "user_id")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@ManyToOne
	@JoinColumn(name = "position_fk")
	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	@ManyToOne
	@JoinColumn(name = "comp_fk")
	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "empdata_fk")
	public EmpData getEmpData() {
		return empData;
	}

	public void setEmpData(EmpData empData) {
		this.empData = empData;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Transient
	public String getName() {
		if (getPosition().getNameEng().equals(Position.ADMIN_NAME_ENG))
			return "ผู้ดูแลระบบ";

		StringBuffer name = new StringBuffer();
//		name.append(getEmpData().getFirstname()).append(" ");
//		name.append(getEmpData().getLastname());
		return name.toString();
	}

	@Transient
	public String getFilename() {
		if (getPosition().getNameEng().equals(Position.ADMIN_NAME_ENG)){
//			System.out.println(getFileName());;
			return "";
		}
		
//		System.out.println(getFileName());;
		return getFileName();
	}

	@Transient
	public String getCode() {
		return "";
	}

}
