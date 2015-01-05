package com.hashedin.javatemplate.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hashedin.javatemplate.entity.SuperMarket;
import com.hashedin.javatemplate.repository.SuperMarketRepository;

@Service
public class SuperMarketService {
	
	@Autowired
	private SuperMarketRepository superMarketRepository;

	public Long getTotalCount() {
		return superMarketRepository.count();
	}
	
	public List<SuperMarket> fetchAllSuperMarkets() {
		return (List<SuperMarket>) superMarketRepository.findAll();
	}

	public SuperMarket createNewSuperMarket(SuperMarket superMarketObject) {
		return superMarketRepository.save(superMarketObject);
	}
}