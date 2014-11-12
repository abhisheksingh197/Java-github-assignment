package com.hashedin.artcollective.entity;

import java.util.Collection;
import java.util.Collections;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
public class Artist implements UserDetails {

	private static final long serialVersionUID = 1L;

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
	private String email;
	private String contactNumber;
	private String username;
	private String password;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	@Override
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	@Override
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ARTIST");
		return Collections.singletonList(authority);
	}
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	@Override
	public boolean isEnabled() {
		return true;
	}
	
}
