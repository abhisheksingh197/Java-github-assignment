package com.hashedin.artcollective.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import scala.Serializable;

import com.hashedin.artcollective.entity.ArtWork;

class ArtWorkComparatorByColourMatch implements Comparator<ArtWork>, Serializable {
	private static final long serialVersionUID = 1L;
	private final Map<Long, Integer> artWorkPositionById;
	
	ArtWorkComparatorByColourMatch(List<Long> artworkIds) {
		Map<Long, Integer> artWorkByPos = new HashMap<Long, Integer>();
		int position = 0;
		for (Long artWorkId : artworkIds) {
			artWorkByPos.put(artWorkId, position++);
		}
		
		this.artWorkPositionById = Collections.unmodifiableMap(artWorkByPos);
	}
	
	@Override
	public int compare(ArtWork o1, ArtWork o2) {
		int index1 = artWorkPositionById.get(o1.getId());
		int index2 = artWorkPositionById.get(o2.getId());
		return (index1 < index2) ? -1 : ((index1 > index2) ? 1 : 0);
	}
}

