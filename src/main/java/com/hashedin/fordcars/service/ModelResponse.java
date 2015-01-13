package com.hashedin.fordcars.service;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;

@JsonIgnoreProperties(ignoreUnknown = true)
class ModelResponse {
	private Response response;

	public Response getResponse() {
		return response;
	}

	@JsonSetter("Response")
	public void setResponse(Response response) {
		this.response = response;
	} 
}

@JsonIgnoreProperties(ignoreUnknown = true)
class Response {
	private int ttl;
	private List<Model> models;
	
	public int getTtl() {
		return ttl;
	}
	public void setTtl(int ttl) {
		this.ttl = ttl;
	}
	public List<Model> getModels() {
		return models;
	}
	
	@JsonSetter("Model")
	public void setModels(List<Model> models) {
		this.models = models;
	}
}

@JsonIgnoreProperties(ignoreUnknown = true) 
class Model {
	private String make;
	private String modelName;
	private String vehicleType;
	private int year;
	
	private Pricing pricing;
	
	public String getMake() {
		return make;
	}
	@JsonSetter("Make")
	public void setMake(String make) {
		this.make = make;
	}
	public String getModelName() {
		return modelName;
	}
	
	@JsonSetter("ModelName")
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	public String getVehicleType() {
		return vehicleType;
	}
	@JsonSetter("VehicleType")
	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}
	public int getYear() {
		return year;
	}
	@JsonSetter("Year")
	public void setYear(int year) {
		this.year = year;
	}
	public Pricing getPricing() {
		return pricing;
	}
	
	@JsonSetter("Pricing")
	public void setPricing(Pricing pricing) {
		this.pricing = pricing;
	}
}

@JsonIgnoreProperties(ignoreUnknown = true)
class Pricing {
	private PriceObject high;
	private PriceObject low;
	public PriceObject getHigh() {
		return high;
	}
	@JsonSetter("High")
	public void setHigh(PriceObject high) {
		this.high = high;
	}
	public PriceObject getLow() {
		return low;
	}
	@JsonSetter("Low")
	public void setLow(PriceObject low) {
		this.low = low;
	}
	
}

@JsonIgnoreProperties(ignoreUnknown = true)
class PriceObject {
	private int baseMSRP;

	public int getBaseMSRP() {
		return baseMSRP;
	}

	@JsonSetter("BaseMSRP")
	public void setBaseMSRP(int baseMSRP) {
		this.baseMSRP = baseMSRP;
	}
	
}