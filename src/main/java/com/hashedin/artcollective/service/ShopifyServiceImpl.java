package com.hashedin.artcollective.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;


import com.hashedin.artcollective.entity.FrameVariant;
import com.hashedin.artcollective.entity.Image;
import com.hashedin.artcollective.utils.ProductSize;


@Service
public class ShopifyServiceImpl implements ShopifyService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ShopifyServiceImpl.class);
		
	private static final int MAX_PAGE_SIZE = 100;
	
	private final String baseUri;
	private final RestOperations rest;
	
	@Autowired
	public ShopifyServiceImpl(RestOperations rest, 
								@Value("${shopify.baseurl}") String baseUrl) {
		this.rest = rest;
		this.baseUri = baseUrl;
	}

	@Override
	public List<CustomCollection> getArtWorkProductsSinceLastModified(DateTime lastModified) {
		String queryString = "?product_type=artworks";
		if (lastModified != null) {
			queryString = "?product_type=artworks&updated_at_min=".concat(lastModified.toString());
		}
		int count = rest.getForObject(baseUri + "products/count.json" + queryString,
				ShopifyProductsCount.class).getCount();
		List<CustomCollection> productsList = new ArrayList<>();
		
		int numPages = (count / MAX_PAGE_SIZE) + 1;
		for (int page = 1; page <= numPages; page++) {
			ShopifyProducts products = rest.getForObject(
					String.format("%s%s%s&limit=%d&page=%d", baseUri, 
							"products.json", queryString, MAX_PAGE_SIZE, page), 
					ShopifyProducts.class);
			productsList.addAll(products.getProducts());
		}
		
		return productsList;
	}

	@Override
	public List<Collection> getCollectionsForProduct(long productId) {
		ArtWorkCollections collections = rest.getForObject(
				baseUri + "custom_collections.json?product_id=" + productId, ArtWorkCollections.class);
		return collections.getCustomCollections();
	}
	
	@Override
	public List<CustomCollection> getAddOnProductsSinceLastModified(DateTime lastRunTime, String productType) {
		ShopifyProducts products = rest.getForObject(
				baseUri + "products.json?product_type=".concat(productType), ShopifyProducts.class);
		return products.getProducts();
	}
	
	private Map<String, Map<String, String>> getImageExtractPostParameters(String imageColors) {
		Map<String, Map<String, String>> params = new HashMap<String, Map<String, String>>();
		Map<String, String> colorExtract = new HashMap<String, String>();
		colorExtract.put("namespace", "c_f");
		colorExtract.put("key", "color_extract");
		colorExtract.put("value", imageColors);
		colorExtract.put("value_type", "string");
		params.put("metafield", colorExtract);	
		return params;
	}

	@Override
	public void postImageColorsMetaField(Long productId, String imageColors) {
		Map<String, Map<String, String>> params = getImageExtractPostParameters(imageColors);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<?> entity = new HttpEntity<Object>(params, headers);
		ResponseEntity<String> postResponse = rest.exchange(
				baseUri + "products/" + productId + "/metafields.json",
				HttpMethod.POST, entity, String.class);	
		LOGGER.debug(postResponse.getBody());
		
	}

	@Override
	public Image uploadImage(CustomCollection p, InputStream image, String imageName) throws IOException {

		String imageUploadUrl = String.format("%sproducts/%s/images.json", baseUri, p.getId());
		
		StringBuilder imageData = new StringBuilder();
		imageData.append("{\"image\": {")
			.append("\"attachment\": \"")
			.append(new String(Base64.encode(
						IOUtils.toByteArray(image)), Charset.forName("UTF-8")))
			.append("\"").append(",")
			.append("\"filename\": \"").append(imageName).append("\"")
		.append("}}");
		
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(imageData.toString(), headers);
		
		ShopifyImageUploadResponse uploadResponse = rest.postForObject(imageUploadUrl, entity, 
				ShopifyImageUploadResponse.class);
		Image img = uploadResponse.getImage();
		LOGGER.info("Uploaded compressed image for product {} to shopify", p.getTitle());
		LOGGER.debug(uploadResponse.toString());
		return img;

	}

	@Override
	public List<Order> getOrderSinceLastModified(DateTime lastModified) {
			String queryString = "?fulfillment_status=shipped";
		
		if (lastModified != null) {
			queryString = "?fulfillment_status=shipped&updated_at_min=".concat(lastModified.toString());
		}
		
		int count = rest.getForObject(baseUri + "orders/count.json".concat(queryString),
				ShopifyProductsCount.class).getCount();
		List<Order> ordersList = new ArrayList<>();
		
		int numPages = (count / MAX_PAGE_SIZE) + 1;
		for (int page = 1; page <= numPages; page++) {
			ShopifyOrders orders = rest.getForObject(
					String.format("%s%s%s&limit=%d&page=%d", baseUri, 
							"orders.json", queryString, MAX_PAGE_SIZE, page), 
					ShopifyOrders.class);
			ordersList.addAll(orders.getOrders());
		}
		
		return ordersList;
	}

	@Override
	public List<MetaField> getMetaFields(String type, Long typeId) {
		final String url = baseUri + type + "/" + typeId.toString() + "/metafields.json";
		ArtWorkMetafields metafields = rest.getForObject(url, ArtWorkMetafields.class);
		return metafields.getMetafields();
	}
	
	@Override
	public CustomCollection getArtistCollection(Long artistCollectionId) {
		
		CustomCollectionWrapper artistCollection = rest.getForObject(baseUri + "custom_collections/" 
				+ artistCollectionId.toString() + ".json", 
				CustomCollectionWrapper.class);
		CustomCollection artistProduct = artistCollection.getCustomCollection();
		return artistProduct;
	}

	@Override
	public CustomCollection createDynamicProduct(FrameVariant frameVariant,
			ProductSize productSize, Double productPrice, String type) {
		StringBuilder productData = new StringBuilder();
		StringBuilder variantData = new StringBuilder();
		String productDescription =  productSize.toString();
		if (type.equalsIgnoreCase("frames")) {
			productDescription = productSize.toString() 
					+ frameVariant.getMountThickness() + frameVariant.getFrameThickness();
		}
		
		variantData.append("{\"option1\": \"")
			.append(productDescription)
			.append("\"").append(",")
			.append("\"price\": \"")
			.append(productPrice.toString())
			.append("\"").append(",")
			.append("\"sku\": \"")
			.append("123\"")
		.append("}");
		
		StringBuilder imageData = new StringBuilder();
		imageData.append("{\"src\": \"")
			.append(frameVariant.getImgSrc())
			.append("\"")
		.append("}");
		
		productData.append("{\"product\": {")
			.append("\"title\": \"")
			.append(frameVariant.getFrameTitle())
			.append("\"").append(",")
			.append("\"body_html\": \"")
			.append("Dynamic Product")
			.append("\"").append(",")
			.append("\"vendor\": \"")
			.append("Art Collective")
			.append("\"").append(",")
			.append("\"product_type\": \"")
			.append("dynamic")
			.append("\"").append(",")
			.append("\"variants\": [").append(variantData).append("]")
			.append(",")
			.append("\"images\": [").append(imageData).append("]")
		.append("}}");
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(productData.toString(), headers);
		
		DynamicProduct product = rest.postForObject(baseUri + "products.json", 
				entity, DynamicProduct.class);
		
		return product.getProduct();
	}

	

}
