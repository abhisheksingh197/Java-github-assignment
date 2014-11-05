package com.hashedin.artcollective.service;


import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Order {
	
	private static final DateTimeFormatter DATE_FORMAT = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ssZ");
	
	private Long id;
	private Boolean buyerAcceptsMarketing;
	private DateTime createdAt;
	private DateTime updatedAt;
	private DateTime processedAt;
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
	private List<OrderFulfillment> fulfillments;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Boolean getBuyerAcceptsMarketing() {
		return buyerAcceptsMarketing;
	}
	@JsonSetter("buyer_accepts_marketing")
	public void setBuyerAcceptsMarketing(Boolean buyerAcceptsMarketing) {
		this.buyerAcceptsMarketing = buyerAcceptsMarketing;
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
	public DateTime getProcessedAt() {
		return processedAt;
	}
	@JsonSetter("processed_at")
	public void setProcessedAt(String processedAt) {
		this.processedAt = DATE_FORMAT.parseDateTime(processedAt);
	}
	public DateTime getCancelledAt() {
		return cancelledAt;
	}
	@JsonSetter("cancelled_at")
	public void setCancelledAt(String cancelledAt) {
		if (cancelledAt != null) {
			this.cancelledAt = DATE_FORMAT.parseDateTime(cancelledAt);
		} 
		else {
			this.cancelledAt = null;
		}
	}
	public String getCartToken() {
		return cartToken;
	}
	@JsonSetter("cart_token")
	public void setCartToken(String cartToken) {
		this.cartToken = cartToken;
	}
	public String getCheckoutToken() {
		return checkoutToken;
	}
	@JsonSetter("checkout_token")
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
	@JsonSetter("financial_status")
	public void setFinancialStatus(String financialStatus) {
		this.financialStatus = financialStatus;
	}
	public String getFulfillmentStatus() {
		return fulfillmentStatus;
	}
	@JsonSetter("fulfillment_status")
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
	@JsonSetter("location_id")
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
	@JsonSetter("subtotal_price")
	public void setSubTotalPrice(Double subTotalPrice) {
		this.subTotalPrice = subTotalPrice;
	}
	public Boolean getTaxesIncluded() {
		return taxesIncluded;
	}
	@JsonSetter("taxes_included")
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
	@JsonSetter("total_discounts")
	public void setTotalDiscounts(Double totalDiscounts) {
		this.totalDiscounts = totalDiscounts;
	}
	public Double getTotalLineItemsPrice() {
		return totalLineItemsPrice;
	}
	@JsonSetter("total_line_items_price")
	public void setTotalLineItemsPrice(Double totalLineItemsPrice) {
		this.totalLineItemsPrice = totalLineItemsPrice;
	}
	public Double getTotalPrice() {
		return totalPrice;
	}
	@JsonSetter("total_price")
	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public Double getTotalPriceUSD() {
		return totalPriceUSD;
	}
	@JsonSetter("total_price_usd")
	public void setTotalPriceUSD(Double totalPriceUSD) {
		this.totalPriceUSD = totalPriceUSD;
	}
	public Double getTotalTax() {
		return totalTax;
	}
	@JsonSetter("total_tax")
	public void setTotalTax(Double totalTax) {
		this.totalTax = totalTax;
	}
	public Double getTotalWeight() {
		return totalWeight;
	}
	@JsonSetter("total_weight")
	public void setTotalWeight(Double totalWeight) {
		this.totalWeight = totalWeight;
	}
	public Long getUserId() {
		return userId;
	}
	@JsonSetter("user_id")
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	@JsonSetter("btowser_ip")
	public String getBrowserIp() {
		return browserIp;
	}
	public void setBrowserIp(String browserIp) {
		this.browserIp = browserIp;
	}
	public Long getOrderNo() {
		return orderNo;
	}
	@JsonSetter("order_number")
	public void setOrderNo(Long orderNo) {
		this.orderNo = orderNo;
	}
	public Long getCheckoutId() {
		return checkoutId;
	}
	@JsonSetter("checkout_id")
	public void setCheckoutId(Long checkoutId) {
		this.checkoutId = checkoutId;
	}
	public List<OrderFulfillment> getFulfillments() {
		return fulfillments;
	}
	public void setFulfillments(List<OrderFulfillment> fulfillments) {
		this.fulfillments = fulfillments;
	}

}
