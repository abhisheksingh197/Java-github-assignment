package com.hashedin.artcollective.service;

import java.util.ArrayList;
import java.util.List;

import com.hashedin.artcollective.entity.PriceBucket;
import com.hashedin.artcollective.entity.SizeBucket;

public class PriceAndSizeBucket {
	private List<PriceBucket> priceBuckets = new ArrayList<>();
	private List<SizeBucket> sizeBuckets = new ArrayList<>();
	public PriceAndSizeBucket(List<PriceBucket> priceBuckets,
			List<SizeBucket> sizeBuckets) {
		this.priceBuckets = priceBuckets;
		this.sizeBuckets = sizeBuckets;
	}
	public List<PriceBucket> getPriceBuckets() {
		return priceBuckets;
	}
	public void setPriceBuckets(List<PriceBucket> priceBuckets) {
		this.priceBuckets = priceBuckets;
	}
	public List<SizeBucket> getSizeBuckets() {
		return sizeBuckets;
	}
	public void setSizeBuckets(List<SizeBucket> sizeBuckets) {
		this.sizeBuckets = sizeBuckets;
	}
}
