package com.hashedin.artcollective.service;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.servlet.ModelAndView;

import com.hashedin.artcollective.BaseUnitTest;
import com.hashedin.artcollective.entity.Artist;
import com.hashedin.artcollective.entity.Deduction;
import com.hashedin.artcollective.entity.OrderLineItem;
import com.hashedin.artcollective.entity.Transaction;
import com.hashedin.artcollective.repository.ArtistRepository;
import com.hashedin.artcollective.repository.DeductionRepository;
import com.hashedin.artcollective.repository.OrderLineItemRepository;
import com.hashedin.artcollective.repository.TransactionRepository;
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
	
	@Autowired
	private DeductionRepository deductionsRepository;
	
	@Autowired 
	private TransactionRepository TransactionRepository;
	
	@Autowired
	private DeductionsService deductionsService;
	
	@Autowired
	private TransactionsService transactionsService;
	
	@Before
	public void setup() {
		
		if (isInitialized) {
			return;
		}
		synchronizeSetup.artworksSynchronizeSetup();
		synchronizeSetup.ordersSynchronizeSetup();
		deductionsService.addNewDeduction(new DateTime(), "Photography", 504096747L, 
				2L, 100.00, 200.00);
		deductionsService.addNewDeduction(new DateTime(), "Discount", 504096747L, 
				null, null, -200.00);
		deductionsService.addNewDeduction(new DateTime(), "Photography", 506096747L, 
				2L, 100.00, 200.00);
		deductionsService.addNewDeduction(new DateTime(), "Photography", 509096747L, 
				2L, 100.00, 200.00);
		transactionsService.addNewTransaction(new DateTime(), 1L, "For 3 Artworks", 500.00);
		transactionsService.addNewTransaction(new DateTime(), 1L, "For 5 Artworks", 800.00);
		transactionsService.addNewTransaction(new DateTime(), 2L, "For 4 Artworks", 600.00);
		
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
		List<PortfolioEarnings> earnings = artistPortfolioService.getEarningsByArtist(2L);
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
		assertEquals(payouts, 600.0, 0.1);
		double pending = dashboardValues.get("pending");
		assertEquals(pending, 5000.00, 0.1);
	}
	
	
	@Test
	public void testForFetchingDeductionsForPortfolio() {
		List<Deduction> deductions = artistPortfolioService.getDeductionsByArtist(2L);
		assertEquals(deductions.size(), 2);
		double totalDeduction = deductions.get(0).getTotalDeduction(); 
		assertEquals(totalDeduction, -200.00, 0.1);
	}
	
	@Test
	public void testForFetchingTransactionsForPortfolio() {
		List<Transaction> transactions = artistPortfolioService.getTransactionsByArtist(1L);
		assertEquals(transactions.size(), 2);
		double amount = transactions.get(0).getAmount();
		assertEquals(amount, 800.00, 0.1);
		
	}
	
	@Test
	public void testForFetchingArtworkImagesMap() {
		Map<String, String> artworksImageMap = artistPortfolioService.getArtworkImagesByArtist(2L);
		assertEquals(artworksImageMap.size(), 2);
		assertEquals(artworksImageMap.get("504096747"), "abc");
	}
	
}
