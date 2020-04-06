package com.distar.dtwr.system.service;

import com.distar.dtwr.company.domain.User;

public class ComposeForm {
	private User user;
	private Long userId;
	private String to;
	private String subject;
	private String message;
	private String urlBack;

	public ComposeForm() {
	}

	public ComposeForm(User user) {
		this.user = user;
		userId = user.getId();
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
		userId = user.getId();
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getUrlBack() {
		return urlBack;
	}

	public void setUrlBack(String urlBack) {
		this.urlBack = urlBack;
	}

}
