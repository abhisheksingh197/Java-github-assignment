package com.hashedin.javatemplate;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.hashedin.javatemplate.integration.AuthorizationTests;

@RunWith(Suite.class)
@SuiteClasses({ 
	AuthorizationTests.class
})
public class IntegrationTests {

}
