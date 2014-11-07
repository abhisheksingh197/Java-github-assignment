package com.hashedin.artcollective.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class OrderLineItem {
	
	@Id
	private Long id;
	@ManyToOne(cascade = CascadeType.ALL)
	private FulfilledOrder order;
	private Long productId;
	private Long artistId;
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
	public FulfilledOrder getOrder() {
		return order;
	}
	public void setOrder(FulfilledOrder order) {
		this.order = order;
	}
	public Long getProductId() {
		return productId;
	}
	@JsonSetter("product_id")
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Long getArtistId() {
		return artistId;
	}
	public void setArtistId(Long artistId) {
		this.artistId = artistId;
	}
	public String getFulfillmentStatus() {
		return fulfillmentStatus;
	}
	@JsonSetter("fulfillment_status")
	public void setFulfillmentStatus(String fulfillmentStatus) {
		this.fulfillmentStatus = fulfillmentStatus;
	}
	public String getFulfillmentService() {
		return fulfillmentService;
	}
	@JsonSetter("fulfillment_service")
	public void setFulfillmentService(String fulfillmentService) {
		this.fulfillmentService = fulfillmentService;
	}
	public Boolean getGiftCard() {
		return giftCard;
	}
	@JsonSetter("gift_card")
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
	@JsonSetter("requires_shipping")
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
	@JsonSetter("variant_id")
	public void setVariantId(Long variantId) {
		this.variantId = variantId;
	}
	public String getVariantTitle() {
		return variantTitle;
	}
	@JsonSetter("variant_title")
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
	@JsonSetter("variant_inventory_management")
	public void setVariantInventoryManagement(String variantInventoryManagement) {
		this.variantInventoryManagement = variantInventoryManagement;
	}
	public Boolean getProductExits() {
		return productExits;
	}
	@JsonSetter("product_exists")
	public void setProductExits(Boolean productExits) {
		this.productExits = productExits;
	}
	public Long getFulfillableQuantity() {
		return fulfillableQuantity;
	}
	@JsonSetter("fulfillable_quantity")
	public void setFulfillableQuantity(Long fulfillableQuantity) {
		this.fulfillableQuantity = fulfillableQuantity;
	}
	
}
