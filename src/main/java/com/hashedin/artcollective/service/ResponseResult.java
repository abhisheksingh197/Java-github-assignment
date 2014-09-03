package com.hashedin.artcollective.service;


public class ResponseResult {
	private String score;
	private String filepath;
	private TinEyeMetadata metadata;
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getFilepath() {
		return filepath;
	}
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	public TinEyeMetadata getMetadata() {
		return metadata;
	}
	public void setMetadata(TinEyeMetadata metadata) {
		this.metadata = metadata;
	}
	
}
