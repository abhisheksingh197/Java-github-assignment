package com.hashedin.artcollective.service;


import java.util.List;

import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

@Service
public interface ShopifyService {

	public List<Product> getArtWorkProductsSinceLastModified(DateTime lastModified);
	
	public List<Collection> getCollectionsForProduct(long productId);
	
	public List<MetaField> getMetaFieldsForProduct(long productId);

	public List<Product> getFrameProductsSinceLastModified(DateTime lastRunTime);

	public void postImageColorsMetaField(Long id, String imageColors);
}