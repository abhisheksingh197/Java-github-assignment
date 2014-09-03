package com.hashedin.artcollective;

import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.client.response.DefaultResponseCreator;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Main.class)
@ActiveProfiles("test")
public class BaseUnitTest {

	@Autowired
	private ResourceLoader loader;
	
	public DefaultResponseCreator withJson(String jsonFile) {
		return withSuccess(loader.getResource(jsonFile), MediaType.APPLICATION_JSON);
	}
	
}
