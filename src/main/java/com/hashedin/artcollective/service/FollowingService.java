package com.hashedin.artcollective.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hashedin.artcollective.repository.ArtCollectionsRepository;
import com.hashedin.artcollective.repository.ArtStyleRepository;
import com.hashedin.artcollective.repository.ArtSubjectRepository;
import com.hashedin.artcollective.repository.ArtistRepository;

@Service
public class FollowingService {
	
	@Autowired
	private ShopifyService shopifyService;
	
	@Autowired
	private ArtSubjectRepository artSubjectRepository;

	@Autowired
	private ArtStyleRepository artStyleRepository;
	
	@Autowired
	private ArtCollectionsRepository artCollectionsRepository;
	
	@Autowired
	private ArtistRepository artistRepository;
	

	public Map<String, Object> getFollowingsForUser(Long customerId) {
		Map<String, Object> userPreferences = new HashMap<String, Object>();
		List<MetaField> metafields = shopifyService.getMetaFieldsByKeyType(
				"customers", customerId, "followings");
		for (MetaField metafield : metafields) {
			String[] collectionIdsInMetafield = metafield.getValue().split(",");
			for (String collectionId : collectionIdsInMetafield) {
				userPreferences.put(collectionId, collectionId);
			}
		}
		return userPreferences;
	}

	public Boolean updateFollowingsForUser(Long customerId, String[] subjects,
			String[] styles, String[] collections, String[] artists) {
		List<MetaField> metafields  = shopifyService.createMetafieldsForCustomer(
				customerId, "followings");
		if (metafields == null) {
			return false;
		}
		for (MetaField metafield : metafields) {
			switch (metafield.getKey().split("_")[1]) {
				case "subject" :
					if (subjects.length > 0) {
						shopifyService.updateMetafield("customers", String.valueOf(customerId), 
								subjects, metafield.getId());
					}
					break;
				case "style" :
					if (styles.length > 0) {
						shopifyService.updateMetafield("customers", String.valueOf(customerId), 
								styles, metafield.getId());
					}
					break;
				case "collection" : 
					if (collections.length > 0) {
						shopifyService.updateMetafield("customers", String.valueOf(customerId), 
								collections, metafield.getId());
					}
					break;
				case "artist" :
					if (artists.length > 0) {
						shopifyService.updateMetafield("customers", String.valueOf(customerId), 
								artists, metafield.getId());
					}
					break;
				default :
					break;
			}
		}
		return true;
		
	}
	
	
	
}
