package com.hashedin.artcollective;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.hashedin.artcollective.integration.AuthorizationTests;
import com.hashedin.artcollective.service.ArtWorksServiceMockitoTest;
import com.hashedin.artcollective.service.ArtWorksServiceTest;
import com.hashedin.artcollective.service.ArtistPortfolioServiceTest;
import com.hashedin.artcollective.service.FrameVariantServiceTest;
import com.hashedin.artcollective.service.OrdersServiceTest;
import com.hashedin.artcollective.service.PriceBucketServiceTest;
import com.hashedin.artcollective.service.ProductsAPITest;
import com.hashedin.artcollective.service.ShopifyServiceTest;
import com.hashedin.artcollective.service.TinEyeServiceImpTest;

@RunWith(Suite.class)
@SuiteClasses({ 
	ProductsAPITest.class,
	ArtWorksServiceTest.class,
	ShopifyServiceTest.class,
	TinEyeServiceImpTest.class,
	AuthorizationTests.class,
	PriceBucketServiceTest.class,
	ArtWorksServiceMockitoTest.class,
	FrameVariantServiceTest.class,
	OrdersServiceTest.class,
	ArtistPortfolioServiceTest.class
})
public class UnitTests {

}
