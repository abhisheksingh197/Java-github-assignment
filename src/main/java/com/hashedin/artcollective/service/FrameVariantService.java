package com.hashedin.artcollective.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hashedin.artcollective.entity.ArtworkVariant;
import com.hashedin.artcollective.entity.FrameVariant;
import com.hashedin.artcollective.repository.ArtworkVariantRepository;
import com.hashedin.artcollective.repository.FrameVariantRepository;
import com.hashedin.artcollective.utils.ProductSize;

@Service
public class FrameVariantService {
	
	private static final Integer INCHES_PER_FEET = 12;
	
	@Autowired
	private ArtworkVariantRepository artworkVariantRepository;
	
	@Autowired
	private FrameVariantRepository frameRepository;
	
	@Autowired
	private ShopifyService shopifyService;
	
	public List<FrameVariant> getFrames(Double mountThickness, Double frameThickness) {
		List<FrameVariant> frameVariants = frameRepository.findVariants(mountThickness, frameThickness);
		
		return frameVariants;
	}

	public Double getFramePrice(Double frameLength, Double frameBreadth,
			FrameVariant frameVariant) {
		Double mountAndFrameLength = (frameVariant.getMountThickness() 
				+ frameVariant.getFrameThickness()) * 2;
		Double perimeterOfLength = (frameLength + mountAndFrameLength) * 2;
		Double perimeterOfWidth = (frameBreadth + mountAndFrameLength) * 2;
		Double totalPerimeter = (perimeterOfLength + perimeterOfWidth);
		Double totalRunningFeet = Math.ceil(totalPerimeter / INCHES_PER_FEET);
		Double frameCost = totalRunningFeet * frameVariant.getUnitPrice();
		return frameCost;
	}

	public CustomCollection createAddOnProduct(Long productVariantId, 
			Long frameVariantId, String type) {
		FrameVariant frameVariant = frameRepository.findOne(frameVariantId);
		ArtworkVariant artworkVariant = artworkVariantRepository.findOne(productVariantId);
		if (artworkVariant != null && frameVariant != null) {
			ProductSize productSize = new ProductSize(artworkVariant);
			Double productPrice = 0.0;
			if (type.equalsIgnoreCase("frames")) {
				productPrice = getFramePrice(productSize.getProductLength(), 
						productSize.getProductBreadth(), 
						frameVariant);
			}
			else if (type.equalsIgnoreCase("canvas")) {
				productPrice = getCanvasPrice(productSize.getProductLength(), 
						productSize.getProductBreadth(), 
						frameVariant.getId());
			}
			return shopifyService.createDynamicProduct(frameVariant, productSize, productPrice, type);
		}
		
		return null;
		
	}

	public Double getCanvasPrice(Double productLength, Double productBreadth,
			long frameVariantId) {
		FrameVariant frameVariant = frameRepository.findOne(frameVariantId);
		if (frameVariant != null) {
			Long runningFeetForLength = Math.round((productLength / INCHES_PER_FEET) * 2) / 2;
			Long runningFeetForBreadth = Math.round((productBreadth / INCHES_PER_FEET) * 2) / 2;
			Double canvasCost = ((runningFeetForLength * 2) + (runningFeetForBreadth * 2)) 
					* frameVariant.getUnitPrice();
			return canvasCost;
		}
		
		return null;
		
		
	}

}
