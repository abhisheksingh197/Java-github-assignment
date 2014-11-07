package com.hashedin.artcollective.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.hashedin.artcollective.entity.OrderLineItem;

public interface OrderLineItemRepository extends CrudRepository<OrderLineItem, Long> {

	
	@Query("SELECT orderLineItems FROM OrderLineItem orderLineItems WHERE "
			+ "orderLineItems.artistId = :artistId")
	public List<OrderLineItem> getOrderLineItemsByArtistId(@Param("artistId")Long artistId);

}
