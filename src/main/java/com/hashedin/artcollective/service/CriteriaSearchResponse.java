package com.hashedin.artcollective.service;

import java.io.Serializable;
import java.util.List;

import com.hashedin.artcollective.entity.ArtWork;

public class CriteriaSearchResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<ArtWork> artworks;
	private int totalArtworkCount;
	
	public CriteriaSearchResponse(List<ArtWork> artworks, int totalArtworkCount) {
		super();
		this.artworks = artworks;
		this.totalArtworkCount = totalArtworkCount;
	}
	public List<ArtWork> getArtworks() {
		return artworks;
	}
	public void setArtworks(List<ArtWork> artworks) {
		this.artworks = artworks;
	}
	public int getTotalArtworkCount() {
		return totalArtworkCount;
	}
	public void setTotalArtworkCount(int totalArtworkCount) {
		this.totalArtworkCount = totalArtworkCount;
	}
	
	

}
