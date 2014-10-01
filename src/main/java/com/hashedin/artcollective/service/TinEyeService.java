package com.hashedin.artcollective.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.hashedin.artcollective.entity.ArtWork;

public interface TinEyeService {

	public void uploadArts(List<ArtWork> arts);
	
	public List<ArtWork> getMatchingArtWorks(ArtSearchCriteria criteria);

	public void extractColors(List<ArtWork> arts);

	public String extractColorUploadImage(File file) throws IOException;

}
