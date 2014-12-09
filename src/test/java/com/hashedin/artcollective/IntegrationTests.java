package com.hashedin.artcollective;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.hashedin.artcollective.integration.AuthorizationTests;
import com.hashedin.artcollective.utils.EmailHelperTest;

@RunWith(Suite.class)
@SuiteClasses({ 
	AuthorizationTests.class,
	EmailHelperTest.class,
})
public class IntegrationTests {

}
