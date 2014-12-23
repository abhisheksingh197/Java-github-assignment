package com.hashedin.artcollective.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hashedin.artcollective.entity.ArtStyle;
import com.hashedin.artcollective.entity.ArtSubject;
import com.hashedin.artcollective.entity.ArtWork;
import com.hashedin.artcollective.entity.ArtworkVariant;
import com.hashedin.artcollective.entity.FrameVariant;
import com.hashedin.artcollective.entity.PriceBucket;
import com.hashedin.artcollective.entity.ShopifyWebHook;
import com.hashedin.artcollective.entity.SizeBucket;
import com.hashedin.artcollective.repository.ArtStyleRepository;
import com.hashedin.artcollective.repository.ArtSubjectRepository;
import com.hashedin.artcollective.repository.ArtWorkRepository;
import com.hashedin.artcollective.repository.ArtworkVariantRepository;
import com.hashedin.artcollective.repository.FulfilledOrderRepository;
import com.hashedin.artcollective.repository.PriceBucketRepository;
import com.hashedin.artcollective.repository.ShopifyWebHookRepository;
import com.hashedin.artcollective.repository.SizeBucketRepository;
import com.hashedin.artcollective.service.ArtWorksSearchService;
import com.hashedin.artcollective.service.ArtWorksService;
import com.hashedin.artcollective.service.CriteriaSearchResponse;
import com.hashedin.artcollective.service.CustomCollection;
import com.hashedin.artcollective.service.DeductionsService;
import com.hashedin.artcollective.service.FollowingService;
import com.hashedin.artcollective.service.Frame;
import com.hashedin.artcollective.service.FrameVariantService;
import com.hashedin.artcollective.service.OrdersService;
import com.hashedin.artcollective.service.PreferenceService;
import com.hashedin.artcollective.service.PriceAndSizeBucketService;
import com.hashedin.artcollective.service.ShopifyService;
import com.hashedin.artcollective.service.Style;
import com.hashedin.artcollective.service.Subject;
import com.hashedin.artcollective.service.TinEyeService;
import com.hashedin.artcollective.service.TransactionsService;
import com.hashedin.artcollective.service.WebHookResponse;
import com.hashedin.artcollective.utils.ProductSize;

@RestController
public class ProductsAPI {

    @Autowired
    private ArtWorksService artworkService;
    
    @Autowired
    private OrdersService ordersService;
    
    @Autowired
    private ArtWorksSearchService artworksSearchService;
    
    @Autowired
    private FrameVariantService frameVariantService;
    
    @Autowired
    private PriceAndSizeBucketService priceAndSizeBucketService;
    
    @Autowired
    private ArtSubjectRepository artSubjectRepository;
    
    @Autowired
    private ArtStyleRepository artStyleRepository;
    
    @Autowired
    private PriceBucketRepository priceBucketRepository;
    
    @Autowired
    private TinEyeService tinEyeService;
    
    @Autowired
    private ArtWorkRepository artWorkRepository;
    
    @Autowired
    private SizeBucketRepository sizeBucketRepository;
    
    @Autowired
    private TransactionsService transactionService;
    
    @Autowired
    private DeductionsService deductionService;
    
    @Autowired
    private ArtworkVariantRepository artworkVariantRepository;
    
    @Autowired
    private ShopifyService shopifyService;
    
    @Autowired
    private PreferenceService preferenceService;
    
    @Autowired
    private FollowingService followingService;
    
    @Autowired
    private FulfilledOrderRepository fulfilledOrderRepository;
    
