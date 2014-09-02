package com.hashedin.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * This exists to 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PersonData {

	private long id;
	private String firstName;
	private String lastName;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	
	
	
}
