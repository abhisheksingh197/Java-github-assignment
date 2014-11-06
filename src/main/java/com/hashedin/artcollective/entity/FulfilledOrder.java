package com.hashedin.artcollective.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

@Entity
public class FulfilledOrder {
	
	@Id
	private Long id;
	private Boolean buyerAcceptsMarketing;
	@Column(name = "created_at")
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime createdAt;
	@Column(name = "updated_at")
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime updatedAt;
	@Column(name = "processed_at")
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime processedAt;
	@Column(name = "cancelled_at")
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime cancelledAt;
	private String cartToken;
	private String checkoutToken;
	private Boolean confirmed;
	private String currency;
	private String email;
	private String financialStatus;
	private String fulfillmentStatus;
	private String gateway;
	private Long locationId;
	private String name;
	private Long number;
	private Double subTotalPrice;
	private Boolean taxesIncluded;
	private String token;
	private Double totalDiscounts;
	private Double totalLineItemsPrice;
	private Double totalPrice;
	private Double totalPriceUSD;
	private Double totalTax;
	private Double totalWeight;
	private Long userId;
	private String browserIp;
	private Long orderNo;
	private Long checkoutId;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Boolean getBuyerAcceptsMarketing() {
		return buyerAcceptsMarketing;
	}
	public void setBuyerAcceptsMarketing(Boolean buyerAcceptsMarketing) {
		this.buyerAcceptsMarketing = buyerAcceptsMarketing;
	}
	public DateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(DateTime createdAt) {
		this.createdAt = createdAt;
	}
	public DateTime getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(DateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
	public DateTime getProcessedAt() {
		return processedAt;
	}
	public void setProcessedAt(DateTime processedAt) {
		this.processedAt = processedAt;
	}
	public DateTime getCancelledAt() {
		return cancelledAt;
	}
	public void setCancelledAt(DateTime cancelledAt) {
		this.cancelledAt = cancelledAt;
	}
	public String getCartToken() {
		return cartToken;
	}
	public void setCartToken(String cartToken) {
		this.cartToken = cartToken;
	}
	public String getCheckoutToken() {
		return checkoutToken;
	}
	public void setCheckoutToken(String checkoutToken) {
		this.checkoutToken = checkoutToken;
	}
	public Boolean getConfirmed() {
		return confirmed;
	}
	public void setConfirmed(Boolean confirmed) {
		this.confirmed = confirmed;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFinancialStatus() {
		return financialStatus;
	}
	public void setFinancialStatus(String financialStatus) {
		this.financialStatus = financialStatus;
	}
	public String getFulfillmentStatus() {
		return fulfillmentStatus;
	}
	public void setFulfillmentStatus(String fulfillmentStatus) {
		this.fulfillmentStatus = fulfillmentStatus;
	}
	public String getGateway() {
		return gateway;
	}
	public void setGateway(String gateway) {
		this.gateway = gateway;
	}
	public Long getLocationId() {
		return locationId;
	}
	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getNumber() {
		return number;
	}
	public void setNumber(Long number) {
		this.number = number;
	}
	public Double getSubTotalPrice() {
		return subTotalPrice;
	}
	public void setSubTotalPrice(Double subTotalPrice) {
		this.subTotalPrice = subTotalPrice;
	}
	public Boolean getTaxesIncluded() {
		return taxesIncluded;
	}
	public void setTaxesIncluded(Boolean taxesIncluded) {
		this.taxesIncluded = taxesIncluded;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Double getTotalDiscounts() {
		return totalDiscounts;
	}
	public void setTotalDiscounts(Double totalDiscounts) {
		this.totalDiscounts = totalDiscounts;
	}
	public Double getTotalLineItemsPrice() {
		return totalLineItemsPrice;
	}
	public void setTotalLineItemsPrice(Double totalLineItemsPrice) {
		this.totalLineItemsPrice = totalLineItemsPrice;
	}
	public Double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public Double getTotalPriceUSD() {
		return totalPriceUSD;
	}
	public void setTotalPriceUSD(Double totalPriceUSD) {
		this.totalPriceUSD = totalPriceUSD;
	}
	public Double getTotalTax() {
		return totalTax;
	}
	public void setTotalTax(Double totalTax) {
		this.totalTax = totalTax;
	}
	public Double getTotalWeight() {
		return totalWeight;
	}
	public void setTotalWeight(Double totalWeight) {
		this.totalWeight = totalWeight;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getBrowserIp() {
		return browserIp;
	}
	public void setBrowserIp(String browserIp) {
		this.browserIp = browserIp;
	}
	public Long getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(Long orderNo) {
		this.orderNo = orderNo;
	}
	public Long getCheckoutId() {
		return checkoutId;
	}
	public void setCheckoutId(Long checkoutId) {
		this.checkoutId = checkoutId;
	}

}
