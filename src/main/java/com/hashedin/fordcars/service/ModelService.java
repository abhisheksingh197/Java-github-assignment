package com.hashedin.fordcars.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ModelService {

	static final String MODELS_URL = "http://services.forddirect.fordvehicles.com/products/ModelSlices.json";
	
	@Autowired
	private RestTemplate restTemplate;
	
	public List<ModelEntity> getAllModels() {
		ModelResponse response = restTemplate.getForObject(MODELS_URL, ModelResponse.class);
		List<Model> models = response.getResponse().getModels();
		
		List<ModelEntity> entities = new ArrayList<>();
		for (Model model : models) {
			ModelEntity entity = new ModelEntity();
			entity.setMake(model.getMake());
			entity.setModelName(model.getModelName());
			entity.setYear(model.getYear());
			entity.setVehicleType(model.getVehicleType());
			
			entity.setHighPrice(model.getPricing().getHigh().getBaseMSRP());
			entity.setLowPrice(model.getPricing().getLow().getBaseMSRP());
			entities.add(entity);
		}
		return entities;
	}

}
