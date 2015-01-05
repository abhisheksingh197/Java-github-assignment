package com.hashedin.javatemplate.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class SuperMarket {
	
	@Override
	public String toString() {
		return "SuperMarket [id=" + id + ", name=" + name + ", city=" + city
				+ ", webAddress=" + webAddress + "]";
	}
	public SuperMarket() {
		
	}
	public SuperMarket(String name, String city, String webAddress) {
		this.name = name;
		this.city = city;
		this.webAddress = webAddress;
	}
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	private String city;
	private String webAddress;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getWebAddress() {
		return webAddress;
	}
	public void setWebAddress(String webAddress) {
		this.webAddress = webAddress;
	}
}
