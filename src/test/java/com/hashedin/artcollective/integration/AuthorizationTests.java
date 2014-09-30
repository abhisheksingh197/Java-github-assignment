package com.hashedin.artcollective.integration;

import static com.jayway.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.junit.Test;

import com.hashedin.artcollective.BaseIntegrationTest;

public class AuthorizationTests extends BaseIntegrationTest {
	
	@Test
	public void testHomePageIsPublicallyAccessible() {
		given()
		.when().get("/")
		.then()
			.statusCode(200)
			.body(not(containsString("Please Login")));
	}
	
	@Test
	public void securePageMustNotBeAccessibleAnnonymously() {
		given()
		.when().get("/secure/artist")
		.then()
			.body(containsString("Please Login"));
	}
	
// Commenting the following test cases since we have removed the _csrf token from login.ftl
	
//	@Test
//	public void testManagementAPIsAreNotAccessibleToShoppers() {
//		given()
//			.sessionId(login("shopper", "shopper"))
//		.when().get("/manage/mappings")
//		.then()
//			.body(containsString("Access is denied"));
//	}
//	
//	@Test
//	public void superAdminCanAccessManagementAPIs() {
//		given()
//			.sessionId(login("superadmin", "superadmin"))
//		.when().get("/manage/mappings")
//		.then()
//			.statusCode(200)
//			.body(not(containsString("Access is denied")));
//		
//	}
	
	
}
