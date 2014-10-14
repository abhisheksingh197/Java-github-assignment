package com.hashedin.artcollective.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ArtCollection {
	@Id
	private Long id;
	private String title;
	
	public ArtCollection() {
	}
	public ArtCollection(Long id, String title) {
		this.id = id;
		this.title = title;
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
