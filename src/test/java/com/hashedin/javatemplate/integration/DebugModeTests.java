package com.hashedin.javatemplate.integration;

import static com.jayway.restassured.RestAssured.given;

import org.junit.Test;

import com.hashedin.javatemplate.BaseIntegrationTest;
import com.jayway.restassured.http.ContentType;

public class DebugModeTests extends BaseIntegrationTest {

	/*
	 * When ?showModel=true is appended to a url,
	 * we expect a JSON response with the data model for that view
	 */
	@Test
	public void testShowModelPrintsJson() {
		given()
		.when().get("/")
		.then().contentType(ContentType.HTML);
		
		given()
		.when().get("/?showModel=true")
		.then().contentType(ContentType.JSON);
		
		
	}
}
