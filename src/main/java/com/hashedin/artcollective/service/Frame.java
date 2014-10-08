package com.hashedin.artcollective.service;

import com.hashedin.artcollective.entity.FrameVariant;

public class Frame {
	public Frame(FrameVariant frameVariant) {
		super();
		this.id = frameVariant.getId();
		this.skuId = frameVariant.getSkuId();
		this.handle = frameVariant.getHandle();
		this.mountThickness = frameVariant.getMountThickness();
		this.frameThickness = frameVariant.getFrameThickness();
		this.frameLength = frameVariant.getFrameLength();
		this.frameBreadth = frameVariant.getFrameLength();
		this.unitPrice = frameVariant.getUnitPrice();
		this.frameTitle = frameVariant.getFrameTitle();
		this.imgSrc = frameVariant.getImgSrc();
	}
	private Long id;
	private Long skuId;
	private String handle;
	private Double mountThickness;
	private Double frameThickness;
	private Double frameLength;
	private Double frameBreadth;
	private Double unitPrice;
	private String imgSrc;
	public String getImgSrc() {
		return imgSrc;
	}
	public void setImgSrc(String imgSrc) {
		this.imgSrc = imgSrc;
	}
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
	public void setMountThickness(Double mountThickness) {
		this.mountThickness = mountThickness;
	}
	public Double getFrameThickness() {
		return frameThickness;
	}
	public void setFrameThickness(Double frameThickness) {
		this.frameThickness = frameThickness;
	}
	public Double getFrameLength() {
		return frameLength;
	}
	public void setFrameLength(Double frameLength) {
		this.frameLength = frameLength;
	}
	public Double getFrameBreadth() {
		return frameBreadth;
	}
	public void setFrameBreadth(Double frameBreadth) {
		this.frameBreadth = frameBreadth;
	}
	public Double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}
	public String getFrameTitle() {
		return frameTitle;
	}
	public void setFrameTitle(String frameTitle) {
		this.frameTitle = frameTitle;
	}
	private String frameTitle;
}
