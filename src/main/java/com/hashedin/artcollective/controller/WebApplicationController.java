package com.hashedin.artcollective.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hashedin.artcollective.entity.Artist;
import com.hashedin.artcollective.repository.ArtWorkRepository;
import com.hashedin.artcollective.repository.ArtistRepository;
import com.hashedin.artcollective.service.ArtistPortfolioService;


@Controller
public final class WebApplicationController {
	
	@Autowired
	private ArtistRepository artistRepository;
	
	@Autowired
	private ArtWorkRepository artworkRepository;
	
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
	public ModelAndView artistDashboard() {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Artist artist = (Artist) auth.getPrincipal();
		Long artistId = artist.getId();
		ModelAndView model = getPortfolio(artistId);
		return model;
		
	}
	
	@RequestMapping("/manage/upload/deductions")
	public ModelAndView uploadDeductionsAsCSV() {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("time", new Date());
		model.put("message", "Hello World!");

		return new ModelAndView("deductions-upload", model);
	}
	
	@RequestMapping("/manage/upload/transactions")
	public ModelAndView uploadTransactionsAsCSV() {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("time", new Date());
		model.put("message", "Hello World!");

		return new ModelAndView("transactions-upload", model);
	}
	
	private ModelAndView getPortfolio(Long artistId) {
		
		Map<String, Object> model = new HashMap<String, Object>();
		Artist artist = artistRepository.findOne(artistId);
		if (artist != null) {
			model.put("artist", artist);
			model.put("lineitems", artistPortfolioService.getLineItemsForPortfoilio(artistId));
			model.put("artworks", artworkRepository.getArtworksByArtist(artistId));
			model.put("dashboardValues", artistPortfolioService.getDashboardValues(artistId));
			model.put("earningsList", artistPortfolioService.getEarningsByArtist(artistId));
			model.put("transactionsList", artistPortfolioService.getTransactionsByArtist(artistId));
			model.put("deductionsList", artistPortfolioService.getDeductionsByArtist(artistId));
			model.put("artworkImages", artistPortfolioService.getArtworkImagesByArtist(artistId));
			return new ModelAndView("artist-dashboard", model);
		}
		return null;
	}
}
