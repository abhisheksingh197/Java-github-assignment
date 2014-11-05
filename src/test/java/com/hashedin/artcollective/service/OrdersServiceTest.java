package com.hashedin.artcollective.service;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import com.hashedin.artcollective.BaseUnitTest;
import com.hashedin.artcollective.repository.SynchronizeLogRepository;

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
	
	@Before
	public void setup() {
		if(isInitialized) {
			return;
		}
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
	
	@Ignore
	@Test
	public void testForModifiedDateFromSyncLog() {
		
		MockRestServiceServer mockArtWorksService = MockRestServiceServer
				.createServer(rest);
		
		String lastModified = syncLogRepository.getLastSynchronizeDate("orders").toString();
		
		mockArtWorksService.expect(requestTo(shopifyBaseUrl + "orders/count.json?fulfillment_status=shipped&updated_at_min="
				.concat(lastModified)))
		.andExpect(method(HttpMethod.GET))
		.andRespond(shopifyOrdersCount(5));
		
		mockArtWorksService.expect(requestTo(shopifyBaseUrl + "orders.json?fulfillment_status=shipped"
				.concat(lastModified).concat("&limit=100&page=1")))
		.andExpect(method(HttpMethod.GET))
		.andRespond(withJson("orders.json"));
		
		List<Order> orders = ordersService.synchronize(null);
		
		assertEquals(orders.size(), 3);
		
		List<OrderFulfillment> fulfillments = orders.get(0).getFulfillments();
		long fulfillmentId = fulfillments.get(0).getId();
		assertEquals(fulfillmentId, 172462467);
		
		List<LineItem> lineItems = fulfillments.get(0).getOrderLineItems();
		String lineItemVariantTitle = lineItems.get(1).getVariantTitle();
		assertEquals(lineItemVariantTitle, "15x11 / 1 / 1");
		
	}
}
