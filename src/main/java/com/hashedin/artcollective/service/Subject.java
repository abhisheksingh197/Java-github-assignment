package com.hashedin.artcollective.service;

import com.hashedin.artcollective.entity.ArtSubject;

public class Subject {
	
	private Long id;
	private String title;
	public Subject(ArtSubject subj) {
		this.id = subj.getId();
		this.title = subj.getTitle(); 
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
