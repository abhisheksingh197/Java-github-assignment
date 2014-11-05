package com.hashedin.artcollective.service;

import java.util.List;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.stereotype.Service;

import com.hashedin.artcollective.entity.FulfilledOrder;
import com.hashedin.artcollective.entity.SynchronizeLog;
import com.hashedin.artcollective.repository.SynchronizeLogRepository;

@Service
public class OrdersService {
	
	@Autowired
	private ShopifyService shopifyService;
	
	@Autowired
	private SynchronizeLogRepository syncLogRepository;
	
	@Autowired
	private DateTimeProvider dateTimeProvider;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(OrdersService.class);
	
	public List<Order> synchronize(String mode) {
		DateTime lastRunTime = null;
		if (!mode.equalsIgnoreCase("full")) {
			lastRunTime = getLastRunTime();
		}
		DateTime syncStartTime = new DateTime(); 
		List<Order> orders = shopifyService.getOrderSinceLastModified(lastRunTime);
		DateTime syncEndTime = new DateTime();
		SynchronizeLog syncLog = new SynchronizeLog(syncStartTime, syncEndTime, "orders",
				"successfull", (long) orders.size());
		syncLogRepository.save(syncLog);
		
//		List<FulfilledOrder> fulfilledOrders = createFulfilledOrders(orders);
		
		return orders;
	}

	private List<FulfilledOrder> createFulfilledOrders(List<Order> orders) {
//		List<FulfilledOrder> fulfilledOrders = new ArrayList<>();
//		for (Order order : orders) {
//			List<OrderLineItem> orderLineItems = new ArrayList<>();
//			List<OrderFulfillment> fulfillments = order.getFulfillments();
//			for (OrderFulfillment fulfillment : fulfillments) {
//				orderLineItems.addAll(fulfillment.getOrderLineItems());
//			}
//		}
		return null;
	}

	private DateTime getLastRunTime() {
		return syncLogRepository.getLastSynchronizeDate("orders");
	}

}
