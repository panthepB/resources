package com.distar.dtwr.company.service;

public class SearchEmpForm {
	private String firstname;
	private String lastname;
	private String nickname;
	private int sex;
	private Long position;
	private int status;
	private int onSubmit;

	public SearchEmpForm() {
		firstname = "";
		lastname = "";
		nickname = "";
		sex = 0;
		position = 0L;
		status = 1;
		onSubmit = 0;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public Long getPosition() {
		return position;
	}

	public void setPosition(Long position) {
		this.position = position;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getOnSubmit() {
		return onSubmit;
	}

	public void setOnSubmit(int onSubmit) {
		this.onSubmit = onSubmit;
	}

}
