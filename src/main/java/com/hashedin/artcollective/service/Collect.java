package com.hashedin.artcollective.service;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonSetter;

public class Collect {
	
	private static final DateTimeFormatter DATE_FORMAT = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ssZ");
	
	private long collectionId;
	private DateTime createdAt;
	private boolean featured;
	private long id;
	private long position;
	private long productId;
	private DateTime updatedAt;
	private long sortValue;
	
	
	public long getCollectionId() {
		return collectionId;
	}
	@JsonSetter("collection_id")
	public void setCollectionId(long collectionId) {
		this.collectionId = collectionId;
	}
	public DateTime getCreatedAt() {
		return createdAt;
	}
	@JsonSetter("created_at")
	public void setCreatedAt(String createdAt) {
		this.createdAt = DATE_FORMAT.parseDateTime(createdAt);
	}
	public boolean isFeatured() {
		return featured;
	}
	public void setFeatured(boolean featured) {
		this.featured = featured;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getPosition() {
		return position;
	}
	public void setPosition(long position) {
		this.position = position;
	}
	public long getProductId() {
		return productId;
	}
	@JsonSetter("product_id")
	public void setProductId(long productId) {
		this.productId = productId;
	}
	public DateTime getUpdatedAt() {
		return updatedAt;
	}
	@JsonSetter("updated_at")
	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = DATE_FORMAT.parseDateTime(updatedAt);
	}

	public long getSortValue() {
		return sortValue;
	}
	@JsonSetter("sort_value")
	public void setSortValue(long sortValue) {
		this.sortValue = sortValue;
	}
	public static DateTimeFormatter getDateFormat() {
		return DATE_FORMAT;
	}
	
}