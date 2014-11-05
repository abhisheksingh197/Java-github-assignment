package com.hashedin.artcollective.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class OrderLineItem {
	
	@Id
	private Long id;
	private Long orderId;
	private Long productId;
	private String fulfillmentService;
	private String fulfillmentStatus;
	private Boolean giftCard;
	private Double grams;
	private Double price;
	private Long quantity;
	private Boolean requiresShipping;
	private String sku;
	private Boolean taxable;
	private String title;
	private Long variantId;
	private String variantTitle;
	private String vendor;
	private String name;
	private String variantInventoryManagement;
	private Boolean productExits;
	private Long fulfillableQuantity;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public String getFulfillmentService() {
		return fulfillmentService;
	}
	public void setFulfillmentService(String fulfillmentService) {
		this.fulfillmentService = fulfillmentService;
	}
	public String getFulfillmentStatus() {
		return fulfillmentStatus;
	}
	public void setFulfillmentStatus(String fulfillmentStatus) {
		this.fulfillmentStatus = fulfillmentStatus;
	}
	public Boolean getGiftCard() {
		return giftCard;
	}
	public void setGiftCard(Boolean giftCard) {
		this.giftCard = giftCard;
	}
	public Double getGrams() {
		return grams;
	}
	public void setGrams(Double grams) {
		this.grams = grams;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Long getQuantity() {
		return quantity;
	}
	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}
	public Boolean getRequiresShipping() {
		return requiresShipping;
	}
	public void setRequiresShipping(Boolean requiresShipping) {
		this.requiresShipping = requiresShipping;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public Boolean getTaxable() {
		return taxable;
	}
	public void setTaxable(Boolean taxable) {
		this.taxable = taxable;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Long getVariantId() {
		return variantId;
	}
	public void setVariantId(Long variantId) {
		this.variantId = variantId;
	}
	public String getVariantTitle() {
		return variantTitle;
	}
	public void setVariantTitle(String variantTitle) {
		this.variantTitle = variantTitle;
	}
	public String getVendor() {
		return vendor;
	}
	public void setVendor(String vendor) {
		this.vendor = vendor;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getVariantInventoryManagement() {
		return variantInventoryManagement;
	}
	public void setVariantInventoryManagement(String variantInventoryManagement) {
		this.variantInventoryManagement = variantInventoryManagement;
	}
	public Boolean getProductExits() {
		return productExits;
	}
	public void setProductExits(Boolean productExits) {
		this.productExits = productExits;
	}
	public Long getFulfillableQuantity() {
		return fulfillableQuantity;
	}
	public void setFulfillableQuantity(Long fulfillableQuantity) {
		this.fulfillableQuantity = fulfillableQuantity;
	}
	
}
