package com.hashedin.artcollective;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.hashedin.artcollective.service.ArtWorksServiceTest;
import com.hashedin.artcollective.service.ShopifyServiceTest;
import com.hashedin.artcollective.service.TinEyeServiceImpTest;

@RunWith(Suite.class)
@SuiteClasses({ 
	ArtWorksServiceTest.class,
	ShopifyServiceTest.class,
	TinEyeServiceImpTest.class
})
public class UnitTests {

}
