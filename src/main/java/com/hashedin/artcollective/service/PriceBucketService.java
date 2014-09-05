package com.hashedin.artcollective.service;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hashedin.artcollective.entity.PriceBucket;
import com.hashedin.artcollective.repository.PriceBucketRepository;

@Service
public class PriceBucketService {
	
	@Autowired
	private PriceBucketRepository priceBucketRepository;
	
	
	public void addPriceBucket(PriceBucket priceBucket) {
		priceBucketRepository.save(priceBucket);
	}
	
	public List<PriceBucket> getPriceBuckets(Product p) {
		List<PriceBucket> priceBucketsForProduct = new ArrayList<>();
		List<PriceBucket> priceBucketsFromRepo = (List<PriceBucket>) priceBucketRepository.findAll();
		List<Variant> variants = p.getVariants();
		HashSet<PriceBucket> hashSet = new HashSet<>();
		for (Variant variant : variants) {
			PriceBucket priceBucket = findPriceBucketForVariant(variant, priceBucketsFromRepo);
			if (priceBucket != null) {
				hashSet.add(priceBucket);
			}
		}
		priceBucketsForProduct.addAll(hashSet);
		return priceBucketsForProduct;
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
		double price = variant.getPrice(), lower = priceBucketIteratorObject.getLowerRange(), 
				upper = priceBucketIteratorObject.getUpperRange();
		return lower + 1 <= price && price <= upper;
	}

}
