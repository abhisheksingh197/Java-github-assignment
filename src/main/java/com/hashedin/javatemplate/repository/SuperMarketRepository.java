package com.hashedin.javatemplate.repository;

import org.springframework.data.repository.CrudRepository;

import com.hashedin.javatemplate.entity.SuperMarket;

public interface SuperMarketRepository extends CrudRepository<SuperMarket, Long> {
	
}
