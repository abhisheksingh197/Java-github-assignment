package com.hashedin.artcollective.service;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;


import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import com.hashedin.artcollective.BaseUnitTest;
import com.hashedin.artcollective.entity.OrderLineItem;
import com.hashedin.artcollective.repository.FulfilledOrderRepository;
import com.hashedin.artcollective.repository.OrderLineItemRepository;
import com.hashedin.artcollective.repository.SynchronizeLogRepository;
import com.hashedin.artcollective.utils.SynchronizeSetup;

public class OrdersServiceTest extends BaseUnitTest{
	
	private static boolean isInitialized = false;
	
	@Value("${shopify.baseurl}")
	private String shopifyBaseUrl;
	
	@Autowired
	private RestTemplate rest;

	@Autowired
	private OrdersService ordersService;

	@Autowired
	private ShopifyService shopifyService;
	
	@Autowired
	private SynchronizeLogRepository syncLogRepository;
	
	@Autowired
	private OrderLineItemRepository orderLineItemRepository;
	
	@Autowired
	private FulfilledOrderRepository fulfilledOrderRepository;
	
	@Autowired
	private SynchronizeSetup synchronizeSetup;
	
	@Before
	public void setup() {
		if(isInitialized) {
			return;
		}
		
		synchronizeSetup.artworksSynchronizeSetup();
		
		MockRestServiceServer mockArtWorksService = MockRestServiceServer
				.createServer(rest);

		mockArtWorksService.expect(requestTo(shopifyBaseUrl + "orders/count.json?fulfillment_status=shipped"))
		.andExpect(method(HttpMethod.GET))
		.andRespond(shopifyOrdersCount(5));
		
		mockArtWorksService.expect(requestTo(shopifyBaseUrl + "orders.json?fulfillment_status=shipped&limit=100&page=1"))
		.andExpect(method(HttpMethod.GET))
		.andRespond(withJson("orders.json"));
		
		ordersService.synchronize(null);
		
		isInitialized = true;
		
	}
	
	
	@Test
	public void testForModifiedDateFromSyncLog() {
		
		
		
		assertEquals(fulfilledOrderRepository.count(), 3);
		assertEquals(orderLineItemRepository.count(), 6);
			
	}
	
	@Test
	public void testToVerifyLineItemHasValidOrderAndArtist() {
		OrderLineItem orderLineItem = orderLineItemRepository.findOne(494138839L);
		long lineItemOrderId = orderLineItem.getOrder().getId();
		assertEquals(lineItemOrderId, 274636103L);
		
		long lineItemOrderProductId = orderLineItem.getProductId();
		assertEquals(lineItemOrderProductId, 504096747L);
		
		long artistId = orderLineItem.getArtistId();
		assertEquals(artistId, 2L);
		
	}
}
