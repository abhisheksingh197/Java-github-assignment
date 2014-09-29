package com.hashedin.artcollective.service;
import java.util.ArrayList;
import java.util.Collections;
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
import com.hashedin.artcollective.entity.FrameVariant;
import com.hashedin.artcollective.repository.ArtCollectionsRepository;
import com.hashedin.artcollective.repository.ArtStyleRepository;
import com.hashedin.artcollective.repository.ArtSubjectRepository;
import com.hashedin.artcollective.repository.ArtWorkRepository;
import com.hashedin.artcollective.repository.ArtistRepository;
import com.hashedin.artcollective.repository.FrameVariantRepository;
import com.hashedin.artcollective.repository.ImageRepository;
import com.hashedin.artcollective.repository.PriceBucketRepository;

@Service
public class ArtWorksService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ArtWorksService.class);
	
	private static final int TITLE_SIZE = 3;
	
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
	private PriceBucketService priceBucketService;
	
	@Autowired
	private PriceBucketRepository priceBucketRepository;
	
	@Autowired
	private FrameVariantRepository frameRepository;
	
	public void synchronize() {
		DateTime lastRunTime = getLastRunTime();
		List<ArtWork> arts = getArtWorksModifiedSince(lastRunTime);
		if (arts.size() > 0) {
			saveArtToInternalDatabase(arts);
			saveArtToTinEye(arts);
		}
		saveFramesModifiedSince(lastRunTime);
		
	}
	
	//Fetches frames from shopify and verifies whether they have Mount and Frame Thickness
	public void saveFramesModifiedSince(DateTime lastRunTime) {
		List<Product> products = shopify.getFrameProductsSinceLastModified(lastRunTime);
		for (Product product : products) {
				List<FrameVariant> frameVariants = getFrameVariants(product);
				frameRepository.save(frameVariants);	
		}
		
	}
	
	private List<FrameVariant> getFrameVariants(Product product) {
		List<FrameVariant> frameVariants = new ArrayList<>();
		for (Variant variant : product.getVariants()) {
			if (!variantHasFrameableValues(variant)) {
				LOGGER.info("No Frame or Mount Thickness: Frame {} Must have supportable Frame "
						+ "and Mount thickness Rejecting Product ", product.getTitle());
				
			} 
			else {
				FrameVariant frameVariant = new FrameVariant();
				frameVariant.setId(variant.getId());
				frameVariant.setFrameLength(Double.parseDouble((variant.getOption1()
						.split("[Xx]")[0].split("\"")[0])));
				frameVariant.setFrameBreadth(Double.parseDouble((variant.getOption1()
						.split("[Xx]")[1].split("\"")[0])));
				frameVariant.setMountThickness(Double.parseDouble((variant.getOption2())));
				frameVariant.setFrameThickness(Double.parseDouble((variant.getOption3())));
				frameVariant.setUnitPrice(variant.getPrice());
				frameVariant.setImgSrc(product.getImage().getImgSrc());
				frameVariant.setFrameTitle(product.getTitle());
				frameVariants.add(frameVariant);
			}
			
		}
		
		return frameVariants;
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
		List<Product> products = shopify.getArtWorkProductsSinceLastModified(lastRunTime);
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
				LOGGER.info("Invalid Collection Type: Collection type {} not recognised", 
						collection.getTitle());
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
			case "artwork_medium":
				artwork.setMedium(metafield.getValue() == null ? "" : metafield.getValue());
				break;
			case "artwork_orientation":
				artwork.setOrientation(metafield.getValue() == null ? "" : metafield.getValue());
				break;
			default:
				break;
			}
		}
		
		//If artwork is framable check if it has a mount thickness and a frame thickness
		if (artwork.isFrameAvailable()) {
			for (Variant variant : p.getVariants()) {
				if (!variantHasFrameableValues(variant)) {
					LOGGER.info("Missing Frame or Mount Thicness: The Framable Artwork {} must "
					+ "have a Mount Thickness and a Frame Thickness mentioned.", p.getTitle());
					//TODO Rejecting Artwork since no image must send an email with product id. 
					return null;
				}
			}
			
		}
		
		
		// Saving Images into Repository.
		if (p.getImages().size() == 0) {
			LOGGER.info("Missing Image: The Artwork {} must at the least have one Image", 
					p.getTitle());
			//TODO Rejecting Artwork since no image must send an email with product id. 
			return null;
		}
		imageRepository.save(p.getImages());
		
		// Fetching Cheapest and Costliest Variant
		if (p.getVariants().size() == 0) {
			LOGGER.info("Missing Variants: The Artwork {} must have at the least one variant", 
					p.getTitle());
			//TODO Rejecting Artwork since no image must send an email with product id. 
			return null;
		}
		Variant cheapest = Collections.min(p.getVariants());
		Variant costliest = Collections.max(p.getVariants());
		
		
		
		
		// Setting attribute values to artwork object and saving them.
		artwork.setTitle(p.getTitle());
		artwork.setId(p.getId());
		artwork.setSkuId(p.getId());
		artwork.setSubject(subject);
		artwork.setArtist(artist);
		artwork.setCollection(artCollections);
		artwork.setStyle(style);
		artwork.setImages(p.getImages());
		artwork.setHandle(p.getHandle());
		artwork.setPriceBuckets(priceBucketService.getPriceBuckets(p));
		artwork.setMinPrice(cheapest.getPrice());
		artwork.setMaxPrice(costliest.getPrice());
		artwork.setMinSize(cheapest.getOption1());
		artwork.setMaxSize(costliest.getOption1());
		artwork.setVariantCount(p.getVariants().size());
		if (artWorkValidator(artwork)) {
			return artwork;
		}
		return null;
	}
	

	private boolean variantHasFrameableValues(Variant variant) {
		try {
			if (variant.getOption2() == null || variant.getOption3() == null 
					|| variant.getOption2().equalsIgnoreCase("") 
					|| variant.getOption3().equalsIgnoreCase("")) {
				return false;
			}
			else if ((Double.parseDouble((variant.getOption1().split("[Xx]")[0].split("\"")[0])) < 0)
				|| (Double.parseDouble((variant.getOption1().split("[Xx]")[1].split("\"")[0])) < 0)
				|| (Double.parseDouble((variant.getOption2())) < 0) 
				|| (Double.parseDouble((variant.getOption3())) < 0)) {
						return false;
					}
		return true;
		}
		catch (NumberFormatException e) {
			return false;
		}
		catch (IndexOutOfBoundsException e) {
			return false;
		}
		
			
	}

	private boolean artWorkValidator(ArtWork artwork) {
		boolean isValid = true;
		if (artwork.getSubject().size() == 0) {
			LOGGER.info("Missing Subject List: The Artwork {} must belong to at the least one subject", 
					artwork.getTitle());
			isValid = false;
		}
		if (artwork.getCollection().size() == 0) {
			LOGGER.info("Missing Collections List: The Artwork {} must belong "
					+ "to at the least one collection", artwork.getTitle());
			isValid = false;
		}
		if (artwork.getStyle().size() == 0) {
			LOGGER.info("Missing Style List: The Artwork {} must belong to at the least one style", 
					artwork.getTitle());
			isValid = false;
		}
		if (artwork.getArtist() == null) {
			LOGGER.info("Missing Artist: TThe Artwork {} must have an artist", 
					artwork.getTitle());
			isValid = false;
		}
		if (artwork.getMedium().equalsIgnoreCase("")) {
			LOGGER.info("Missing Medium: The Artwork {} must belong to a medium", 
					artwork.getTitle());
			isValid = false;
		}
		if (artwork.getOrientation().equalsIgnoreCase("")) {
			LOGGER.info("Missing Orientation: The Artwork {} must belong to an orientation", 
					artwork.getTitle());
			isValid = false;
		}
		
		return isValid;
		
	}

	private DateTime getLastRunTime() {
		return null;
	}
	
}
