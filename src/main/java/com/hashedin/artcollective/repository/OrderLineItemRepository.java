package com.hashedin.artcollective.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.hashedin.artcollective.entity.OrderLineItem;
import com.hashedin.artcollective.utils.Pair;

public interface OrderLineItemRepository extends CrudRepository<OrderLineItem, Long> {

	
	@Query("SELECT NEW com.hashedin.artcollective.utils.Pair(items.variantId, SUM(items.earning)) "
			+ "FROM OrderLineItem items WHERE "
			+ "items.artistId = :artistId "
			+ "group by items.variantId")
	public List<Pair<Long, Double>> getOrderLineItemsByArtistAndArtwork(@Param("artistId")Long artistId);
	
	@Query("SELECT items FROM OrderLineItem items WHERE "
			+ "items.artistId = :artistId order by items.order.createdAt desc")
	public List<OrderLineItem> getOrderLineItemsByArtist(@Param("artistId")Long artistId);

	@Query("SELECT SUM(earning) FROM OrderLineItem items WHERE "
			+ "items.artistId = :artistId")
	public Double getSumOfEarningsByArtist(@Param("artistId")Long artistId);

}
