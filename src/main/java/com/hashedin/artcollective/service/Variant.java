package com.hashedin.artcollective.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Variant implements Comparable<Variant> {

	private String barcode;
	private String compareAtPrice;
	private String createdAt;
	private String fulfillmentService;
	private int grams;
	private Long id;
	private String inventoryManagement;
	private String inventoryPolicy;
	private String option1;
	private String option2;
	private String option3;
	private int position;
	private double price;
	private String productId;
	private boolean requiresShipping;
	private String sku;
	private boolean taxable;
	private String title;
	private String updatedAt;
	private int inventoryQuantity;
	private int oldInventoryQuantity;
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
	public void setId(String id) {
		this.id = Long.parseLong(id);
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
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public boolean isRequiresShipping() {
		return requiresShipping;
	}
	public void setRequiresShipping(boolean requiresShipping) {
		this.requiresShipping = requiresShipping;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public boolean isTaxable() {
		return taxable;
	}
	public void setTaxable(boolean taxable) {
		this.taxable = taxable;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}
	public int getInventoryQuantity() {
		return inventoryQuantity;
	}
	public void setInventoryQuantity(int inventoryQuantity) {
		this.inventoryQuantity = inventoryQuantity;
	}
	public int getOldInventoryQuantity() {
		return oldInventoryQuantity;
	}
	public void setOldInventoryQuantity(int oldInventoryQuantity) {
		this.oldInventoryQuantity = oldInventoryQuantity;
	}
	
	@Override
	public int compareTo(Variant v) {
		return Double.valueOf(this.price - v.price).intValue();
    }
	
	//CHECKSTYLE:OFF
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Variant other = (Variant) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	//CHECKSTYLE:ON

	
	
}
