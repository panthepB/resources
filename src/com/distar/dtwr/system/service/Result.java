package com.distar.dtwr.system.service;

public class Result {
	private String resultTxt;
	private String url;
	private boolean error;

	public Result(boolean isError) {
		this.error = isError;
	}

	public String getResultTxt() {
		return resultTxt;
	}

	public void setResultTxt(String resultTxt) {
		this.resultTxt = resultTxt;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean isError() {
		return this.error;
	}

}
