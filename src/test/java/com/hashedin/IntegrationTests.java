package com.hashedin;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.hashedin.integration.AuthorizationTests;
import com.hashedin.integration.DebugModeTests;

@RunWith(Suite.class)
@SuiteClasses({ 
	AuthorizationTests.class,
	DebugModeTests.class,
})
public class IntegrationTests {

}
