package com.hashedin.controller;

import java.io.Serializable;

public class Product implements Serializable {

	private static final long serialVersionUID = 7120075470411170832L;

	private String id;
	private String title;
	private String price;
	private String description;
	
	public Product(String id, String title, String price, String description) {
		super();
		this.id = id;
		this.title = title;
		this.price = price;
		this.description = description;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
