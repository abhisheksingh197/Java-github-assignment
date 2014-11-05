package com.hashedin.artcollective.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

@Entity
public class SynchronizeLog {
	
	public SynchronizeLog(DateTime synchronizeStartedAt,
			DateTime synchronizeCompletedAt, String synchronizeType,
			String synchronizeStatus, Long synchronizedItems) {
		super();
		this.synchronizeStartedAt = synchronizeStartedAt;
		this.synchronizeCompletedAt = synchronizeCompletedAt;
		this.synchronizeType = synchronizeType;
		this.synchronizeStatus = synchronizeStatus;
		this.synchronizedItems = synchronizedItems;
	}
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "synchronize_started_at")
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime synchronizeStartedAt;
	
	@Column(name = "synchronize_completed_at")
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime synchronizeCompletedAt;
	
	@Column(name = "synchronize_type")
	private String synchronizeType;
	private String synchronizeStatus;
	private Long synchronizedItems;
	public DateTime getSynchronizedStartedAt() {
		return synchronizeStartedAt;
	}
	public void setSynchronizedStartedAt(DateTime synchronizedStartedAt) {
		this.synchronizeStartedAt = synchronizedStartedAt;
	}
	public DateTime getSynchronizedCompletedAt() {
		return synchronizeCompletedAt;
	}
	public void setSynchronizedCompletedAt(DateTime synchronizedCompletedAt) {
		this.synchronizeCompletedAt = synchronizedCompletedAt;
	}
	public String getSynchronizeType() {
		return synchronizeType;
	}
	public void setSynchronizeType(String synchronizeType) {
		this.synchronizeType = synchronizeType;
	}
	public String getSynchronizeStatus() {
		return synchronizeStatus;
	}
	public void setSynchronizeStatus(String synchronizeStatus) {
		this.synchronizeStatus = synchronizeStatus;
	}
	public Long getSynchronizedItems() {
		return synchronizedItems;
	}
	public void setSynchronizedItems(Long synchronizedItems) {
		this.synchronizedItems = synchronizedItems;
	}
	public Long getId() {
		return id;
	}
	
	
}
