package com.hashedin.artcollective.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class FrameVariant {
	
	@Id
	private Long id;
	private Long skuId;
	private String handle;
	private Double mountThickness;
	private Double frameThickness;
	private Double frameLength;
	private Double frameBreadth;
	private Double unitPrice;
	private String frameTitle;
	
	public String getFrameTitle() {
		return frameTitle;
	}
	public void setFrameTitle(String frameTitle) {
		this.frameTitle = frameTitle;
	}
	public Double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}
	public String getImgSrc() {
		return imgSrc;
	}
	public void setImgSrc(String imgSrc) {
		this.imgSrc = imgSrc;
	}
	private String imgSrc;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getSkuId() {
		return skuId;
	}
	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}
	public String getHandle() {
		return handle;
	}
	public void setHandle(String handle) {
		this.handle = handle;
	}
	public Double getMountThickness() {
		return mountThickness;
	}
	public void setMountThickness(double mountThickness) {
		this.mountThickness = mountThickness;
	}
	public Double getFrameThickness() {
		return frameThickness;
	}
	public void setFrameThickness(double frameThickness) {
		this.frameThickness = frameThickness;
	}
	public Double getFrameLength() {
		return frameLength;
	}
	public void setFrameLength(double d) {
		this.frameLength = d;
	}
	public Double getFrameBreadth() {
		return frameBreadth;
	}
	public void setFrameBreadth(double frameBreadth) {
		this.frameBreadth = frameBreadth;
	}

	
	

}
