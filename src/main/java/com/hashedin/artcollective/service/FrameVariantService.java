package com.hashedin.artcollective.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hashedin.artcollective.entity.FrameVariant;
import com.hashedin.artcollective.repository.FrameVariantRepository;

@Service
public class FrameVariantService {
	
	@Autowired
	private FrameVariantRepository frameRepository;
	
	public List<FrameVariant> getFrames(Double frameLength, Double frameBreadth, 
			Double mountThickness, Double frameThickness) {
		List<Long> frameIds = new ArrayList<>();
		List<FrameVariant> frameVariants = frameRepository.findVariants(frameLength, 
				frameBreadth, mountThickness, frameThickness);
		
		return frameVariants;
	}

}
