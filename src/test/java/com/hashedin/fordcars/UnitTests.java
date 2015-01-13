package com.hashedin.fordcars;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.hashedin.fordcars.service.SuperMarketServiceTest;

@RunWith(Suite.class)
@SuiteClasses({ 
	SuperMarketServiceTest.class
})
public class UnitTests {

}
