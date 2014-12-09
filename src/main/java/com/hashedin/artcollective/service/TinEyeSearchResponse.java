package com.hashedin.artcollective.service;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonSetter;

class TinEyeSearchResponse {
	
	
	private String count;
	private String status;
	private String[] error;
	private String method;
	private List<TinEyeResponseResult> result;
	public String getCount() {
		return count;
	}
	
	@JsonSetter("count")
	public void setCount(String count) {
		this.count = count;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String[] getError() {
		return error;
	}
	public void setError(String[] error) {
		this.error = error;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public List<TinEyeResponseResult> getResult() {
		return result;
	}
	public void setResult(List<TinEyeResponseResult> result) {
		this.result = result;
	}
	
}
