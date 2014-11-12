package com.hashedin.artcollective.service;


import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import com.hashedin.artcollective.entity.Image;

@Service
public interface ShopifyService {

	public List<CustomCollection> getArtWorkProductsSinceLastModified(DateTime lastModified);
	
	public List<Collection> getCollectionsForProduct(long productId);
	
	public List<CustomCollection> getFrameProductsSinceLastModified(DateTime lastRunTime);

	public void postImageColorsMetaField(Long id, String imageColors);

	public Image uploadImage(CustomCollection product, InputStream image, String format) throws IOException;
	
	public List<Order> getOrderSinceLastModified(DateTime lastModified);

	public List<MetaField> getMetaFields(String string, Long collectionId);

	public CustomCollection getArtistCollection(Long artistCollectionId);
	
}