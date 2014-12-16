package com.hashedin.artcollective.service;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Method;
import org.imgscalr.Scalr.Mode;
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
import com.hashedin.artcollective.entity.ArtworkVariant;
import com.hashedin.artcollective.entity.FrameVariant;
import com.hashedin.artcollective.entity.Image;
import com.hashedin.artcollective.entity.SynchronizeLog;
import com.hashedin.artcollective.repository.ArtCollectionsRepository;
import com.hashedin.artcollective.repository.ArtStyleRepository;
import com.hashedin.artcollective.repository.ArtSubjectRepository;
import com.hashedin.artcollective.repository.ArtWorkRepository;
import com.hashedin.artcollective.repository.ArtistRepository;
import com.hashedin.artcollective.repository.ArtworkVariantRepository;
import com.hashedin.artcollective.repository.FrameVariantRepository;
import com.hashedin.artcollective.repository.ImageRepository;
import com.hashedin.artcollective.repository.PriceBucketRepository;
import com.hashedin.artcollective.repository.SynchronizeLogRepository;

@Service
public class ArtWorksService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ArtWorksService.class);
	
	private static final int TITLE_SIZE = 3;

	private static final int WIDTH_OF_ART_FINDER_IMAGE = 198;
	
	private static final int WIDTH_OF_ART_DETAILS_IMAGE = 462;
	
	@Autowired
	private ShopifyService shopify;
	
	@Autowired
	private TinEyeService tineye;
	
	@Autowired
	private SynchronizeLogRepository syncLogRepository;
	
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
	private PriceAndSizeBucketService priceAndSizeBucketService;
	
	@Autowired
	private PriceBucketRepository priceBucketRepository;
	
	@Autowired
	private FrameVariantRepository frameRepository;
	
	@Autowired
	private ArtworkVariantRepository artworkVariantRepository;
	
	
	// Method to synchronize data from Shopify
	public void synchronize(String mode) {
		try {
			DateTime lastRunTime = null;
			if (!"full".equalsIgnoreCase(mode)) {
				lastRunTime = getLastRunTime();
			}
			DateTime syncStartTime = new DateTime();
			List<ArtWork> arts = getArtWorksModifiedSince(lastRunTime);
			if (arts.size() > 0) {
				saveArtToInternalDatabase(arts);
				saveArtToTinEye(arts);
			}
			saveAddOnsModifiedSince(lastRunTime);
			DateTime syncEndTime = new DateTime();
			SynchronizeLog syncLog = new SynchronizeLog(syncStartTime, syncEndTime, "artworks",
					"successfull", (long) arts.size());
			syncLogRepository.save(syncLog);
		}
		catch (Exception e) {
			LOGGER.error("Error in synchrnozation", e);
			throw new RuntimeException(e);
		}
		
	}
	
	//Fetches frames from shopify and verifies whether they have Mount and Frame Thickness
	public void saveAddOnsModifiedSince(DateTime lastRunTime) {
		List<CustomCollection> addOnProducts = shopify.getAddOnProductsSinceLastModified(lastRunTime, "frames");
		addOnProducts.addAll(shopify.getAddOnProductsSinceLastModified(lastRunTime, "canvas"));
		for (CustomCollection product : addOnProducts) {
				List<FrameVariant> frameVariants = getFrameVariants(product);
				frameRepository.save(frameVariants);	
		}
		
	}
	
	private List<FrameVariant> getFrameVariants(CustomCollection product) {
		List<FrameVariant> frameVariants = new ArrayList<>();
		Image productImg = product.getImage();
		if (productImg == null || productImg.getImgSrc() == null) {
			return null;
		}
		for (Variant variant : product.getVariants()) {
			String productType = product.getProductType();
			if (!variantHasFrameableValues(variant, productType)) {
				LOGGER.info("No Frame or Mount Thickness: Frame {} Must have supportable Frame "
						+ "and Mount thickness Rejecting Product ", product.getTitle());
				
			} 
			else {
				FrameVariant frameVariant = new FrameVariant();
				frameVariant.setId(variant.getId());
				if (productType.equalsIgnoreCase("frames")) {
					frameVariant.setFrameThickness(Double.parseDouble((variant.getOption2())));
					frameVariant.setMountThickness(Double.parseDouble((variant.getOption3())));
				}
				frameVariant.setProductId(product.getId());
				frameVariant.setUnitPrice(variant.getPrice());
				frameVariant.setImgSrc(productImg.getImgSrc());
				frameVariant.setFrameTitle(product.getTitle());
				frameVariants.add(frameVariant);
			}
			
		}
		
		return frameVariants;
	}

	// Fetches Artworks from Shopify and saves as a saves a secondary to tineye.
	private void saveArtToTinEye(List<ArtWork> arts) {
			tineye.uploadArts(arts);
			tineye.extractColors(arts);
	}

	// Saves the list of arts into the internal Database.
	private void saveArtToInternalDatabase(List<ArtWork> arts) {
		artRepository.save(arts);
		
	}

	List<ArtWork> getArtWorksModifiedSince(DateTime lastRunTime) {
		List<ArtWork> arts = new ArrayList<>();
		// Fetches Artworks from Shopify and then updates them into internal DB.
		List<CustomCollection> products = shopify.getArtWorkProductsSinceLastModified(lastRunTime);
		for (CustomCollection p : products) {
			try {
				List<Collection> collections = shopify.getCollectionsForProduct(p.getId());
				List<MetaField> metafields = shopify.getMetaFields("products", p.getId());
				ArtWork art = createArtWork(p, collections, metafields);
				if (art != null) {
					arts.add(art);
				}
			}
			catch (Exception e) {
				LOGGER.error("Could not process Product " + p.getHandle() + ", skipping it", e);
			}
		}
		return arts;
	}

	private ArtWork createArtWork(CustomCollection p, List<Collection> collections, List<MetaField> metafields) {
		boolean origMedium = false, origSize = false, origPrice = false;
		ArtWork artwork = new ArtWork();
		List<ArtSubject> subject = new ArrayList<>();
		List<ArtStyle> style = new ArrayList<>();
		Artist artist = new Artist();
		List<ArtCollection> artCollections = new ArrayList<>();
		// Classifying Collections into each category.
		for (Collection collection : collections) {
			String tokens[] = collection.getTitle().split("_");
			if (tokens == null || tokens.length < 2) {
				LOGGER.info("Invalid Collection Type: Collection type {} not recognised", 
						collection.getTitle());
				continue;
			}
			String collectionType = tokens[0]; 
			switch(collectionType) {
			case "subject":
				ArtSubject artSubject = new ArtSubject(collection.getId(), 
						getCollectionTitle(collection.getTitle()));
				subject.add(artSubject);
				artSubjectRepository.save(artSubject);
				break;
			case "styles":
				ArtStyle artStyle = new ArtStyle(collection.getId(), 
						getCollectionTitle(collection.getTitle()));
				style.add(artStyle);
				artStyleRepository.save(artStyle);
				break;
			case "artist":
				String[] artistTitle = collection.getTitle().split("_");
				artist = artistWithCollectionId(collection.getId());
				if (artist == null) {
					artist = getArtistObject(artistTitle[1], artistTitle.length 
							>= TITLE_SIZE ? artistTitle[2] : "", collection.getHandle(), 
							collection.getId());
					artistRepository.save(artist);
				} 
				break;
			case "coll":
				ArtCollection artCollection = new ArtCollection(collection.getId(), 
						getCollectionTitle(collection.getTitle()));
				artCollections.add(artCollection);
				artCollectionsRepository.save(artCollection);
				break;
			default:
				LOGGER.info("Invalid Collection Type: Collection type {} not recognised", 
						collection.getTitle());
				break;
			}
		}
		artwork.setSubject(subject);
		artwork.setArtist(artist);
		artwork.setStyle(style);
		artwork.setCollection(artCollections);
		
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
			case "original_art_medium":
				origMedium = metafield.getValue() != null || !metafield.getValue().equalsIgnoreCase("") 
					? true : false;
				break;
			case "original_art_size":
				origSize = metafield.getValue() != null || !metafield.getValue().equalsIgnoreCase("") 
					? true : false;
				break;
			case "original_art_price":
				origPrice = metafield.getValue() != null || !metafield.getValue().equalsIgnoreCase("") 
					? true : false;
				break;
			default:
				break;
			}
		}
		
		boolean isValidArtwork = validateArtwork(artwork, p, origMedium, origPrice, origSize);
		
		if (isValidArtwork) {
			Variant cheapest = Collections.min(p.getVariants());
			Variant costliest = Collections.max(p.getVariants());
			// Setting attribute values to artwork object and saving them.
			artwork.setTitle(p.getTitle());
			artwork.setId(p.getId());
			artwork.setSkuId(p.getId());
			artwork.setHandle(p.getHandle());
			artwork.setDescription(p.getBodyHtml());
			artwork.setCreatedAt(p.getCreatedAt());
			PriceAndSizeBucket priceAndSizeBucket = priceAndSizeBucketService.getPriceAndSizeBuckets(p);
			artwork.setPriceBuckets(priceAndSizeBucket.getPriceBuckets());
			artwork.setSizeBuckets(priceAndSizeBucket.getSizeBuckets());
			artwork.setMinPrice(cheapest.getPrice());
			artwork.setMaxPrice(costliest.getPrice());
			artwork.setMinSize(cheapest.getOption1());
			artwork.setMaxSize(costliest.getOption1());
			artwork.setVariantCount(p.getVariants().size());
			List<ArtworkVariant> artworkVariants = getArtworkVariants(artwork, p.getVariants());
			artworkVariantRepository.save(artworkVariants);
			artwork.setVariants(artworkVariants);
			Image image = resizeFeaturedImage(p, metafields, p.getImages(), p.getImage());
			if (image != null) {
				List<Image> images = p.getImages();
				/* Old image is removed and the same image with Dimensions is added */
				images.remove(image);
				images.add(image);
				p.setImages(images);
			}
			imageRepository.save(p.getImages());
			artwork.setImages(p.getImages());
			return artwork;
		}
		
		return null;
		

	}
	
	
	private String getCollectionTitle(String title) {
		String[] splitByUnderscore = title.split("_");
		String collectionTitle = "";
		int subjectsLength = splitByUnderscore.length;
		for (int titleIterator = 1; titleIterator < subjectsLength; titleIterator++) {
			collectionTitle = collectionTitle.concat(splitByUnderscore[titleIterator]);
			collectionTitle = titleIterator + 1 < subjectsLength ? collectionTitle.concat(" ") 
					: collectionTitle;
		}
		return collectionTitle;
	}

	private Artist getArtistObject(String firstName, String lastName,
			String handle, Long collectionId) {
		Artist artist = new Artist(firstName, lastName, handle, collectionId);
		List<MetaField> artistMetafields = shopify.getMetaFields("custom_collections", collectionId);
		for (MetaField metafield : artistMetafields) {
			switch (metafield.getKey()) {
			case "artist_email":
				artist.setEmail(metafield.getValue());
				break;
			case "artist_contact_no":
				artist.setContactNumber(metafield.getValue());
				break;
			case "artist_account_username":
				artist.setUsername(metafield.getValue());
				break;
			case "artist_account_password":
				artist.setPassword(metafield.getValue());
				break;
			default:
				break;
			}
		}
		
		artist.setImgSrc(getImageForArtist(collectionId));
		
		return artist;
	}

	private String getImageForArtist(Long collectionId) {
		
		CustomCollection artistProduct = shopify.getArtistCollection(collectionId);
		Image image = artistProduct.getImage();
		if (image != null) {
			return image.getImgSrc();
		}
		return null;
	}

	private List<ArtworkVariant> getArtworkVariants(ArtWork artwork, List<Variant> variants) {
		List<ArtworkVariant> artworkVariants = new ArrayList<>();
		for (Variant variant : variants) {
			ArtworkVariant artworkVariant = new ArtworkVariant();
			artworkVariant.setId(variant.getId());
			artworkVariant.setOption1(variant.getOption1());
			artworkVariant.setOption2(variant.getOption2());
			artworkVariant.setOption3(variant.getOption3());
			artworkVariant.setPrice(variant.getPrice());
			artworkVariant.setProductId(variant.getProductId());
			Double earning = getEarningsForVariant(variant.getId()); 
			artworkVariant.setEarning((earning != null) 
					&& (variant.getPrice() > earning) ? earning : 0); 
			artworkVariants.add(artworkVariant);
		}
		return artworkVariants;
	}
	
	private Double getEarningsForVariant(Long variantId) {
		List<MetaField> variantMetafields = shopify.getMetaFields("variants", variantId);
		for (MetaField metafield : variantMetafields) {
			if (metafield.getKey().equalsIgnoreCase("artist_earning")) {
				return Double.valueOf(metafield.getValue());
			}
		}
		return null;
	}

	private Artist artistWithCollectionId(Long collectionId) {
		 return artistRepository.findArtistByCollectionID(collectionId);
	}

	private boolean validateArtwork(ArtWork artwork, 
			CustomCollection p, boolean origMedium, boolean origPrice, boolean origSize) {
		String artworkLogger = String.format("Artwork Id:%d - Handle:%s :", 
				p.getId(), p.getHandle());
		boolean isValid = true;
		//If artwork is framable check if it has a mount thickness and a frame thickness
		if (artwork.isFrameAvailable()) {
			for (Variant variant : p.getVariants()) {
				if (!variantHasFrameableValues(variant, "artwork")) {
					artworkLogger = artworkLogger.concat("-- Missing Frame or Mount Thicness "
							+ "in Variants:The Artwork variants do not have frame and "
							+ "mount Thickness");
					/*TODO Rejecting Artwork since no frameable values
					*must send an email with product id.*/ 
					isValid = false;
					break;
				}
			}
		}
		// Saving Images into Repository.
		if (p.getImages().size() == 0) {
			artworkLogger = artworkLogger.concat("-- Missing Image: The Artwork must have "
					+ "atleast one image");
			isValid = false;
		}
		// Fetching Cheapest and Costliest Variant
		if (p.getVariants().size() == 0) {
			artworkLogger = artworkLogger.concat("-- Missing Variants: The Artwork must have "
					+ "atleast one variant");
			isValid = false;
		}
		
		if (artwork.getSubject().size() == 0) {
			artworkLogger = artworkLogger.concat("-- Missing Subject List: The Artwork must "
					+ "belong to at the least one subject");
			//4 Dec 2014 : Subjects no longer mandatory
		}
		if (artwork.getCollection().size() == 0) {
			artworkLogger = artworkLogger.concat("-- Missing Collections List: The Artwork must belong "
					+ "to at the least one collection");
			//4 Dec 2014 : Collections no longer mandatory
		}
		if (artwork.getStyle().size() == 0) {
			artworkLogger = artworkLogger.concat("-- Missing Style List: The Artwork must belong to at the "
					+ "least one style");
			//4 Dec 2014 : Styles no longer mandatory
		}
		if (artwork.getArtist() == null || artwork.getArtist().getHandle() == null) {
			artworkLogger = artworkLogger.concat("-- Missing Artist: The Artwork must have an artist");
			isValid = false;
		}
		if (artwork.getMedium().equalsIgnoreCase("")) {
			artworkLogger = artworkLogger.concat("-- Missing Medium: The Artwork must belong to a medium");
			isValid = false;
		}
		if (artwork.getOrientation().equalsIgnoreCase("")) {
			artworkLogger = artworkLogger.concat("-- Missing Orientation: The Artwork must "
					+ "belong to an orientation");
			isValid = false;
		}
		//Loggers for original Artwork
		if (artwork.isOriginalAvailable()) {
			if (!origMedium || !origPrice || !origSize) {
				artworkLogger = artworkLogger.concat("-- Missing Original Artwork Details: "
						+ "The original Artwork must have original price, size and medium "
						+ "mentioned");
				isValid = false;
			}
		}
		if (!isValid) {
			LOGGER.info(artworkLogger);
			return false;
		}
		return true;
	}

	private Image resizeFeaturedImage(CustomCollection p, List<MetaField> metafields,
			List<Image> images, Image image) {
		try {
			Image resizedimage = maybeResizeImage(p, metafields, p.getImages(), p.getImage());
			return resizedimage;
		}
		catch (IOException ioe) {
			LOGGER.error("Could not resize image for Product " + p, ioe);
		}
		
		return null;
		
	}

	/*
	 * 1. Check if the product has an image of width = 198px, height = whatever
	 * 2. If it has, do nothing
	 * 3. If it does not have - 
	 * 3.1 Download the default image from shopify
	 * 3.2 Resize the image
	 * 3.3 Upload the resized image
	 * 3.4 Store the id of the resized image in a meta-field
	 */
	private Image maybeResizeImage(CustomCollection p, List<MetaField> metafields,
			List<Image> images, Image featuredImage) throws IOException {
		
		String format = determineFormat(featuredImage);
		BufferedImage original;
		try {
			original = ImageIO.read(new URL(featuredImage.getImgSrc()));
		}
		catch (Exception e) {
			LOGGER.error("Could not read image {}", featuredImage.getImgSrc(), e);
			throw new RuntimeException("Could not read image " + featuredImage.getImgSrc(), e);
		}
		
		if (!imageExistsWithPattern(images, "-artdetails")) {
			BufferedImage resizedArtDetailsImage = resizeImage(original, WIDTH_OF_ART_DETAILS_IMAGE);
			ByteArrayOutputStream artDetailsBos = new ByteArrayOutputStream();
			ImageIO.write(resizedArtDetailsImage, format, artDetailsBos);
			shopify.uploadImage(p, 
				new ByteArrayInputStream(artDetailsBos.toByteArray()), 
					String.format("%s-artdetails.%s", p.getHandle(), format));
		}
		if (imageExistsWithPattern(images, "-artfinder")) {
			Image image = new Image();
			/*
			 * TODO - Delete this block
			 * Code was added to compute dimensions of existing images
			 * After a few runs of synchronize, this block is pointless
			 */
			try {
				image = getImageWithPattern(images, "-artfinder");
				setImageDimensions(image);
				return image;
			} 
			catch (Exception ioe) {
				LOGGER.error(
						"Error - setting image dimensions for Image id - "
		 						+ image.getId(), ioe);
			}
			
		}
		
		
		BufferedImage resizedArtFinderImage = resizeImage(original, WIDTH_OF_ART_FINDER_IMAGE);
		int resizedHeight = resizedArtFinderImage.getHeight();
		int resizedWidth = resizedArtFinderImage.getWidth();
		ByteArrayOutputStream artFinderBos = new ByteArrayOutputStream();
		ImageIO.write(resizedArtFinderImage, format, artFinderBos);
		
		Image artFinderImage = shopify.uploadImage(p, 
				new ByteArrayInputStream(artFinderBos.toByteArray()), 
				String.format("%s-artfinder.%s", p.getHandle(), format));
		artFinderImage.setHeight(resizedHeight);
		artFinderImage.setWidth(resizedWidth);
		return artFinderImage;
	}

	private void setImageDimensions(final Image image) throws IOException {
		BufferedImage original = ImageIO.read(new URL(image.getImgSrc()));
		image.setWidth(original.getWidth());
		image.setHeight(original.getHeight());
	}

	private Image getImageWithPattern(List<Image> images, String pattern) {
		for (Image image : images) {
			if (image.getImgSrc().contains(pattern)) {
				return image;
			}
		}
		return null;
	}

	private boolean imageExistsWithPattern(List<Image> images, String pattern) {
		Image image = getImageWithPattern(images, pattern);
		return (image != null);
	}

	private String determineFormat(Image image) {
		String url = image.getImgSrc();
		if (url.contains(".jpg") || url.contains(".jpeg")) {
			return "jpg";
		}
		else if (url.contains(".png")) {
			return "png";
		}
		return "jpg";
	}

	private BufferedImage resizeImage(BufferedImage original, int widthOfImage) {
		return Scalr.resize(original, Method.ULTRA_QUALITY, Mode.FIT_TO_WIDTH, widthOfImage);
		
	}
	
	private boolean variantHasFrameableValues(Variant variant, String type) {
		try {
			if (!type.equalsIgnoreCase("canvas")) {
				if (variant.getOption2() == null || variant.getOption3() == null 
						|| variant.getOption2().equalsIgnoreCase("") 
						|| variant.getOption3().equalsIgnoreCase("")) {
					return false;
				}
				else {
					if (!type.equalsIgnoreCase("frames")) {
						if ((Double.parseDouble((variant.getOption1()
								.split("[Xx]")[0].split("\"")[0])) < 0)
						   || (Double.parseDouble((variant.getOption1()
								.split("[Xx]")[1].split("\"")[0])) < 0)) {
							return false;
						}
					}
					if ((Double.parseDouble((variant.getOption2())) < 0) 
							|| (Double.parseDouble((variant.getOption3())) < 0)) {
							return false;
					}
				}
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

	private DateTime getLastRunTime() {
		return syncLogRepository.getLastSynchronizeDate("artworks");
	}

	public void deleteProduct(Long productId) {
		ArtWork art = artRepository.findOne(productId);
		if (art != null) {
			softDeleteArtwork(productId);
		}
		else {
			frameRepository.softDeleteVariantsByProductId(productId);
		}
	}

	private void softDeleteArtwork(Long productId) {
		ArtWork artwork = artRepository.findOne(productId);
		artwork.setDeleted(true);
		artRepository.save(artwork);
	}
	
}
