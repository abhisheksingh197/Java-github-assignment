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
	
	public List<Long> getFrames(Long frameLength, Long frameBreadth, Long mountThickness, Long frameThickness) {
		List<Long> frameIds = new ArrayList<>();
		List<FrameVariant> frameVariants = frameRepository.findVariants(frameLength, 
				frameBreadth, mountThickness, frameThickness);
		for (FrameVariant frameVariant : frameVariants) {
			frameIds.add(frameVariant.getId());
		}
		
		return frameIds;
	}

}
