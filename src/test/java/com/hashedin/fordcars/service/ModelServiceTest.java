package com.hashedin.fordcars.service;

import static org.junit.Assert.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import com.hashedin.fordcars.BaseUnitTest;

public class ModelServiceTest extends BaseUnitTest {

	@Autowired
	private ModelService service;
	
	@Autowired
	private RestTemplate rest;
	
	@Before
	public void setUp() {
		
	}
	
	@Test
	public void testGetAllModelsIsNotEmpty() {
		MockRestServiceServer mockFord = MockRestServiceServer.createServer(rest);
		mockFord.expect(requestTo(ModelService.MODELS_URL))
			.andExpect(method(HttpMethod.GET))
			.andRespond(withJson("ModelSlices.json"));
		
		List<ModelEntity> models = service.getAllModels();
		assertEquals(models.size(), 2);
		ModelEntity firstEntity = models.get(0);
		assertEquals(firstEntity.getModelName(), "Escape Hybrid");
		assertEquals(firstEntity.getHighPrice(), 32323);
		assertEquals(firstEntity.getLowPrice(), 30570);
		
		ModelEntity lastEntity = models.get(1);
		assertEquals(lastEntity.getModelName(), "Econoline Wagon");
				
	}

}
