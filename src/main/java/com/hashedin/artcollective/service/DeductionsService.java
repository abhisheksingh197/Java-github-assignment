package com.hashedin.artcollective.service;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hashedin.artcollective.entity.ArtWork;
import com.hashedin.artcollective.entity.Deduction;
import com.hashedin.artcollective.repository.ArtWorkRepository;
import com.hashedin.artcollective.repository.DeductionRepository;

@Service
public class DeductionsService {
	
	@Autowired
	private DeductionRepository deductionsRepository;
	
	@Autowired
	private ArtWorkRepository artworkRepository;
	
	public Deduction addNewDeduction(DateTime createdAt, String type, Long artworkId, 
			Long quantity, Double unitPrice, Double totalDeductions) {
		ArtWork artwork = artworkRepository.findOne(artworkId);
		if (artwork != null) {
			Long artistId = artwork.getArtist().getId();
			Deduction deduction = new Deduction(createdAt, type, artistId, artworkId, 
					quantity, unitPrice, totalDeductions);
			return deductionsRepository.save(deduction);
		}
		return null;
	}

}
