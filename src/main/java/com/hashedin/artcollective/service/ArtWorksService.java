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
import com.hashedin.artcollective.entity.FrameVariant;
import com.hashedin.artcollective.entity.Image;
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

	private static final int WIDTH_OF_IMAGE = 198;
	
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
	private PriceAndSizeBucketService priceAndSizeBucketService;
	
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
			tineye.extractColors(arts);
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

	private ArtWork createArtWork(Product p, List<Collection> collections, List<MetaField> metafields) {
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
						collection.getTitle().split("_")[1]);
				subject.add(artSubject);
				artSubjectRepository.save(artSubject);
				break;
			case "styles":
				ArtStyle artStyle = new ArtStyle(collection.getId(), 
						collection.getTitle().split("_")[1]);
				style.add(artStyle);
				artStyleRepository.save(artStyle);
				break;
			case "artist":
				String[] artistTitle = collection.getTitle().split("_");
				artist = new Artist(collection.getId(), artistTitle[1], artistTitle.length 
						== TITLE_SIZE ? artistTitle[2] : "", collection.getHandle());
				artistRepository.save(artist);
				break;
			case "coll":
				ArtCollection artCollection = new ArtCollection(collection.getId(), 
						collection.getTitle().split("_")[1]);
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
			artwork.setImages(p.getImages());
			artwork.setHandle(p.getHandle());
			artwork.setCreatedAt(p.getCreatedAt());
			PriceAndSizeBucket priceAndSizeBucket = priceAndSizeBucketService.getPriceAndSizeBuckets(p);
			artwork.setPriceBuckets(priceAndSizeBucket.getPriceBuckets());
			artwork.setSizeBuckets(priceAndSizeBucket.getSizeBuckets());
			artwork.setMinPrice(cheapest.getPrice());
			artwork.setMaxPrice(costliest.getPrice());
			artwork.setMinSize(cheapest.getOption1());
			artwork.setMaxSize(costliest.getOption1());
			artwork.setVariantCount(p.getVariants().size());
			Image image = resizeFeaturedImage(p, metafields, p.getImages(), p.getImage());
			if (image != null) {
				List<Image> images = p.getImages();
				/* Old image is removed and the same image with Dimensions is added */
				images.remove(image);
				images.add(image);
				p.setImages(images);
			}
			imageRepository.save(p.getImages());
			return artwork;
		}
		
		return null;
		

	}
	

	private boolean validateArtwork(ArtWork artwork, 
			Product p, boolean origMedium, boolean origPrice, boolean origSize) {
		String artworkLogger = String.format("Artwork Id:%d - Handle:%s :", 
				p.getId(), p.getHandle());
		boolean isValid = true;
		//If artwork is framable check if it has a mount thickness and a frame thickness
		if (artwork.isFrameAvailable()) {
			for (Variant variant : p.getVariants()) {
				if (!variantHasFrameableValues(variant)) {
					artworkLogger = artworkLogger.concat("-- Missing Frame or Mount Thicness "
							+ "in Variants:The Artwork variants do not have frame and "
							+ "mount Thickness");
					//TODO Rejecting Artwork since no image must send an email with product id. 
					isValid = false;
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
			isValid = false;
		}
		if (artwork.getCollection().size() == 0) {
			artworkLogger = artworkLogger.concat("-- Missing Collections List: The Artwork must belong "
					+ "to at the least one collection");
			isValid = false;
		}
		if (artwork.getStyle().size() == 0) {
			artworkLogger = artworkLogger.concat("-- Missing Style List: The Artwork must belong to at the "
					+ "least one style");
			isValid = false;
		}
		if (artwork.getArtist() == null) {
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

	private Image resizeFeaturedImage(Product p, List<MetaField> metafields,
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
	private Image maybeResizeImage(Product p, List<MetaField> metafields,
			List<Image> images, Image featuredImage) throws IOException {
		if (imageForArtFinderExists(images)) {
			Image image = new Image();
			/*
			 * TODO - Delete this block
			 * Code was added to compute dimensions of existing images
			 * After a few runs of synchronize, this block is pointless
			 */
			try {
				image = getArtFinderImage(images);
				setImageDimensions(image);
				return image;
			} 
			catch (Exception ioe) {
				LOGGER.error(
						"Error - setting image dimensions for Image id - "
		 						+ image.getId(), ioe);
			}
			
		}
		
		String format = determineFormat(featuredImage);
		BufferedImage original = ImageIO.read(new URL(featuredImage.getImgSrc()));
		BufferedImage resizedImage = resizeImage(original);
		int resizedHeight = resizedImage.getHeight();
		int resizedWidth = resizedImage.getWidth();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ImageIO.write(resizedImage, format, bos);
		
		Image image = shopify.uploadImage(p, 
				new ByteArrayInputStream(bos.toByteArray()), 
				String.format("%s-artfinder.%s", p.getHandle(), format));
		image.setHeight(resizedHeight);
		image.setWidth(resizedWidth);
		return image;
	}

	private void setImageDimensions(final Image image) throws IOException {
		BufferedImage original = ImageIO.read(new URL(image.getImgSrc()));
		image.setWidth(original.getWidth());
		image.setHeight(original.getHeight());
	}

	private Image getArtFinderImage(List<Image> images) {
		for (Image image : images) {
			if (image.getImgSrc().contains("-artfinder")) {
				return image;
			}
		}
		return null;
	}

	private boolean imageForArtFinderExists(List<Image> images) {
		Image image = getArtFinderImage(images);
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

	private BufferedImage resizeImage(BufferedImage original) {
		return Scalr.resize(original, Mode.FIT_TO_WIDTH, WIDTH_OF_IMAGE);
		
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

	private DateTime getLastRunTime() {
		return null;
	}
	
}
