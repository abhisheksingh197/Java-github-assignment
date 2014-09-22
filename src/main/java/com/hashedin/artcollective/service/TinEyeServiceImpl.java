package com.hashedin.artcollective.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestOperations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hashedin.artcollective.entity.ArtWork;
import com.hashedin.artcollective.entity.Image;
import com.hashedin.artcollective.repository.ArtWorkRepository;

@Service
public class TinEyeServiceImpl implements TinEyeService {

	private static final Logger LOGGER = LoggerFactory.getLogger(TinEyeService.class);
	
	private final String baseUri;
	@Autowired
	private RestOperations rest;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private ArtWorkRepository artworkRepository;
	
	@Autowired
	public TinEyeServiceImpl(RestOperations rest, 
								@Value("${tinEye.baseurl}") String baseUrl) {
		this.rest = rest;
		this.baseUri = baseUrl;
	}
	
	@Override
	public void uploadArts(List<ArtWork> arts) {
		
			MultiValueMap<String, String> params = getUploadPostParameters(arts);
			HttpHeaders headers = new HttpHeaders();
			HttpEntity<?> entity = new HttpEntity<Object>(params, headers);
			ResponseEntity<String> postResponse = 
					rest.exchange(baseUri + "add", 
							HttpMethod.POST, entity, String.class);
			LOGGER.info(postResponse.getBody());
			LOGGER.info("Uploaded ArtWorks to tineye servers");
	}

	private MultiValueMap<String, String> getUploadPostParameters(List<ArtWork> arts) {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		int imgIterator = 0;
		for (ArtWork artwork : arts) {
			List<Image> artworkImageList = artwork.getImages();
			Image image = artworkImageList.get(0);
			params.add("urls[" + imgIterator + "]", image.getImgSrc());
			params.add("filepaths[" + imgIterator + "]", "artworks/" + String.valueOf(image.getId()) 
					+ ".jpg");
			String metadata = "{\"artworkId\":"
				+ "{\"action\": \"return\", \"type\": \"uint\", \"\":\"" 
				+ artwork.getId() + "\"}}";
			params.add("metadata[" + imgIterator + "]", metadata);
			imgIterator++;
		}
		return params;
	}
	
	@Override
	public List<ArtWork> getMatchingArtWorks(ArtSearchCriteria criteria) {
		
		List<ArtWork> artWorks = new ArrayList<>();
		MultiValueMap<String, String> params = getSearchPostParameters(criteria);
		HttpHeaders headers = new HttpHeaders();
		SearchResponse searchResponseObj = new SearchResponse();
		HttpEntity<?> entity = new HttpEntity<Object>(params, headers);
		ResponseEntity<String> postResponse = rest.exchange(baseUri + "color_search/", 
				HttpMethod.POST, entity, String.class);
		try {
			searchResponseObj = objectMapper.readValue(postResponse.getBody(), SearchResponse.class);
		} 
		catch (IOException e1) {
			e1.printStackTrace();
		}
		LOGGER.info(postResponse.getBody());
		List<ResponseResult> responseResult = searchResponseObj.getResult();
		for (ResponseResult result : responseResult) {
			TinEyeMetadata metadata = result.getMetadata();
			Long artworkId = metadata.getArtworkId();
			if (artworkId != null) {
				ArtWork tinEyeSearchArt = artworkRepository.findOne(artworkId);
				if (tinEyeSearchArt != null) {
					artWorks.add(artworkRepository.findOne(artworkId));
				}
			}
		}
		
		LOGGER.info("Search Based on Colors");
		return artWorks;
	}
	
	private MultiValueMap<String, String> getSearchPostParameters(ArtSearchCriteria criteria) {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
			params.add("colors[0]", criteria.getColour1());
			params.add("weights[0]", (criteria.getColor1Weight()) != 0 
					? String.valueOf(criteria.getColor1Weight()) : "1");
			params.add("colors[1]", criteria.getColour2());
			params.add("weights[1]", (criteria.getColor2Weight()) != 0 
					? String.valueOf(criteria.getColor2Weight()) : "1");
			params.add("return_metadata", "[\"artworkId\"]");
			params.add("limit", "-1");
		return params;
	}
	
//	@Override
//	public void deleteTinEyeImages() {
//		int count = getCountofTinEyeListAPI();
//		while (count > 0) {
//			HttpHeaders headers = new HttpHeaders();
//			HttpEntity<?> entity = new HttpEntity<String>(headers);
//			ResponseEntity<String> postResponse = rest.exchange(baseUri + "list/", 
//					HttpMethod.POST, entity, String.class);
//			String searchResponse = postResponse.getBody();
//			MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//			try {
//				JSONObject jsonObj = new JSONObject(searchResponse);
//				JSONArray jsonArray = jsonObj.getJSONArray("result");
//				
//				for (int i = 0; i < jsonArray.length(); i++) {
//					String filepath = jsonArray.get(i).toString();
//					params.add("filepaths[" + i + "]", filepath);
//				}
//				callTinEyeDeleteAPI(params);
//				
//			} catch (JSONException e) {
//				e.printStackTrace();
//			}
//			count = getCountofTinEyeListAPI();
//		}
//		LOGGER.info("Deleted All Images");
//	}
//
//	private int getCountofTinEyeListAPI() {
//		int count = 0;
//		HttpHeaders headers = new HttpHeaders();
//		HttpEntity<?> entity = new HttpEntity<String>(headers);
//		ResponseEntity<String> postResponse = rest.exchange(baseUri + "count/", 
//				HttpMethod.POST, entity, String.class);
//		String searchResponse = postResponse.getBody();
//		try {
//			JSONObject jsonObj = new JSONObject(searchResponse);
//			JSONArray jsonArray = jsonObj.getJSONArray("result");
//			count = (int) jsonArray.get(0);
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
//		return count;
//	}
//
//	private void callTinEyeDeleteAPI(MultiValueMap<String, String> params) {
//		HttpHeaders headers = new HttpHeaders();
//		HttpEntity<?> entity = new HttpEntity<Object>(params, headers);
//		ResponseEntity<String> postResponse = rest.exchange(baseUri + "delete/", 
//				HttpMethod.POST, entity, String.class);
//	}


}
