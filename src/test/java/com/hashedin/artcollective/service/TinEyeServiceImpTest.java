package com.hashedin.artcollective.service;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hashedin.artcollective.BaseUnitTest;
import com.hashedin.artcollective.entity.ArtWork;

public class TinEyeServiceImpTest extends BaseUnitTest {

	@Autowired
	private TinEyeServiceImpl tineye;
	
	@Test
	public void test() {
		ArtWork art = new ArtWork();
		art.setId(42L);
		List<ArtWork> arts = new ArrayList<>();
		arts.add(art);
		tineye.uploadArts(arts);
	}

}
