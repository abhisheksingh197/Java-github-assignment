package com.hashedin.fordcars.service;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hashedin.fordcars.BaseUnitTest;

public class ModelServiceTest extends BaseUnitTest {

	@Autowired
	private ModelService service;
	
	@Test
	public void testGetAllModelsIsNotEmpty() {
		List<ModelEntity> models = service.getAllModels();
		assertEquals(models.size(), 257);
		ModelEntity firstEntity = models.get(0);
		assertEquals(firstEntity.getModelName(), "Escape Hybrid");
		
		ModelEntity lastEntity = models.get(256);
		assertEquals(lastEntity.getModelName(), "Econoline Wagon");
				
	}

}
