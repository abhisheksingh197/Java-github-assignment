package com.hashedin.artcollective.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;



@Entity
public class ArtSubject implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	private Long id;
	private String title;
	
	public ArtSubject() {
	}

	public ArtSubject(Long id, String title) {
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
