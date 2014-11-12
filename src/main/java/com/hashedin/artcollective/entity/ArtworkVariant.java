package com.hashedin.artcollective.entity;

import javax.persistence.Entity;
import javax.persistence.Id;



@Entity
public class ArtworkVariant {
	
	@Id
	private Long id;
	private String barcode;
	private String compareAtPrice;
	private String createdAt;
	private String fulfillmentService;
	private int grams;
	private String inventoryManagement;
	private String inventoryPolicy;
	private String option1;
	private String option2;
	private String option3;
	private int position;
	private Double price;
	private Long productId;
	private boolean requiresShipping;
	private Double earning;
	// commenting out since we do not use these fields currently, can revert them back whenever required
//	private String sku;
//	private boolean taxable;
//	private String title;
//	private String updatedAt;
//	private int inventoryQuantity;
//	private int oldInventoryQuantity;
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public String getCompareAtPrice() {
		return compareAtPrice;
	}
	public void setCompareAtPrice(String compareAtPrice) {
		this.compareAtPrice = compareAtPrice;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	public String getFulfillmentService() {
		return fulfillmentService;
	}
	public void setFulfillmentService(String fulfillmentService) {
		this.fulfillmentService = fulfillmentService;
	}
	public int getGrams() {
		return grams;
	}
	public void setGrams(int grams) {
		this.grams = grams;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getInventoryManagement() {
		return inventoryManagement;
	}
	public void setInventoryManagement(String inventoryManagement) {
		this.inventoryManagement = inventoryManagement;
	}
	public String getInventoryPolicy() {
		return inventoryPolicy;
	}
	public void setInventoryPolicy(String inventoryPolicy) {
		this.inventoryPolicy = inventoryPolicy;
	}
	public String getOption1() {
		return option1;
	}
	public void setOption1(String option1) {
		this.option1 = option1;
	}
	public String getOption2() {
		return option2;
	}
	public void setOption2(String option2) {
		this.option2 = option2;
	}
	public String getOption3() {
		return option3;
	}
	public void setOption3(String option3) {
		this.option3 = option3;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public boolean isRequiresShipping() {
		return requiresShipping;
	}
	public void setRequiresShipping(boolean requiresShipping) {
		this.requiresShipping = requiresShipping;
	}
	public Double getEarning() {
		return earning;
	}
	public void setEarning(Double earnings) {
		this.earning = earnings;
	}

}
