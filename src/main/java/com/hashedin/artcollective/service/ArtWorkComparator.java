package com.hashedin.artcollective.service;

import java.util.Comparator;
import java.util.List;

import scala.Serializable;

import com.hashedin.artcollective.entity.ArtWork;

class ArtWorkComparator implements Comparator<ArtWork>, Serializable {
	private static final long serialVersionUID = 1L;
	private List<Long> artworkIds;
	public List<Long> getArtworkIds() {
		return artworkIds;
	}

	public void setArtworkIds(List<Long> artworkIds) {
		this.artworkIds = artworkIds;
	}

	ArtWorkComparator(List<Long> artworkIds) {
	   this.artworkIds = artworkIds;
	 }
	  
	 @Override
	 public int compare(ArtWork o1, ArtWork o2) {
	   int index1 = artworkIds.indexOf(o1.getId());
	   int index2 = artworkIds.indexOf(o2.getId());
	   return (index1 < index2) ? -1 : ((index1 > index2) ? 1 : 0);
	}
}
