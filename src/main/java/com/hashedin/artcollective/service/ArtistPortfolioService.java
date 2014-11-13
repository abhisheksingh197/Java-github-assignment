package com.hashedin.artcollective.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hashedin.artcollective.entity.ArtWork;
import com.hashedin.artcollective.entity.Artist;
import com.hashedin.artcollective.entity.ArtworkVariant;
import com.hashedin.artcollective.entity.Deduction;
import com.hashedin.artcollective.entity.FulfilledOrder;
import com.hashedin.artcollective.entity.OrderLineItem;
import com.hashedin.artcollective.entity.Transaction;
import com.hashedin.artcollective.repository.ArtWorkRepository;
import com.hashedin.artcollective.repository.ArtistRepository;
import com.hashedin.artcollective.repository.ArtworkVariantRepository;
import com.hashedin.artcollective.repository.DeductionRepository;
import com.hashedin.artcollective.repository.OrderLineItemRepository;
import com.hashedin.artcollective.repository.TransactionRepository;
import com.hashedin.artcollective.utils.Pair;

@Service
public class ArtistPortfolioService implements UserDetailsService {
	
	
	@Autowired
	private ArtistRepository artistRepository;
	
	@Autowired
	private OrderLineItemRepository orderLineItemRepository;
	
	@Autowired
	private ArtWorkRepository artworkRepository;
	
	@Autowired
	private ArtworkVariantRepository artworkVariantRepository;
	
	@Autowired
	private DeductionRepository deductionsRepository;
	
	@Autowired
	private TransactionRepository transactionRepository;
	

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

	public List<PortfolioEarnings> getEarningsByArtist(Long artistId) {
		List<OrderLineItem> earningsLineItems = orderLineItemRepository.getOrderLineItemsByArtist(artistId);
		List<PortfolioEarnings> earningsForArtist = new ArrayList<>();
		for (OrderLineItem lineItem : earningsLineItems) {
			PortfolioEarnings earningsObject = new PortfolioEarnings();
			FulfilledOrder lineItemOrder = lineItem.getOrder();
			earningsObject.setOrderId(lineItemOrder.getId());
			earningsObject.setOrderName(lineItemOrder.getName());
			earningsObject.setOrderDate(lineItemOrder.getCreatedAt());
			earningsObject.setProductImageSrc(getProductImageSrc(lineItem.getProductId()));
			earningsObject.setVariantSize(getVariantSize(lineItem.getVariantId()));
			earningsObject.setQuantity(lineItem.getQuantity());
			earningsObject.setCommission(lineItem.getEarning());
			earningsForArtist.add(earningsObject);	
		}
		return earningsForArtist;
	}
	
	

	private String getVariantSize(Long variantId) {
		ArtworkVariant variant = artworkVariantRepository.findOne(variantId);
		return variant.getOption1();
	}

	private String getProductImageSrc(Long productId) {
		ArtWork artwork = artworkRepository.findOne(productId);
		return artwork.getImages().get(0).getImgSrc();
	}

	public Map<String, Double> getDashboardValues(Long artistId) {
		Map<String, Double> dashboardValues = new HashMap<>();
		Double zero = 0.0;
		Double totalEarningsAsCommission = orderLineItemRepository.getSumOfOrderLineItemsByArtist(artistId);
		totalEarningsAsCommission = totalEarningsAsCommission == null ? zero : totalEarningsAsCommission;
		Double totalDeductions = deductionsRepository.getSumOfDeductionsByArtist(artistId);
		totalDeductions = totalDeductions == null ? zero : totalDeductions;
		Double netCommission = totalEarningsAsCommission - totalDeductions;
		Double payouts = transactionRepository.getSumOfTransactionsByArtist(artistId);
		payouts = payouts == null ? zero : payouts;
		Double pending = netCommission - payouts;
		
		dashboardValues.put("totalEarningsAsCommission", totalEarningsAsCommission);
		dashboardValues.put("totalDeductions", totalDeductions);
		dashboardValues.put("netCommission", netCommission);
		dashboardValues.put("payouts", payouts);
		dashboardValues.put("pending", pending);
		
		return dashboardValues;
	}
	
	public Map<String, String> getArtworkImagesByArtist(Long artistId) {
		Map<String, String> artworksImageMap = new HashMap<>();
		List<ArtWork> artworks = artworkRepository.getArtworksByArtist(artistId);
		for (ArtWork artwork : artworks) {
			String artworkId = String.valueOf(artwork.getId());
			String artworkImageSrc = artwork.getImages().get(0).getImgSrc();
			artworksImageMap.put(artworkId, artworkImageSrc);
		}
		return artworksImageMap;
		
	}

	public List<Transaction> getTransactionsByArtist(Long artistId) {
		List<Transaction> transactions = transactionRepository.getTransactionsByArtist(artistId);
		return transactions;
	}

	public List<Deduction> getDeductionsByArtist(Long artistId) {
		List<Deduction> deductions = deductionsRepository.getDeductionsByArtist(artistId);
		return deductions;
	}

}
