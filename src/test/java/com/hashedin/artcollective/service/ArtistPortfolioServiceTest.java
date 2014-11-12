package com.hashedin.artcollective.service;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.servlet.ModelAndView;

import com.hashedin.artcollective.BaseUnitTest;
import com.hashedin.artcollective.entity.Artist;
import com.hashedin.artcollective.entity.OrderLineItem;
import com.hashedin.artcollective.repository.ArtistRepository;
import com.hashedin.artcollective.repository.OrderLineItemRepository;
import com.hashedin.artcollective.repository.PortfolioEarnings;
import com.hashedin.artcollective.utils.SynchronizeSetup;

public class ArtistPortfolioServiceTest extends BaseUnitTest{

	private static boolean isInitialized = false;
	
	@Autowired
	private ArtistRepository repository;
	
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
	public void testFindByUsername() {
		Artist artist = new Artist();
		artist.setUsername("johndoe");
		artist.setPassword("supersecret");
		artist.setHandle("john-doe");
		artist.setEmail("john.doe@gmail.com");
		artist.setCollectionId(32L);
		artist.setContactNumber("333-444-5555");
		
		repository.save(artist);
		
		UserDetails loadedUser = artistPortfolioService.loadUserByUsername("johndoe");
		assertEquals(loadedUser.getPassword(), artist.getPassword());
		assertEquals(loadedUser.getUsername(), artist.getUsername());
	}
	
	@Test(expected = UsernameNotFoundException.class)
	public void testFindByUsernameThatDoesNotExist() {
		artistPortfolioService.loadUserByUsername("smartalec");		
	}
	
	@Test
	public void testForFetchinEarningsForPortfolio() {
		List<PortfolioEarnings> earnings = artistPortfolioService.getEarningsForArtist(2L);
		assertEquals(earnings.size(), 4);
		PortfolioEarnings earning = earnings.get(0);
		assertEquals(earning.getOrderName(), "AC-WOD1011");
		double commission = earning.getCommission();
		assertEquals(commission, 800, 0.1);
		long orderId = earning.getOrderId();
		assertEquals(orderId, 274636103L);
		long quantity = earning.getQuantity();
		assertEquals(quantity, 1L);
		assertEquals(earning.getVariantSize(), "12\" x 16\"");
		
	}
	
	@Test
	public void testForFetchingDashboardValuesForPortfolio() {
		Map<String, Double> dashboardValues = artistPortfolioService.getDashboardValues(2L);
		double totalEarningsAsCommission = dashboardValues.get("totalEarningsAsCommission");
		assertEquals(totalEarningsAsCommission, 5600, 0.1);
		double totalDeductions = dashboardValues.get("totalDeductions");
		assertEquals(totalDeductions, 0.0, 0.1);
		double netCommission = dashboardValues.get("netCommission");
		assertEquals(netCommission, 5600.0, 0.1);
		double payouts = dashboardValues.get("payouts");
		assertEquals(payouts, 0.0, 0.1);
		double pending = dashboardValues.get("pending");
		assertEquals(pending, 5600.00, 0.1);
	}
	
}
