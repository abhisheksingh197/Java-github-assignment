package com.hashedin.artcollective.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class FrameVariant {
	
	@Id
	private Long id;
	private Long skuId;
	private String handle;
	private Long mountThickness;
	private Long frameThickness;
	private Long frameLength;
	private Long frameBreadth;
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
	public Long getMountThickness() {
		return mountThickness;
	}
	public void setMountThickness(Long mountThickness) {
		this.mountThickness = mountThickness;
	}
	public Long getFrameThickness() {
		return frameThickness;
	}
	public void setFrameThickness(Long frameThickness) {
		this.frameThickness = frameThickness;
	}
	public Long getFrameLength() {
		return frameLength;
	}
	public void setFrameLength(Long frameLength) {
		this.frameLength = frameLength;
	}
	public Long getFrameBreadth() {
		return frameBreadth;
	}
	public void setFrameBreadth(Long frameBreadth) {
		this.frameBreadth = frameBreadth;
	}
	
	

}
