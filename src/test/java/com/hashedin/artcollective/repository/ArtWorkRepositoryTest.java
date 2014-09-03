package com.hashedin.artcollective.repository;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hashedin.artcollective.BaseUnitTest;
import com.hashedin.artcollective.entity.ArtWork;
import com.hashedin.artcollective.entity.Artist;
import com.hashedin.artcollective.entity.ArtStyle;;
 
public class ArtWorkRepositoryTest extends BaseUnitTest {

	@Autowired
	private ArtWorkRepository repository;
	@Test
	public void createArtWork() {
		
		
		ArtWork art = new ArtWork();
		Artist artist = new Artist("Leonardo", "Picasso");
		ArtStyle artStyle = new ArtStyle();
		List<ArtStyle> artStyleList = new ArrayList<>();
		artStyle.setId(2L);
		artStyle.setTitle("Success");
		artStyleList.add(artStyle);
		art.setId(1L);
		art.setArtist(artist);
		art.setStyle(artStyleList);
		art.setIsCanvasAvailable(true);
		ArtWork artwork  = repository.save(art);
		artStyleList = art.getStyle();
		artStyle = artStyleList.get(0);
		assertEquals(artStyle.getTitle(), "Success");
		
	}

}
