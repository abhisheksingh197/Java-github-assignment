package com.hashedin.artcollective.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.hashedin.artcollective.entity.ShopifyWebHook;

public interface ShopifyWebHookRepository extends CrudRepository<ShopifyWebHook, Long> {

	@Query("SELECT COUNT(*) FROM ShopifyWebHook webHook "
			+ "WHERE requestUniqueId = :webHookUniqueKey")
	int getWebHookCountByUniqueKey(@Param("webHookUniqueKey")String webHookUniqueKey);

}
