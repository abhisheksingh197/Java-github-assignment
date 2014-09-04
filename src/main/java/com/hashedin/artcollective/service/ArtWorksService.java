package com.hashedin.artcollective.service;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hashedin.artcollective.entity.ArtCollection;
import com.hashedin.artcollective.entity.ArtStyle;
import com.hashedin.artcollective.entity.ArtSubject;
import com.hashedin.artcollective.entity.ArtWork;
import com.hashedin.artcollective.entity.Artist;
import com.hashedin.artcollective.entity.PriceBucket;
import com.hashedin.artcollective.repository.ArtCollectionsRepository;
import com.hashedin.artcollective.repository.ArtStyleRepository;
import com.hashedin.artcollective.repository.ArtSubjectRepository;
import com.hashedin.artcollective.repository.ArtWorkRepository;
import com.hashedin.artcollective.repository.ArtistRepository;
import com.hashedin.artcollective.repository.ImageRepository;
import com.hashedin.artcollective.repository.PriceBucketRepository;

@Service
public class ArtWorksService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TinEyeService.class);
	
	private static final int TITLE_SIZE = 3;
	
	private static final long OBJ1 = 1, OBJ2 = 2, OBJ3 = 3, OBJ4 = 4, OBJ5 = 5;
	private static final double GREATERPRICE = 50000;
	
	private static enum PriceBucketRange {
		   lower(2500), medium(5000), average(7500), higher(10000), large(15000);
		   private double priceValue;
		   PriceBucketRange(int p) {
		      priceValue = p;
		   }
		   double getPriceValue() {
		      return priceValue;
		   } 
		}
	
	
	@Autowired
	private ShopifyService shopify;
	
	@Autowired
	private TinEyeService tineye;
	
	@Autowired 
	private ArtWorkRepository artRepository;
	
	@Autowired
	private ArtSubjectRepository artSubjectRepository;
	
	@Autowired
	private ArtStyleRepository artStyleRepository;
	
	@Autowired
	private ArtCollectionsRepository artCollectionsRepository;
	
	@Autowired
	private ArtistRepository artistRepository;
	
	@Autowired
	private ImageRepository imageRepository;
	
	@Autowired
	private PriceBucketRepository priceBucketRespository;
	
	public void synchronize() {
		DateTime lastRunTime = getLastRunTime();
		addPriceBucketsToRepository();
		List<ArtWork> arts = getArtWorksModifiedSince(lastRunTime);
		saveArtToInternalDatabase(arts);
		saveArtToTinEye(arts);
	}
	
	// Fetches Artworks from Shopify and saves as a saves a secondary to tineye.
	private void saveArtToTinEye(List<ArtWork> arts) {
			tineye.uploadArts(arts);
	}

	// Saves the list of arts into the internal Database.
	private void saveArtToInternalDatabase(List<ArtWork> arts) {
		artRepository.save(arts);
	}

	List<ArtWork> getArtWorksModifiedSince(DateTime lastRunTime) {
		List<ArtWork> arts = new ArrayList<>();
		// Fetches Artworks from Shopify and then updates them into internal DB.
		List<Product> products = shopify.getProductsSinceLastModified(lastRunTime);
		for (Product p : products) {
			List<Collection> collections = shopify.getCollectionsForProduct(p.getId());
			List<MetaField> metafields = shopify.getMetaFieldsForProduct(p.getId());
			ArtWork art = createArtWork(p, collections, metafields);
			if (art != null) {
				arts.add(art);
			}
		}
		return arts;
	}

	private ArtWork createArtWork(Product p, List<Collection> collections,
			List<MetaField> metafields) {
		ArtWork artwork = new ArtWork();
		List<ArtSubject> subject = new ArrayList<>();
		List<ArtStyle> style = new ArrayList<>();
		Artist artist = new Artist();
		List<ArtCollection> artCollections = new ArrayList<>();
		
		// Classifying Collections into each category.
		for (Collection collection : collections) {
			switch(collection.getTitle().split("_")[0]) {
			case "subject":
				ArtSubject artSubject = new ArtSubject();
				artSubject.setId(collection.getId());
				artSubject.setTitle(collection.getTitle().split("_")[1]);
				subject.add(artSubject);
				artSubjectRepository.save(artSubject);
				break;
			case "styles":
				ArtStyle artStyle = new ArtStyle();
				artStyle.setId(collection.getId());
				artStyle.setTitle(collection.getTitle().split("_")[1]);
				style.add(artStyle);
				artStyleRepository.save(artStyle);
				break;
			case "artist":
				String[] artistTitle = collection.getTitle().split("_");
				artist = new Artist(collection.getId(), artistTitle[1], 
						artistTitle.length == TITLE_SIZE ? artistTitle[2] : "");
				artistRepository.save(artist);
				break;
			case "coll":
				ArtCollection artCollection = new ArtCollection();
				artCollection.setId(collection.getId());
				artCollection.setTitle(collection.getTitle().split("_")[1]);
				artCollections.add(artCollection);
				artCollectionsRepository.save(artCollection);
				break;
			default:
				break;
			}
		}
		
		//Classifying Metafields and storing them.
		for (MetaField metafield : metafields) {
			switch (metafield.getKey()) {
			case "is_certified":
				artwork.setIsCertified(Boolean.valueOf(metafield.getValue()));
				break;
			case "is_limited_edition":
				artwork.setIsLimitedEdition(Boolean.valueOf(metafield.getValue()));
				break;
			case "is_original_available":
				artwork.setIsOriginalAvailable(Boolean.valueOf(metafield.getValue()));
				break;
			case "is_frame_available":
				artwork.setIsFrameAvailable(Boolean.valueOf(metafield.getValue()));
				break;
			case "is_canvas_available":
				artwork.setIsCanvasAvailable(Boolean.valueOf(metafield.getValue()));
				break;
			case "medium":
				artwork.setMedium(metafield.getValue());
				break;
			case "orientation":
				artwork.setOrientation(metafield.getValue());
				break;
			default:
				break;
			}
		}
		
		
		// Saving Images into Repository.
		if (p.getImages().size() == 0) {
			LOGGER.info("No Image,Rejecting Product " + String.valueOf(p.getId()));
			//TODO Rejecting Artwork since no image must send an email with product id. 
			return null;
		}
		
		imageRepository.save(p.getImages());
		List<PriceBucket> priceBuckets = getPriceBuckets(p);
		
		// Setting attribute values to artwork object and saving them.
		artwork.setTitle(p.getTitle());
		artwork.setId(p.getId());
		artwork.setSkuId(p.getId());
		artwork.setSubject(subject);
		artwork.setArtist(artist);
		artwork.setCollection(artCollections);
		artwork.setStyle(style);
		artwork.setImages(p.getImages());
		artwork.setPriceBuckets(priceBuckets);
		return artwork;
	}
	
	private List<PriceBucket> getPriceBuckets(Product p) {
		List<PriceBucket> priceBuckets = new ArrayList<>();
		List<Variant> variants = p.getVariants();
		for (Variant variant : variants) {
			double price = variant.getPrice();
			if (isBetween(price, PriceBucketRange.lower, 
					PriceBucketRange.medium)) {
				PriceBucket priceBucketObj = priceBucketRespository.findOne(OBJ1);
				priceBuckets.add(priceBucketObj);
			}
			if (isBetween(price, PriceBucketRange.medium, 
					PriceBucketRange.average)) {
				PriceBucket priceBucketObj = priceBucketRespository.findOne(OBJ2);
				priceBuckets.add(priceBucketObj);
			}
			if (isBetween(price, PriceBucketRange.average, 
					PriceBucketRange.higher)) { 
				PriceBucket priceBucketObj = priceBucketRespository.findOne(OBJ3);
				priceBuckets.add(priceBucketObj);
			}
			if (isBetween(price, PriceBucketRange.higher, 
					PriceBucketRange.large)) {
				PriceBucket priceBucketObj = priceBucketRespository.findOne(OBJ4);
				priceBuckets.add(priceBucketObj);
			}
			if (isAbove(price, PriceBucketRange.large)) {
				PriceBucket priceBucketObj = priceBucketRespository.findOne(OBJ5);
				priceBuckets.add(priceBucketObj);
			}
		}
		return priceBuckets;
	}
	
	private PriceBucket getNewPriceBucketObject(long l, String string,
			double priceValue, double priceValue2) {
		PriceBucket priceBucket = new PriceBucket();
		priceBucket.setId(l);
		priceBucket.setTitle(string);
		priceBucket.setLowerRange(priceValue);
		priceBucket.setUpperRange(priceValue2);
		return priceBucket;
	}

	private static boolean isAbove(double price, PriceBucketRange upper) {
		return price > upper.getPriceValue();
	}
	
	private static boolean isBetween(double price, PriceBucketRange lower, PriceBucketRange upper) {
		  return lower.getPriceValue() + 1 <= price && price <= upper.getPriceValue();
	}

	private DateTime getLastRunTime() {
		return null;
	}
	
	private void addPriceBucketsToRepository() {
		List<PriceBucket> priceBuckets = new ArrayList<>();
		PriceBucket priceBucketObj1 = getNewPriceBucketObject(OBJ1,
				"low", PriceBucketRange.lower.getPriceValue(), 
				PriceBucketRange.medium.getPriceValue());
		PriceBucket priceBucketObj2 = getNewPriceBucketObject(OBJ2, "medium", 
				PriceBucketRange.medium.getPriceValue(), 
				PriceBucketRange.average.getPriceValue());
		PriceBucket priceBucketObj3 = getNewPriceBucketObject(OBJ3, "average", 
				PriceBucketRange.average.getPriceValue(), 
				PriceBucketRange.higher.getPriceValue()); 
		PriceBucket priceBucketObj4 = getNewPriceBucketObject(OBJ4, "high", 
				PriceBucketRange.higher.getPriceValue(), 
				PriceBucketRange.large.getPriceValue());
		PriceBucket priceBucketObj5 = getNewPriceBucketObject(OBJ5, "large", 
				PriceBucketRange.large.getPriceValue(), 
				GREATERPRICE);
		priceBuckets.add(priceBucketObj1);
		priceBuckets.add(priceBucketObj2);
		priceBuckets.add(priceBucketObj3);
		priceBuckets.add(priceBucketObj4);
		priceBuckets.add(priceBucketObj5);
		
	priceBucketRespository.save(priceBuckets);
		
	}
}
