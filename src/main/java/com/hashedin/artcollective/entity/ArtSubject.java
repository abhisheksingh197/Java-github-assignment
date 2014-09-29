package com.hashedin.artcollective.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonProperty;


@Entity
public class ArtSubject {
	@Id
	private Long id;
	private String title;

	@edu.umd.cs.findbugs.annotations.SuppressWarnings(
		    value = "UUF_UNUSED_FIELD", 
		    justification = "Value not required for implementation")
	private Long wrapId;
	
	@JsonProperty("subjectId")
	public Long getWrapId() {
		return id;
	}
	
	@JsonProperty("subjectId")
	public void setWrapId(Long wrapId) {
		this.id = wrapId;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
}
