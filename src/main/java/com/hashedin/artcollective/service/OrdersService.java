package com.hashedin.artcollective.service;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hashedin.artcollective.entity.ArtWork;
import com.hashedin.artcollective.entity.FulfilledOrder;
import com.hashedin.artcollective.entity.OrderLineItem;
import com.hashedin.artcollective.entity.SynchronizeLog;
import com.hashedin.artcollective.repository.ArtWorkRepository;
import com.hashedin.artcollective.repository.ArtworkVariantRepository;
import com.hashedin.artcollective.repository.FulfilledOrderRepository;
import com.hashedin.artcollective.repository.OrderLineItemRepository;
import com.hashedin.artcollective.repository.SynchronizeLogRepository;

@Service
public class OrdersService {
	
	@Autowired
	private ShopifyService shopifyService;
	
	@Autowired
	private ArtWorkRepository artworkRepository;
	
	@Autowired
	private SynchronizeLogRepository syncLogRepository;
	
	@Autowired
	private OrderLineItemRepository orderLineItemRepository;
	
	@Autowired
	private FulfilledOrderRepository fulfillOrderRepository;
	
	@Autowired
	private ArtworkVariantRepository artworkVariantRepository;
	
	
	
	public List<Order> synchronize(String mode) {
		
		//Fetching last synchronize time 
		DateTime lastRunTime = null;
		if (!"full".equalsIgnoreCase(mode)) {
			lastRunTime = getLastRunTime();
		}
		DateTime syncStartTime = new DateTime(); 
		List<Order> orders = shopifyService.getOrderSinceLastModified(lastRunTime);
		DateTime syncEndTime = new DateTime();
		
		// Adding current synchronize details
		SynchronizeLog syncLog = new SynchronizeLog(syncStartTime, syncEndTime, "orders",
				"successfull", (long) orders.size());
		syncLogRepository.save(syncLog);
		
		segregateShopifyOrders(orders);
		
		return orders;
	}

	private List<FulfilledOrder> segregateShopifyOrders(List<Order> orders) {
		/*
		 * From orders returned by shopify we create fulfilledOrders and valid LineItems and then 
		 * save them in to the repository 
		*/
		for (Order order : orders) {
			FulfilledOrder fulfilledOrder = createFulfilledOrder(order);
			fulfillOrderRepository.save(fulfilledOrder);
			List<OrderFulfillment> fulfillments = order.getFulfillments();
			for (OrderFulfillment fulfillment : fulfillments) {
				List<OrderLineItem> lineItems = createLineItems(fulfillment.getOrderLineItems(), 
						fulfilledOrder);
				orderLineItemRepository.save(lineItems);		
			}
			
		}
		
		return null;
	}
	
	/*
	 * Creating line items with corresponding order and Artist id
	 * Additional custom fields can later be added to meet requirements
	*/
	private List<OrderLineItem> createLineItems(
			List<OrderLineItem> orderLineItems, FulfilledOrder fulfilledOrder) {
		for (OrderLineItem lineItem : orderLineItems) {
			lineItem.setOrder(fulfilledOrder); 
			lineItem.setArtistId(getArtistId(lineItem.getProductId()));
			Double earning = getEarningsForVariant(lineItem.getVariantId());
			if (earning != null) {
				lineItem.setEarning(earning * lineItem.getQuantity());
			}
			
		}
		return orderLineItems;
	}
	
	private Double getEarningsForVariant(Long variantId) {

		return artworkVariantRepository.getEarningForVariant(variantId);
	}

	// Fetching Artist Id based on Product Id
	private Long getArtistId(Long productId) {
		ArtWork artwork = artworkRepository.findOne(productId);
		if (artwork != null) {
			return artwork.getArtist().getId();
		}
		return null;
	}
	
	// Creating a fulfilled order 
	private FulfilledOrder createFulfilledOrder(Order order) {
		FulfilledOrder fulfilledOrder = new FulfilledOrder();
		fulfilledOrder.setId(order.getId());
		fulfilledOrder.setName(order.getName());
		fulfilledOrder.setCreatedAt(order.getCreatedAt());
		fulfilledOrder.setOrderNo(order.getOrderNo());
		fulfilledOrder.setTotalPrice(order.getTotalPrice());
		return fulfilledOrder;
	}

	private DateTime getLastRunTime() {
		return syncLogRepository.getLastSynchronizeDate("orders");
	}

}
