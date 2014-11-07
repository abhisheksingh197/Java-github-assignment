package com.hashedin.artcollective.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hashedin.artcollective.service.ArtWorksService;
import com.hashedin.artcollective.service.ArtistPortfolioService;


@Controller
public final class WebApplicationController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(WebApplicationController.class);
	
	@Autowired
	private ArtistPortfolioService artistPortfolioService;
	
	@RequestMapping("/")
	public ModelAndView index() {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("time", new Date());
		model.put("message", "Hello World!");

		return new ModelAndView("index", model);
	}
	
	@RequestMapping("/dashboard")
	public ModelAndView search() {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Long artistId = Long.parseLong(auth.getName()); //get logged in username/Id
		ModelAndView model = artistPortfolioService.getPortfolio(artistId);
		LOGGER.info(model.toString());
		LOGGER.info("reading --------------------");
		return model;
		
	}
}
