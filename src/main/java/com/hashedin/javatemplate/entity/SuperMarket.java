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
				+ ", webAddress=" + webAddress + ", noOfProducts=" + noOfProducts + ", noOfOutlets=" + noOfOutlets +    "]";
	}
	public SuperMarket() {
		
	}
	public SuperMarket(String name, String city, String webAddress, int noOfProducts, int noOfOutlets) {
		this.name = name;
		this.city = city;
		this.webAddress = webAddress;
		this.noOfProducts = 999;
		this.noOfOutlets = 99; 
	}
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	private String city;
	private String webAddress;
	private int noOfProducts;
	private int noOfOutlets;
	public String getName(){ return name; }
	public int getNoOfOutlets() { return noOfOutlets;}
	public int getNoOfProducts() { return noOfProducts;}
	

}
