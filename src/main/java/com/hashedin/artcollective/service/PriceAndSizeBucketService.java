package com.hashedin.artcollective.service;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hashedin.artcollective.entity.PriceBucket;
import com.hashedin.artcollective.entity.SizeBucket;
import com.hashedin.artcollective.repository.PriceBucketRepository;
import com.hashedin.artcollective.repository.SizeBucketRepository;

@Service
public class PriceAndSizeBucketService {
	
	@Autowired
	private PriceBucketRepository priceBucketRepository;
	
	@Autowired
	private SizeBucketRepository sizeBucketRepository;
	
	
	public void addPriceBucket(PriceBucket priceBucket) {
		priceBucketRepository.save(priceBucket);
	}
	
	public void addSizeBucket(SizeBucket sizeBucket) {
		sizeBucketRepository.save(sizeBucket);
	}

	
	public PriceAndSizeBucket getPriceAndSizeBuckets(Product p) {
		List<PriceBucket> priceBucketsForProduct = new ArrayList<>();
		List<SizeBucket> sizeBucketsForProduct = new ArrayList<>();
 		List<PriceBucket> priceBucketsFromRepo = (List<PriceBucket>) priceBucketRepository.findAll();
		List<SizeBucket> sizeBucketsFromRepo = (List<SizeBucket>) sizeBucketRepository.findAll();
		List<Variant> variants = p.getVariants();
		HashSet<SizeBucket> sizeBucketHashSet = new HashSet<>();
		HashSet<PriceBucket> priceBucketHashSet = new HashSet<>();
		for (Variant variant : variants) {
			SizeBucket sizeBucket = findSizeBucketForVariant(variant, sizeBucketsFromRepo);
			if (sizeBucket != null) {
				sizeBucketHashSet.add(sizeBucket);
			}
			PriceBucket priceBucket = findPriceBucketForVariant(variant, priceBucketsFromRepo);
			if (priceBucket != null) {
				priceBucketHashSet.add(priceBucket);
			}
		}
		sizeBucketsForProduct.addAll(sizeBucketHashSet);
		priceBucketsForProduct.addAll(priceBucketHashSet);
		
		return new PriceAndSizeBucket(priceBucketsForProduct, sizeBucketsForProduct);
	}
	
	
	// Iterate through Price Bucket objects to see if the variant belongs to any and return the same.
	private SizeBucket findSizeBucketForVariant(Variant variant, 
			List<SizeBucket> sizeBucketsFromRepo) {
		for (SizeBucket sizeBucketIteratorObject : sizeBucketsFromRepo) {
			if (doesVariantBelongToSizeBucket(variant, sizeBucketIteratorObject)) {
				return sizeBucketIteratorObject;
			}
		}
		return null;
	}

	// Iterate through Price Bucket objects to see if the variant belongs to any and return the same.
	private PriceBucket findPriceBucketForVariant(Variant variant, 
			List<PriceBucket> priceBucketsFromRepo) {
		for (PriceBucket priceBucketIteratorObject : priceBucketsFromRepo) {
			if (doesVariantBelongToPriceBucket(variant, priceBucketIteratorObject)) {
				return priceBucketIteratorObject;
			}
		}
		return null;
	}
	
	// Checks whether a variant belongs to particular Price Bucket or not.
	private boolean doesVariantBelongToPriceBucket(Variant variant,
			PriceBucket priceBucketIteratorObject) {
		Double price = variant.getPrice(), lower = priceBucketIteratorObject.getLowerRange(), 
				upper = priceBucketIteratorObject.getUpperRange();
		return lower + 1 <= price && (upper == null ? true : price <= upper);
	}
	
	// Checks whether a variant belongs to particular Size Bucket or not.
	private boolean doesVariantBelongToSizeBucket(Variant variant,
			SizeBucket sizeBucketIteratorObject) {
		
		Double size = getVariantArea(variant), lower = sizeBucketIteratorObject.getLowerValue(),  
				upper = sizeBucketIteratorObject.getUpperValue();
		return lower + 1 <= size && (upper == null ? true : size <= upper);
	}
	
	// Returns the Area(Length * Breadth) of a variant
	private double getVariantArea(Variant variant) {
		double variantLength = Double.parseDouble((
				variant.getOption1().split("[Xx]")[0].split("\"")[0]));
		double variantBreadth = Double.parseDouble((
				variant.getOption1().split("[Xx]")[1].split("\"")[0]));
		
		return variantLength * variantBreadth;
	}

}
