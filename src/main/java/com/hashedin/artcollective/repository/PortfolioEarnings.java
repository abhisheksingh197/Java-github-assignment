package com.hashedin.artcollective.repository;

import org.joda.time.DateTime;

public class PortfolioEarnings {
	
	private DateTime orderDate;
	private Long orderId;
	private String orderName;
	private String productImageSrc;
	private String variantSize;
	private Long quantity;
	private double commission;
	public DateTime getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(DateTime orderDate) {
		this.orderDate = orderDate;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public String getOrderName() {
		return orderName;
	}
	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}
	public String getProductImageSrc() {
		return productImageSrc;
	}
	public void setProductImageSrc(String producImageSrc) {
		this.productImageSrc = producImageSrc;
	}
	public String getVariantSize() {
		return variantSize;
	}
	public void setVariantSize(String variantSize) {
		this.variantSize = variantSize;
	}
	public Long getQuantity() {
		return quantity;
	}
	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}
	public double getCommission() {
		return commission;
	}
	public void setCommission(double commission) {
		this.commission = commission;
	}
	

}