    @Autowired 
    private ShopifyWebHookRepository shopifyWebHookRepository;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductsAPI.class);
    
    /**
     * Add Price Range Bucket
     * @param id
     * @param title
     * @param lowerRange (Price Bucket Lower Range - currency ex: 2500)
     * @param upperRange (Price Bucket Lower Range - currency ex: 5000)
     */
    @RequestMapping(value = "/admin/priceRange/add", method = RequestMethod.GET)
    public void addPriceBucket(
            @RequestParam(value = "id", required = true) Long id,
            @RequestParam(value = "title", required = true) String title,
            @RequestParam(value = "lowerRange", required = true) Double lowerRange,
            @RequestParam(value = "upperRange", required = false) Double upperRange) {
        PriceBucket priceBucket = new PriceBucket(id, title, lowerRange, upperRange);
        priceAndSizeBucketService.addPriceBucket(priceBucket);
        LOGGER.info("Price Bucket: " + priceBucket.getTitle() + " Successfully Added");
    
    }
    
    /**
     * API for uploading artist transactions as a Multipart CSV file.
     * @param file - CSV File
     * @return - Returns a list of errors
     */
    @RequestMapping(value = "/api/upload/transactions", method = RequestMethod.POST)
    public List<String> transactionsCSVUpload(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                Reader csvReader = new InputStreamReader(file.getInputStream(), Charset.forName("UTF-8"));
                List<String> errors = transactionService.saveTransactionsInBulk(csvReader);
                return errors;
            } 
            catch (Exception e) {
                return Arrays.asList("You failed to upload ");
            }
        } 
        else {
            return Arrays.asList("You failed to upload  because the file was empty.");
        }
    }
    
    /**
     * API for uploading artist deductions as a Multipart CSV file.
     * @param file - CSV File
     * @return - Returns a list of errors
     */
    @RequestMapping(value = "/api/upload/deductions", method = RequestMethod.POST)
    public List<String> deductionsCSVUpload(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                Reader csvReader = new InputStreamReader(file.getInputStream(), Charset.forName("UTF-8"));
                List<String> errors = deductionService.saveDeductionsInBulk(csvReader);
                return errors;
            } 
            catch (Exception e) {
                return Arrays.asList("You failed to upload ");
            }
        } 
        else {
            return Arrays.asList("You failed to upload  because the file was empty.");
        }
    }
    
    /**
     * Add Size Range Bucket
     * @param id
     * @param title
     * @param lowerRange - Lower size range (area of the artwork)
     * @param upperRange - Upper size range (area of the artwork)
     */
    @RequestMapping(value = "/admin/sizeRange/add", method = RequestMethod.GET)
    public void addSizeBucket(
            @RequestParam(value = "id", required = true) Long id,
            @RequestParam(value = "title", required = true) String title,
            @RequestParam(value = "lowerValue", required = true) Double lowerRange,
            @RequestParam(value = "upperValue", required = false) Double upperRange) {
        SizeBucket sizeBucket = new SizeBucket(id, title, lowerRange, upperRange);
        priceAndSizeBucketService.addSizeBucket(sizeBucket);
        LOGGER.info("Price Bucket: " + sizeBucket.getTitle() + " Successfully Added");
    
    }
    

    /**
     * Fetching Price Bucket Ranges
     * @return List of price Buckets for the shop
     */
    @RequestMapping(value = "/admin/priceRange/getall", method = RequestMethod.GET)
    public List<PriceBucket> getAllPriceBuckets() {
        
        return (List<PriceBucket>) priceBucketRepository.findAll();
    }
    
    /**
     * Fetching Size Bucket Ranges
     * @return List of Size Buckets for the shop
     */
    @RequestMapping(value = "/admin/sizeRange/getall", method = RequestMethod.GET)
    public List<SizeBucket> getAllSizeBuckets() {
        
        return (List<SizeBucket>) sizeBucketRepository.findAll();
    }
    
    /**
     * Synchronize data from Shopify into internal Database and Tin Eye
     * @param type - The type of synchronize (ex: artworks, addons, orders)
     * @param mode - The mode in which synchronize takes place (defaults to null/ partial
     * synchronize for update mode. 'full' performs a complete synchronize on the shop
     */
    @RequestMapping(value = "/api/shopify/synchronize", method = RequestMethod.GET)
    public void synchronize(
            @RequestParam(value = "type", required = true) String type,
            @RequestParam(value = "mode", required = false) String mode) {
        if (type.equalsIgnoreCase("artworks")) {
            LOGGER.info("Artworks Synchronize Started");
            artworkService.synchronize(mode);
            LOGGER.info("Artworks Successfully Synchronized");
        }
        else if (type.equalsIgnoreCase("orders")) {
            LOGGER.info("Orders Synchronize Started");
            ordersService.synchronize(mode);
            LOGGER.info("Orders Successfully Synchronized");
        }
        /**
         * Addons - Additional products that are purchased along with an artwork
         * currently frames and canvas are considered as addons. In future if 
         * there are new miscellaneous products coming up we can categorize them under this.
         */
        else if (type.equalsIgnoreCase("addons")) {
            LOGGER.info("Add-Ons Synchronize Started");
            artworkService.saveAddOnsModifiedSince(null);
            LOGGER.info("Add-Ons Successfully Synchronized");
        }
        
    }
    
    /**
     * Adding POST API requests for supporting WebHooks from Shopify that helps us
     * handle Auto-Synchronization, and Deletion of products on Shopify.
     * @param event - The event for which the webhook has triggered (ex: delete/update/create)
     * @param webHookResponse - The body content of the webhook
     * @param webHookUniqueKey - The Shopify Hmac Unique key for every unique event on shopify
     */
    @RequestMapping(value = "/api/shopify/webhook", method = RequestMethod.POST)
    public void shopifyWebHookhandling(
            @RequestParam(value = "event", required = true) String event,
            @RequestBody WebHookResponse webHookResponse,
            @RequestHeader(value = "X-Shopify-Hmac-Sha256") String webHookUniqueKey) {
        if (webHookUniqueKey == null) {
            return;
        }
        String productType = webHookResponse.getProductType() != null 
                ? webHookResponse.getProductType() : "orders";
        Long productId = webHookResponse.getId();
        if (!webHookExists(webHookUniqueKey, event, productType)) {
            if (event.equalsIgnoreCase("update") || event.equalsIgnoreCase("create")) {
                LOGGER.info("Web Hook Update Started");
                if (productType.equalsIgnoreCase("orders")) {
                    ordersService.synchronize(null);
                }
                else if (productType.equalsIgnoreCase("artworks") 
                        || productType.equalsIgnoreCase("frames") 
                        || productType.equalsIgnoreCase("canvas")) {
                    artworkService.synchronize(null);
                }
                LOGGER.info("Web Hook Update Completed");
            }
            else if (event.equalsIgnoreCase("delete")) {
                LOGGER.info("Web Hook Delete Started");
                    artworkService.deleteProduct(productId);
                LOGGER.info("Web Hook Delete Completed");
            }
        }
        
    }
    
    /**
     * Helper method to check if a requesting WebHook is already processed. Returns false
     * if it does not exist and creates a new WebHook entry into the DB by 
     * Shopify unique WebHook id else returns true.
     * @param webHookUniqueKey - The Shopify Hmac Unique key for every unique event on shopify
     * @param event - The event for which the webhook has triggered (ex: delete/update/create)
     * @param productType - The type of product to be synchronized (ex: artworks, frames, canvas)
     * @return
     */
    private boolean webHookExists(String webHookUniqueKey, String event, String productType) {
        int webHookCount = shopifyWebHookRepository.getWebHookCountByUniqueKey(webHookUniqueKey);
        if (webHookCount == 0) {
            ShopifyWebHook webHook = new ShopifyWebHook(DateTime.now(), 
                    webHookUniqueKey, productType, event);
            shopifyWebHookRepository.save(webHook);
            return false;
        }
        return true;
    }

    /**
     * Search Artworks based on criteria
     * @param subjects - List of Subject ids
     * @param styles - List of Style ids
     * @param colors - List of Colors for search (max 2)
     * @param priceBucketRange - Price Bucket Range ids 
     * @param medium - Medium(fine-art / canvas)
     * @param orientation - (Landscape / Potrait / Square)
     * @param sizeRange - (small / medium / large)
     * @param limit 
     * @param offset
     * @return
     */
    //CHECKSTYLE:OFF
    @RequestMapping(value = "/api/artworks/search", method = RequestMethod.GET)
    public Map<String, Object> getAllArtworksByCriteria(
            @RequestParam(value = "subjects", required = false) String[] subjects,
            @RequestParam(value = "styles", required = false) String[] styles,
            @RequestParam(value = "colors", required = false) String[] colors,
            @RequestParam(value = "priceBucketRange", required = false) String[] priceBucketRange,
            @RequestParam(value = "medium", required = false) String medium,
            @RequestParam(value = "orientation", required = false) String orientation,
            @RequestParam(value = "sizeRange", required = false) String sizeRange,
            @RequestParam(value = "limit", required = true) Integer limit,
            @RequestParam(value = "offset", required = true) Integer offset) {
    //CHECKSTYLE:ON
        List<String> subjectList = new ArrayList<>();
        List<String> styleList = new ArrayList<>();
        List<String> colorsList = new ArrayList<>();
        List<String> priceBucketRangeList = new ArrayList<>();
        List<String> sizeBucketRangeList = new ArrayList<>();
         subjectList.add("-1");
        styleList.add("-1");
        colorsList.add("");
        priceBucketRangeList.add("-1");
        sizeBucketRangeList.add("-1");
        subjectList = subjects != null ? Arrays.asList(subjects) : subjectList;
        styleList = styles != null ? Arrays.asList(styles) : styleList;
        colors = colors != null ? colors : null;
        medium = (medium !=  null) && (!medium.equalsIgnoreCase("")) ? medium : null;
        orientation = (orientation !=  null) && (!orientation.equalsIgnoreCase("")) ? orientation : null;
        priceBucketRangeList = priceBucketRange != null ? Arrays.asList(priceBucketRange) 
                : priceBucketRangeList;
        sizeBucketRangeList = sizeRange != null ? Arrays.asList(sizeRange) 
                : sizeBucketRangeList;
        CriteriaSearchResponse searchResponse = artworksSearchService.findArtworksByCriteria(
                subjectList, 
                styleList,
                colors,
                priceBucketRangeList,
                medium, 
                orientation,
                sizeBucketRangeList,
                limit,
                offset);
        
        return wrapResponse(searchResponse);
    }
    
    /**
     * Get artwork Id map for artworks in customer favorite collection.
     * @param customerId - Logged in customer id
     * @return
     */
    @RequestMapping(value = "/api/customer/favoriteArtworks", method = RequestMethod.GET)
    public Map<Long, Boolean> fetchArtworksRecomendedArtworks(
            @RequestParam(value = "customerId", required = true)Long customerId) {
        
        return shopifyService.getFavProductsMap(customerId);
    }
    
    /**
     * Search Tin Eye based on color Criteria 
     * @param colors - List of colors for color search
     * @param weights - Corresponding weights for each color
     * @return
     */
    @RequestMapping(value = "/api/artworks/search/color", method = RequestMethod.GET)
    public Map<String, Object> getAllArtworksByColor(@RequestParam(value = "colors") 
        String[] colors, @RequestParam(value = "weights", required = false) int[] weights) {
        CriteriaSearchResponse searchResponse = artworksSearchService.findArtworksByColor(colors, weights);
        return wrapResponse(searchResponse);
    }
    
    /**
     * Search For Subjects and Styles
     * @return
     */
    @RequestMapping(value = "/api/collections", method = RequestMethod.GET)
    public Map<String, Object> getAllSubjects() {
        List<ArtSubject> subjects = (List<ArtSubject>) artSubjectRepository.findAll();
        List<Subject> tempSubjects = new ArrayList<>();
        for (ArtSubject subj : subjects) {
            tempSubjects.add(new Subject(subj));
        }
        List<ArtStyle> styles = (List<ArtStyle>) artStyleRepository.findAll();
        List<Style> tempStyles = new ArrayList<>();
        for (ArtStyle style : styles) {
            tempStyles.add(new Style(style));
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("subjects", tempSubjects);
        map.put("styles", tempStyles);
        return map;
    }
    
    /**
     * Returns Frames that match the specified length, breadth, mount thickness 
     * and frame thickness. Used at Art Details page for fetching matching frames for an 
     * artwork variant
     * @param frameLength - Length of the particular frame required
     * @param frameBreadth - Breadth of the particular frame required
     * @param mountThickness - Mount Thickness for the frame
     * @param frameThickness - Frame Border thickness
     * @return
     */
    @RequestMapping(value = "/api/frames", method = RequestMethod.GET)
    public List<Frame> getFrames(
            @RequestParam(value = "frameLength", required = true) Double frameLength,
            @RequestParam(value = "frameBreadth", required = true) Double frameBreadth,
            @RequestParam(value = "mountThickness", required = true) Double mountThickness,
            @RequestParam(value = "frameThickness", required = true) Double frameThickness
            ) {
        List<Frame> tempFrames = new ArrayList<>();
        List<FrameVariant> frameVariants = frameVariantService.getFrames(mountThickness, frameThickness);
        for (FrameVariant frameVariant : frameVariants) {
            Frame frame = new Frame(frameVariant);
            frame.setFramePrice(frameVariantService.getFramePrice(frameLength, frameBreadth, frameVariant));
            tempFrames.add(frame);
        }
        return tempFrames;
    }
    
    /**
     * Returns Canvas price for a particular artwork variant.
     * Fetches unit price (per foot) for canvas from the canvas product as
     * canvas variant id
     * @param productVariantId - The product/artwork variant id for which the canvas is generated
     * @param canvasVariantId - The canvas variant id from which unit pricing and other details are 
     * fetched from
     * @return
     */
    @RequestMapping(value = "/api/canvas/price", method = RequestMethod.GET)
    public Double getCanvasPrice(
            @RequestParam(value = "productVariantId", required = true)Long productVariantId,
            @RequestParam(value = "canvasVariantId", required = true)Long canvasVariantId) {
        ArtworkVariant artworkVariant = artworkVariantRepository.findOne(productVariantId);
        if (artworkVariant != null) {
            ProductSize productSize = new ProductSize(artworkVariant);
            return frameVariantService.getCanvasPrice(productSize.getProductLength(), 
                    productSize.getProductBreadth(), canvasVariantId);
        }
        return null;
        
    }
    
    /**
     * API used for uploading an image as a Multipart File and then return colors 
     * extracted out of it from TinEye.
     * @param file - Image file to extract colors
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/api/uploadImage", headers = "content-type=multipart/*", method = RequestMethod.POST)
    @ResponseBody String extractColorsFromImage(@RequestParam("file") MultipartFile file) throws IOException {
        File tempFile = File.createTempFile("uploadedfile", null);
        file.transferTo(tempFile);
        String colors = tinEyeService.extractColorUploadImage(tempFile);
        return colors;
    }
    
    /**
     * API used for uploading an image as File Base 64 Data(String) and then return colors 
     * extracted out of it from TinEye.
     * @param fileBase64 - Image in Base 64 String format
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/api/uploadFileBase64", method = RequestMethod.POST)
    @ResponseBody String extractColorsFromBase64Image(@RequestParam("fileBase64") String fileBase64)
            throws IOException {
        String temp = fileBase64.split(",")[1].trim();
        byte imageBytes[] = Base64.decodeBase64(temp);
        File tempFile = File.createTempFile("uploadedfile", null);
        OutputStream output = null;
        try {
            output = new FileOutputStream(tempFile);
            IOUtils.write(imageBytes, output);
        }
        finally {
            IOUtils.closeQuietly(output);
        }
        String colors = tinEyeService.extractColorUploadImage(tempFile);
        return colors;
    }
    
    /**
     * API that accepts artwork variant id, type variant id, and type(Canvas/Frame)
     * and then creates a new product on Shopify store as type dynamic and having 
     * property hidden.
     * @param productVariantId - The product for which the add on is purchased
     * @param typeVariantId - The add on type variant id that is being purchased along with 
     * the artwork
     * @param type - The type of add on
     * @return
     */
    @RequestMapping(value = "/api/createProduct", method = RequestMethod.GET)
    public CustomCollection createDynamicProduct(
            @RequestParam(value = "productVariantId", required = true)Long productVariantId,
            @RequestParam(value = "typeVariantId", required = true)Long typeVariantId, 
            @RequestParam(value = "type", required = true)String type) {
        
            return frameVariantService.createAddOnProduct(productVariantId, 
                    typeVariantId, type);
        
    }
    
    /*
     * API that returns all preferences for the shop, and the preferences that 
     * a particular customer has checked.
     */
    @RequestMapping(value = "/api/customer/preferences", method = RequestMethod.GET)
    public Map<String, Object> getCustomerPreferences(
            @RequestParam(value = "customerId", required = true)Long customerId) {
        Map<String, Object> shopPreferences = preferenceService.getPreferencesForShop();
        Map<String, Object> customerPreferences = preferenceService.getPreferencesForUser(customerId);
        Map<String, Object> preferences = new HashMap<>();
        preferences.put("shopPreferences", shopPreferences);
        preferences.put("customerPreferences", customerPreferences);
        
        return preferences;
    }
    
    /**
     * API accepts preferences for a particular customer and updates on the 
     * corresponding metafields using Shopify API's
     * @param customerId - The id of the logged in customer
     * @param subjects - List of subjects that he prefers
     * @param styles - List of styles that he prefers
     * @param mediums - The medium that he prefers
     * @param orientations - The orientation that he prefers
     * @return
     */
    @RequestMapping(value = "/api/customer/preferences", method = RequestMethod.POST)
    public Boolean modifyCustomerPreferences(
            @RequestParam(value = "customerId", required = true)Long customerId,
            @RequestParam(value = "subjects", required = true)String[] subjects,
            @RequestParam(value = "styles", required = true)String[] styles,
            @RequestParam(value = "mediums", required = true)String[] mediums,
            @RequestParam(value = "orientations", required = true)String[] orientations) {
        return preferenceService.updatePreferencesForUser(customerId, 
                subjects, styles, mediums, orientations);
    }
    
    /**
     * API that returns a Map of Collection/Artist Id's that a customer is following
     * @param customerId - Logged in customer id
     * @return
     */
    @RequestMapping(value = "/api/customer/followings", method = RequestMethod.GET)
    public Map<String, Object> getCustomerFollowings(
            @RequestParam(value = "customerId", required = true)Long customerId) {
        Map<String, Object> customerFollowings = followingService.getFollowingsForUser(customerId);
        Map<String, Object> followings = new HashMap<>();
        followings.put("customerFollowings", customerFollowings);
        return followings;
    }
    
    /**
     * API that accepts followings data and updates the followings of a particular
     * customer
     * @param customerId Logged in Customer id
     * @param subjects - List of subjects that the customer follows
     * @param styles - List of styles that the customer follows
     * @param collections - List of collections that the customer follows
     * @param artists - List of artists that a customer follows
     * @return
     */
    @RequestMapping(value = "/api/customer/followings", method = RequestMethod.POST)
    public Boolean modifyCustomerFollowings(
            @RequestParam(value = "customerId", required = true)Long customerId,
            @RequestParam(value = "subjects", required = true)String[] subjects,
            @RequestParam(value = "styles", required = true)String[] styles,
            @RequestParam(value = "collections", required = true)String[] collections,
            @RequestParam(value = "artists", required = true)String[] artists) {
        return followingService.updateFollowingsForUser(customerId, 
                subjects, styles, collections, artists);
    }
    
    /**
     * API that accepts a Customer Id, Collection Type and a Collection Id and returns true if that 
     * collection is followed by the customer else returns false
     * @param customerId - Logged in Customer id
     * @param collectionId -  Viewing Collection id
     * @param collectionType - Viewing Collection type
     * @return
     */
    @RequestMapping(value = "/api/customer/following/collection", method = RequestMethod.GET)
    public Boolean getCustomerFollowingCollection(
            @RequestParam(value = "customerId", required = true)Long customerId, 
            @RequestParam(value = "collectionId", required = true)Long collectionId,
            @RequestParam(value = "collectionType", required = true)String collectionType) {
        
        return followingService.isCollectionFollowedByCustomer(customerId, collectionId, collectionType);
    };
    
    /**
     * API that accepts a Customer Id, Collection Type, Collection Id, and a Set Follow parameter.
     * The Collection is added to the Customers' followings if the Set Follow parameter is true, 
     * else the collection is removed from the customers' followings
     * @param customerId - Logged in Customer id
     * @param collectionId - Following/Unfollowing Collection id
     * @param collectionType - Following/Unfollowing Collection type
     * @param setfollow - set collection to follow(true) and unfollow(flase)
     * @return
     */
    @RequestMapping(value = "/api/customer/following/collection", method = RequestMethod.POST)
    public Integer updateCustomerFollowingCollection(
            @RequestParam(value = "customerId", required = true)Long customerId, 
            @RequestParam(value = "collectionId", required = true)Long collectionId,
            @RequestParam(value = "collectionType", required = true)String collectionType,
            @RequestParam(value = "setFollow", required = true)Boolean setfollow) {
        
        return followingService.toggleCollectionFollowedByCustomer(customerId, 
                collectionId, collectionType, setfollow);
    };
    
    /**
     * Get Recommended artworks for a particular customer paged by limit and offset and 
     * offset specified 
     * @param customerId - Logged in customer id
     * @param limit
     * @param offset
     * @return
     */
    @RequestMapping(value = "/api/customer/recomended", method = RequestMethod.GET)
    public Map<String, Object> fetchArtworksRecomendedArtworks(
            @RequestParam(value = "customerId", required = true)Long customerId,
            @RequestParam(value = "limit", required = true) Integer limit,
            @RequestParam(value = "offset", required = true) Integer offset) {
        CriteriaSearchResponse searchResponse = preferenceService
                .getRecomendedArtworksForCustomer(customerId, limit, offset);
           
        return wrapResponse(searchResponse);
    }
    
    /**
     * API that accepts a Customer Id and a Artwork Id. Updates the Favorites of the 
     * customer based on the isLiked boolean value
     * @param customerId - Logged in customer id
     * @param productId - Product that the customer is willing to Like/Unlike
     * @param isLiked - True/False
     */
    @RequestMapping(value = "/api/customer/favorites", method = RequestMethod.POST)
    public void customerCustomCollection(
            @RequestParam(value = "customerId", required = true)Long customerId, 
            @RequestParam(value = "productId", required = true)Long productId,
            @RequestParam(value = "isLiked", required = true)Boolean isLiked) {
        
        shopifyService.updateFavoritesCollection(customerId, productId, isLiked);
        return;
    };
                        
    /**
     * Wrap Artwork objects into a Map Helper Function 
     * @param searchResponse - SearchResponse object with valid search response. Converts to map
     * @return - Returns valid map for rendering on to HTML pages
     */
    private Map<String, Object> wrapResponse(CriteriaSearchResponse searchResponse) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<Object> artworksList = new ArrayList<>();
        if (searchResponse != null) {
            List<ArtWork> artworks = searchResponse.getArtworks();
            for (ArtWork art : artworks) {
                Map<String, Object> artworkMap = new HashMap<String, Object>();
                artworkMap.put("images", art.getImages());
                artworkMap.put("priceBuckets", art.getPriceBuckets());
                artworkMap.put("details", art);
                artworkMap.put("artist", art.getArtist());
                artworksList.add(artworkMap);
            }
            map.put("searchCount", searchResponse.getTotalArtworkCount());
        }
        Object allArtworksCount = artWorkRepository.count();
        map.put("allArtworksCount", allArtworksCount);
        map.put("artworks", artworksList);
        return map;
    }
}
