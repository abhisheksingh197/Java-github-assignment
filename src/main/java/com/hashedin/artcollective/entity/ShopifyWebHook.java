package com.hashedin.artcollective.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

@Entity
public class ShopifyWebHook {
	
	public ShopifyWebHook(DateTime createdAt, String requestUniqueId,
			String webHookType, String webHookEvent) {
		super();
		this.createdAt = createdAt;
		this.requestUniqueId = requestUniqueId;
		this.webHookType = webHookType;
		this.webHookEvent = webHookEvent;
	}
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@Column(name = "created_at")
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime createdAt;
	private String requestUniqueId;
	private String webHookType;
	private String webHookEvent;
	public DateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(DateTime createdAt) {
		this.createdAt = createdAt;
	}
	public String getRequestUniqueId() {
		return requestUniqueId;
	}
	public void setRequestUniqueId(String requestUniqueId) {
		this.requestUniqueId = requestUniqueId;
	}
	public String getWebHookType() {
		return webHookType;
	}
	public void setWebHookType(String webHookType) {
		this.webHookType = webHookType;
	}
	public String getWebHookEvent() {
		return webHookEvent;
	}
	public void setWebHookEvent(String webHookEvent) {
		this.webHookEvent = webHookEvent;
	}
}
