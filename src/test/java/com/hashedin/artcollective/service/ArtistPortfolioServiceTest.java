package com.hashedin.artcollective.service;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

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
		List<OrderLineItem> orderLineItems = orderLineItemRepository.getOrderLineItemsByArtistId(2L);
		assertEquals(orderLineItems.size(),3);
	}
	
	@Test
	public void testForCreatingPortfolioLineItems() {
		Map<Long, PortfolioLineItem> portfolioItems = artistPortfolioService.getArtworksForPortfoilio(2L);
		assertEquals(portfolioItems.size(),1);
		PortfolioLineItem portfolioLineItem = portfolioItems.get(504096747L);
		assertEquals(portfolioLineItem.getOrderLineItems().size(), 3);
		portfolioItems = artistPortfolioService.getArtworksForPortfoilio(1L);
		portfolioLineItem = portfolioItems.get(505096747L);
		assertEquals(portfolioLineItem.getOrderLineItems().size(), 1);
		portfolioLineItem = portfolioItems.get(506096747L);
		assertEquals(portfolioLineItem.getOrderLineItems().size(), 1);
		assertEquals(portfolioItems.size(),2);
	}
	
}
