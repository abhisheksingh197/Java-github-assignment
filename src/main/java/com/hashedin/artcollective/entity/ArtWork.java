package com.hashedin.artcollective.entity;

import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;


@Entity
public class ArtWork {
	
	@Id
	private Long id;
	private Long skuId;
	private String handle;
	private String description;
	
	@ManyToMany(cascade = CascadeType.ALL)
	private List<ArtStyle> styles;
	
	@ManyToMany(cascade = CascadeType.ALL)
	private List<ArtSubject> subjects;
	
	@ManyToMany(cascade = CascadeType.ALL)
	private List<ArtCollection> collections;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<PriceBucket> priceBuckets;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<SizeBucket> sizeBuckets;
	
	// Adding fetch type eager so that we can access images of an artwork for the Test case
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Image> images;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<ArtworkVariant> variants;
	
	@ManyToOne(cascade = CascadeType.ALL)
	private Artist artist;
	private String title;
	private String medium;
	private String orientation;
	private Boolean isCanvasAvailable;
	private Boolean isOriginalAvailable;
	private Boolean isLimitedEdition;
	private Boolean isFrameAvailable;
	private Boolean isCertified;
	private String minSize;
	private String maxSize;
	@Column(name = "created_at")
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime createdAt;
	private double minPrice;
	private double maxPrice;
	private int variantCount;
	
	public ArtWork() {
		this.isCanvasAvailable = false;
		this.isOriginalAvailable = false;
		this.isLimitedEdition = false;
		this.isFrameAvailable = false;
		this.isCertified = false;
		this.medium = "";
		this.orientation = "";
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<ArtworkVariant> getVariants() {
		return variants;
	}
	public void setVariants(List<ArtworkVariant> variants) {
		this.variants = variants;
	}
	public List<SizeBucket> getSizeBuckets() {
		return sizeBuckets;
	}
	public void setSizeBuckets(List<SizeBucket> sizeBuckets) {
		this.sizeBuckets = sizeBuckets;
	}
	public List<PriceBucket> getPriceBuckets() {
		return priceBuckets;
	}
	public void setPriceBuckets(List<PriceBucket> priceBuckets) {
		this.priceBuckets = priceBuckets;
	}
	public String getHandle() {
		return handle;
	}
	public void setHandle(String handle) {
		this.handle = handle;
	}
	public DateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(DateTime createdAt) {
		this.createdAt = createdAt;
	}
	public String getMinSize() {
		return minSize;
	}
	public void setMinSize(String minSize) {
		this.minSize = minSize;
	}
	public String getMaxSize() {
		return maxSize;
	}
	public void setMaxSize(String maxSize) {
		this.maxSize = maxSize;
	}
	public double getMinPrice() {
		return minPrice;
	}
	public void setMinPrice(double minPrice) {
		this.minPrice = minPrice;
	}
	public double getMaxPrice() {
		return maxPrice;
	}
	public void setMaxPrice(double maxPrice) {
		this.maxPrice = maxPrice;
	}
	public int getVariantCount() {
		return variantCount;
	}
	public void setVariantCount(int variantCount) {
		this.variantCount = variantCount;
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
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
