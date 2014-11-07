package com.hashedin.artcollective.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.hashedin.artcollective.entity.ArtWork;
import com.hashedin.artcollective.entity.Artist;
import com.hashedin.artcollective.entity.OrderLineItem;
import com.hashedin.artcollective.repository.ArtWorkRepository;
import com.hashedin.artcollective.repository.ArtistRepository;
import com.hashedin.artcollective.repository.OrderLineItemRepository;

@Service
public class ArtistPortfolioService {
	
	@Autowired
	private ArtistRepository artistRepository;
	
	@Autowired
	private OrderLineItemRepository orderLineItemRepository;
	
	@Autowired
	private ArtWorkRepository artworkRepository;
	
	public ModelAndView getPortfolio(Long artistId) {
		
		Map<String, Object> model = new HashMap<String, Object>();
		Artist artist = artistRepository.findOne(artistId);
		if (artist != null) {
			model.put("artist", artist);
			model.put("artworks", getArtworksForPortfoilio(artistId));
			return new ModelAndView("artist-dashboard", model);
		}
		return null;
	}

	/**
	 * a
	 * NOTE: Using <code>String</code> instead <code>Long</code> because FTL does not
	 * support non-string keys for Hashes
	 *
	 * @param artistId
	 * @return
	 */
	public Map<String, PortfolioLineItem> getArtworksForPortfoilio(Long artistId) {
		List<OrderLineItem> orderLineItemsForArtist = orderLineItemRepository
				.getOrderLineItemsByArtistId(artistId);
		
		Map<String, PortfolioLineItem> lineItemOrders = new HashMap<String, PortfolioLineItem>();
		
		for (OrderLineItem orderLineItemForArtist : orderLineItemsForArtist) {
			Long lineItemProductId = orderLineItemForArtist.getProductId();
			PortfolioLineItem portfolioLineItem = lineItemOrders.get(lineItemProductId);
			if (portfolioLineItem == null) {
				lineItemOrders.put(lineItemProductId.toString(), 
						getPortfolioElement(orderLineItemForArtist));
			}
			else {
				lineItemOrders.remove(portfolioLineItem);
				portfolioLineItem.addOrderLineItem(orderLineItemForArtist);
				lineItemOrders.put(lineItemProductId.toString(), portfolioLineItem);
			}
		}
		
		return lineItemOrders;
	}

	private PortfolioLineItem getPortfolioElement(OrderLineItem orderLineItemForArtist) {
		Long productId = orderLineItemForArtist.getProductId();
		ArtWork artwork = artworkRepository.findOne(productId);
		PortfolioLineItem portfolioLineItem = new PortfolioLineItem(artwork, orderLineItemForArtist);
		return portfolioLineItem;
	}
}
