package com.hashedin.artcollective;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.hashedin.artcollective.integration.AuthorizationTests;

@RunWith(Suite.class)
@SuiteClasses({ 
	AuthorizationTests.class,
})
public class IntegrationTests {

}
