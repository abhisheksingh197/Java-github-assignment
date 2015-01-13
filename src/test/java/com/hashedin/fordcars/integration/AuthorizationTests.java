package com.hashedin.fordcars.integration;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;

import org.junit.Test;

import com.hashedin.fordcars.BaseIntegrationTest;

public class AuthorizationTests extends BaseIntegrationTest {
	
	private static final Integer STATUS_CODE = 200;
	
	@Test
	public void testAdminAPINotPublicallyAccessible() {
		given()
		.when().get("/admin/supermarkets.json")
		.then()
			.statusCode(STATUS_CODE)
			.body(not(containsString("Login")));
	}
}
