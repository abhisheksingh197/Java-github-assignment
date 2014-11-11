package com.hashedin.artcollective.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.hashedin.artcollective.entity.Artist;
import com.hashedin.artcollective.repository.ArtWorkRepository;
import com.hashedin.artcollective.repository.ArtistRepository;
import com.hashedin.artcollective.repository.OrderLineItemRepository;
import com.hashedin.artcollective.utils.Pair;

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
			model.put("lineitems", getLineItemsForPortfoilio(artistId));
			model.put("artworks", artworkRepository.getArtworksByArtist(artistId));
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
	public Map<String, Double> getLineItemsForPortfoilio(Long artistId) {
		Map<String, Double> earningsByVariant = new HashMap<>();
		List<Pair<Long, Double>> earningsByOrder = orderLineItemRepository
				.getOrderLineItemsByArtistAndArtwork(artistId);
		
		for (Pair<Long, Double> pair : earningsByOrder) {
			earningsByVariant.put(pair.getKey().toString(), pair.getValue());
		}
		
		return earningsByVariant;
	}

}
