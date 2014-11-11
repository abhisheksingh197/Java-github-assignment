package com.hashedin.artcollective.service;


import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import com.hashedin.artcollective.entity.Image;

@Service
public interface ShopifyService {

	public List<Product> getArtWorkProductsSinceLastModified(DateTime lastModified);
	
	public List<Collection> getCollectionsForProduct(long productId);
	
	public List<MetaField> getMetaFieldsForProduct(long productId);

	public List<Product> getFrameProductsSinceLastModified(DateTime lastRunTime);

	public void postImageColorsMetaField(Long id, String imageColors);

	public Image uploadImage(Product product, InputStream image, String format) throws IOException;
	
	public List<Order> getOrderSinceLastModified(DateTime lastModified);

	public List<MetaField> getMetafieldsForVariant(Long variantId);
	
}