package com.hashedin.artcollective.service;

import java.util.ArrayList;
import java.util.List;

import com.hashedin.artcollective.entity.ArtWork;
import com.hashedin.artcollective.entity.OrderLineItem;

public class PortfolioLineItem {
	
	private String title;
	public String getTitle() {
		return "success";
	}
	public void setTitle(String title) {
		this.title = title;
	}
	private ArtWork artwork;
	private List<OrderLineItem> orderLineItems;
	
	public PortfolioLineItem(ArtWork artwork,
			OrderLineItem orderLineItemForArtist) {
		this.title = "success";
		this.artwork = artwork;
		this.orderLineItems = new ArrayList<>();
		this.orderLineItems.add(orderLineItemForArtist);
	}
	public ArtWork getArtwork() {
		return artwork;
	}
	public void setArtwork(ArtWork artwork) {
		this.artwork = artwork;
	}
	public List<OrderLineItem> getOrderLineItems() {
		return orderLineItems;
	}
	@Override
	public String toString() {
		return "PortfolioLineItem [title=" + title + ", artwork=" + artwork
				+ ", orderLineItems=" + orderLineItems + "]";
	}
	public void setOrderLineItems(List<OrderLineItem> orderLineItems) {
		this.orderLineItems = orderLineItems;
	}
	public void addOrderLineItem(OrderLineItem orderLineItem) {
		this.orderLineItems.add(orderLineItem);
	}
}
