package com.hashedin.artcollective.entity;

import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class ArtWork {
	
	@Id
	private Long id;
	private Long skuId;
	
	@ManyToMany(cascade = CascadeType.ALL)
	private List<ArtStyle> styles;
	
	@ManyToMany(cascade = CascadeType.ALL)
	private List<ArtSubject> subjects;
	
	@ManyToMany(cascade = CascadeType.ALL)
	private List<ArtCollection> collections;
	
	@OneToMany(cascade = CascadeType.ALL)
	private List<Image> images;
	
	@ManyToOne(cascade = CascadeType.ALL)
	private Artist artist;
	private String title;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	private String medium;
	private String orientation;
	private Boolean isCanvasAvailable;
	private Boolean isOriginalAvailable;
	private Boolean isLimitedEdition;
	private Boolean isFrameAvailable;
	private Boolean isCertified;
	
	public ArtWork() {
		this.isCanvasAvailable = false;
		this.isOriginalAvailable = false;
		this.isLimitedEdition = false;
		this.isFrameAvailable = false;
		this.isCertified = false;
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

	public List<ArtStyle> getStyle() {
		return Collections.unmodifiableList(styles);
	}
	public void setStyle(List<ArtStyle> style) {
		this.styles = style;
	}
	public List<ArtSubject> getSubject() {
		return Collections.unmodifiableList(subjects);
	}
	public void setSubject(List<ArtSubject> subject) {
		this.subjects = subject;
	}
	public List<ArtCollection> getCollection() {
		return Collections.unmodifiableList(collections);
	}
	public void setCollection(List<ArtCollection> collection) {
		this.collections = collection;
	}
	public Artist getArtist() {
		return artist;
	}
	public void setArtist(Artist artist) {
		this.artist = artist;
	}
	
	public List<Image> getImages() {
		return images;
	}
	public void setImages(List<Image> images) {
		this.images = images;
	}

	public String getMedium() {
		return medium;
	}
	public void setMedium(String medium) {
		this.medium = medium;
	}
	public String getOrientation() {
		return orientation;
	}
	public void setOrientation(String orientation) {
		this.orientation = orientation;
	}
	public boolean isCanvasAvailable() {
		return isCanvasAvailable;
	}
	public void setIsCanvasAvailable(boolean isCanvasAvailable) {
		this.isCanvasAvailable = isCanvasAvailable;
	}
	public boolean isOriginalAvailable() {
		return isOriginalAvailable;
	}
	public void setIsOriginalAvailable(boolean isOriginalAvailable) {
		this.isOriginalAvailable = isOriginalAvailable;
	}
	public boolean isLimitedEdition() {
		return isLimitedEdition;
	}
	public void setIsLimitedEdition(boolean isLimitedEdition) {
		this.isLimitedEdition = isLimitedEdition;
	}
	public boolean isFrameAvailable() {
		return isFrameAvailable;
	}
	public void setIsFrameAvailable(boolean isFrameAvailable) {
		this.isFrameAvailable = isFrameAvailable;
	}
	public boolean isCertified() {
		return isCertified;
	}
	public void setIsCertified(boolean isCertified) {
		this.isCertified = isCertified;
	}

}