package com.hashedin.javatemplate.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hashedin.javatemplate.BaseUnitTest;
import com.hashedin.javatemplate.entity.SuperMarket;

public class SuperMarketServiceTest extends BaseUnitTest {
	
	@Autowired
	private SuperMarketService superMarketService;
	
	@Test
	public void testForCreatingNewSuperMarket() {
		SuperMarket superMarketObject = new SuperMarket("Food World", "Bangalore", "www.foodworld.com");
		superMarketService.createNewSuperMarket(superMarketObject);
		long superMarketsCount = superMarketService.getTotalCount();
		assertEquals(1, superMarketsCount);
		List<SuperMarket> superMarkets = superMarketService.fetchAllSuperMarkets();
		assertEquals(superMarketsCount, superMarkets.size());
		SuperMarket superMarket = superMarkets.get((int) (superMarketsCount - 1));
		assertEquals("Food World", superMarket.getName());
	}
}
