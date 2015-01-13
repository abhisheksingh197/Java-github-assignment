package com.hashedin.fordcars.controller;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hashedin.fordcars.entity.SuperMarket;
import com.hashedin.fordcars.service.CustomCollection;
import com.hashedin.fordcars.service.SuperMarketService;

@RestController
public class RestAPIController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RestAPIController.class);
    
    @Autowired
    private SuperMarketService superMarketService;
    
    /**
     * Add SuperMarket
     * @param name
     * @param city
     * @param webAddress
     * @return 
     * @return SuperMarket
     * 
     */
    @RequestMapping(value = "/admin/supermarkets.json", method = RequestMethod.POST,
    		headers = {"content-type=application/json" })
    @ResponseBody
    public  SuperMarket createSuperMarket(
    		@RequestBody SuperMarket superMarketObject) {
    	SuperMarket superMarket = superMarketService.createNewSuperMarket(superMarketObject);
        LOGGER.info(String.format("Super Market: %s Successfully Added", superMarket));
        return superMarket;
    }
    
    /**
     * Get All SuperMarkets
     * @return List<SuperMarket>
     */
    @RequestMapping(value = "/admin/supermarkets.json", method = RequestMethod.GET)
    public List<SuperMarket> getAllSuperMarket() {
        return superMarketService.fetchAllSuperMarkets();
    }
    
    @RequestMapping(value = "/secure/supermarkets.json", method = RequestMethod.GET)
    public List<SuperMarket> getAllSuperMarketsss() {
        return superMarketService.fetchAllSuperMarkets();
    }
    
    @RequestMapping(value = "/secure/products.json", method = RequestMethod.GET)
    public List<CustomCollection> getAllProducts() {
    	return superMarketService.getAllProductsModifiedSince(null);
    }
}
