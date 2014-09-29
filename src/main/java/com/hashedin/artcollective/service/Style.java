package com.hashedin.artcollective.service;

import com.hashedin.artcollective.entity.ArtStyle;

public class Style {
	
	private Long id;
	private String title;
	public Style(ArtStyle style) {
		this.id = style.getId();
		this.title = style.getTitle();
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
