package com.hashedin.artcollective.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hashedin.artcollective.entity.ArtWork;
//import com.hashedin.artcollective.entity.Artist;

@Controller
public final class WebApplicationController {
	
	@RequestMapping("/")
	public ModelAndView index() {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("time", new Date());
		model.put("message", "Hello World!");
		
		return new ModelAndView("index", model);
	}
	
	@RequestMapping("/dashboard")
	public ModelAndView search() {
		Map<String, Object> model = new HashMap<String, Object>();
		
		String artistId = "magic";
		
		List<ArtWork> artworks = new ArrayList<>(); 
		
		//model.put("artist", new Artist("Amit", "Bhar", "amit-bhar", 32L));
		model.put("artworks", artworks);
		
		return new ModelAndView("artist-dashboard", model);
	}
}
