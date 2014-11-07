package com.hashedin.artcollective.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Artist {

	public Artist(String firstName, String lastName, String handle, Long collectionId) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.handle = handle;
		this.collectionId = collectionId;
	}
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private Long collectionId;
	private String firstName;
	private String lastName;
	private String handle;
	public long getId() {
		return id;
	}
	public String getHandle() {
		return handle;
	}
	public Long getCollectionId() {
		return collectionId;
	}

	public void setCollectionId(Long collectionId) {
		this.collectionId = collectionId;
	}
	public void setHandle(String handle) {
		this.handle = handle;
	}

	public Artist() {};
	
	public Artist(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	@Override
	public String toString() {
		return "Artist [id=" + id + ", firstName=" + firstName + ", lastName="
				+ lastName + "]";
	}

	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	
}
