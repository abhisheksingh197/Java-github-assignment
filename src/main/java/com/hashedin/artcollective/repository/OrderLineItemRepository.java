package com.hashedin.artcollective.repository;

import org.springframework.data.repository.CrudRepository;

import com.hashedin.artcollective.entity.OrderLineItem;

public interface OrderLineItemRepository extends CrudRepository<OrderLineItem, Long> {

}
