package com.hashedin.artcollective.service;

import java.util.List;

import com.hashedin.artcollective.entity.ArtWork;

public interface TinEyeService {

	public void uploadArts(List<ArtWork> arts);
	
	public List<ArtWork> getMatchingArtWorks(ArtSearchCriteria criteria);

	// void deleteTinEyeImages();
}
