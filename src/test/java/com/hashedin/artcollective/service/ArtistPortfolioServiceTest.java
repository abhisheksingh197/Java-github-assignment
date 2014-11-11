package com.hashedin.artcollective.service;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.hashedin.artcollective.BaseUnitTest;
import com.hashedin.artcollective.entity.OrderLineItem;
import com.hashedin.artcollective.repository.OrderLineItemRepository;
import com.hashedin.artcollective.utils.SynchronizeSetup;

public class ArtistPortfolioServiceTest extends BaseUnitTest{

	private static boolean isInitialized = false;
	
	@Autowired
	private SynchronizeSetup synchronizeSetup;
	
	@Autowired
	private ArtistPortfolioService artistPortfolioService;
	
	@Autowired
	private OrderLineItemRepository orderLineItemRepository;
	
	@Before
	public void setup() {
		
		if (isInitialized) {
			return;
		}
		synchronizeSetup.artworksSynchronizeSetup();
		synchronizeSetup.ordersSynchronizeSetup();
		
		isInitialized = true;
	}
	
	@Test
	public void testForFetchingOrderLineItemsByArtistId() {
		Map<String, Double> orderLineItems = artistPortfolioService.getLineItemsForPortfoilio(2L);
		assertEquals(orderLineItems.size(),2);
	}
	
	@Test
	public void testForCreatingPortfolioLineItems() {
		ModelAndView model = artistPortfolioService.getPortfolio(2L);
		assertEquals(model.getViewName(), "artist-dashboard");
	}
	
}
