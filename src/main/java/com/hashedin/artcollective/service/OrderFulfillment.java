package com.hashedin.artcollective.service;

import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderFulfillment {
	
	private static final DateTimeFormatter DATE_FORMAT = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ssZ");
	
	private Long id;
	private Long orderId;
	private DateTime createdAt;
	private DateTime updatedAt;
	private String service;
	private String status;
	private String trackingCompany;
	private List<LineItem> orderLineItems;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getOrderId() {
		return orderId;
	}
	@JsonSetter("order_id")
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public DateTime getCreatedAt() {
		return createdAt;
	}
	@JsonSetter("created_at")
	public void setCreatedAt(String createdAt) {
		this.createdAt = DATE_FORMAT.parseDateTime(createdAt);
	}
	public DateTime getUpdatedAt() {
		return updatedAt;
	}
	@JsonSetter("updated_at")
	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = DATE_FORMAT.parseDateTime(updatedAt);
	}
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTrackingCompany() {
		return trackingCompany;
	}
	@JsonSetter("tracking_company")
	public void setTrackingCompany(String trackingCompany) {
		this.trackingCompany = trackingCompany;
	}
	public List<LineItem> getOrderLineItems() {
		return orderLineItems;
	}
	@JsonSetter("line_items")
	public void setOrderLineItems(List<LineItem> orderLineItems) {
		this.orderLineItems = orderLineItems;
	}
	
}
