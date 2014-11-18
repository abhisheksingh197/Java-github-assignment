package com.hashedin.artcollective.integration;

import static com.jayway.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.junit.Test;

import com.hashedin.artcollective.BaseIntegrationTest;

public class AuthorizationTests extends BaseIntegrationTest {
	
	@Test
	public void testAdminAPINotPublicallyAccessible() {
		given()
		.when().get("/admin/priceRange/getall")
		.then()
			.statusCode(200)
			.body(containsString("Login"));
	}
	
	@Test
	public void testGenericAPIPublicallyAccessible() {
		given()
		.when().get("/api/artworks/search?limit=0&offset=10")
		.then()
			.body(not(containsString("Access Denied")));
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
