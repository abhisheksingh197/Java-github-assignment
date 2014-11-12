package com.hashedin.artcollective.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.hashedin.artcollective.entity.Artist;
import com.hashedin.artcollective.repository.ArtistRepository;
import com.hashedin.artcollective.repository.OrderLineItemRepository;
import com.hashedin.artcollective.utils.Pair;

@Service
public class ArtistPortfolioService implements UserDetailsService {
	
	@Autowired
	private ArtistRepository artistRepository;
	
	@Autowired
	private OrderLineItemRepository orderLineItemRepository;
	

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

	@Override
	public UserDetails loadUserByUsername(String username) {
		Artist artist = artistRepository.findByUsername(username);
		
		if (artist == null) {
			throw new UsernameNotFoundException("Username " + username + " not found");
		}
		return artist;
	}

}
