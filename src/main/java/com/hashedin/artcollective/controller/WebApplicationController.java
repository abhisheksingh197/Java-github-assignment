package com.hashedin.artcollective.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hashedin.artcollective.entity.Artist;
import com.hashedin.artcollective.repository.ArtWorkRepository;
import com.hashedin.artcollective.repository.ArtistRepository;
import com.hashedin.artcollective.repository.LeadRepository;
import com.hashedin.artcollective.service.ArtistPortfolioService;
import com.hashedin.artcollective.service.PreferenceService;


@Controller
public final class WebApplicationController {
    
    @Autowired
    private ArtistRepository artistRepository;
    
    @Autowired
    private ArtWorkRepository artworkRepository;
    
    @Autowired
    private ArtistPortfolioService artistPortfolioService;
    
    @Autowired
    private LeadRepository leadRepository;
    
    @Value(value = "${artistdashboard.pagelimit}")
    private Integer pageLimit;
    
    @Value(value = "${env.homePageURL}")
    private String homePageURL;
    
    @Autowired
    private PreferenceService preferenceService;
    
    /**
     * Controller that renders the Access Denied page for unauthorized users
     * @return - Access Denied Model and View
     */
    @RequestMapping("/access-denied")
    public ModelAndView accessDenied() {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("time", new Date());
        model.put("message", "Hello World!");
        model.put("homePageURL", homePageURL);
        return new ModelAndView("access-denied", model);
    }
    
    /**
     * Default routing to the Artist Dashboard
     * @param page - The page limit for displaying the artist details
     * @return - Artist Dashboard model and view
     */
    @RequestMapping("/")
    public ModelAndView artistDashboard(Pageable page) {
        if (page == null || page.getPageSize() != pageLimit) {
            page = new PageRequest(0, pageLimit);
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Artist artist = (Artist) auth.getPrincipal();
        Long artistId = artist.getId();
        ModelAndView model = getPortfolio(artistId, page);
        return model;
    }
    
    /**
     * Controller that allows a manager to upload deductions
     * @return Upload Deductions Model and View
     */
    @RequestMapping("/manage/upload/deductions")
    public ModelAndView uploadDeductionsAsCSV() {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("time", new Date());
        model.put("message", "Hello World!");
        model.put("homePageURL", homePageURL);
        return new ModelAndView("deductions-upload", model);
    }
    
    /**
     * Controller that allows a manager to upload transactions
     * @return Upload Transactions Model and View
     */
    @RequestMapping("/manage/upload/transactions")
    public ModelAndView uploadTransactionsAsCSV() {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("time", new Date());
        model.put("message", "Hello World!");
        model.put("homePageURL", homePageURL);
        return new ModelAndView("transactions-upload", model);
    }
    
    /**
     * Controller used to render the leads generated
     * @param page - Page limit for displaying the leads generated
     * @return
     */
    @RequestMapping("/manage/leads")
    public ModelAndView displayLeads(Pageable page) {
        if (page == null || page.getPageSize() != pageLimit) {
            page = new PageRequest(0, pageLimit);
        }
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("leads", leadRepository.getLeadsOrderByCreatedAt(page));
        model.put("homePageURL", homePageURL);
        return new ModelAndView("leads", model);
    }
    
    /**
     * Helper Method that provides data to the artist dashboard model
     * @param artistId - The logged in artist id
     * @param page -  The page limit for the artist dashboard page
     * @return
     */
    private ModelAndView getPortfolio(Long artistId, Pageable page) {
        Map<String, Object> model = new HashMap<String, Object>();
        Artist artist = artistRepository.findOne(artistId);
        if (artist != null) {
            model.put("artist", artist);
            model.put("lineitems", artistPortfolioService.getLineItemsForPortfoilio(artistId));
            model.put("artworks", artworkRepository.getArtworksByArtist(artistId));
            model.put("dashboardValues", artistPortfolioService.getDashboardValues(artistId));
            model.put("earningsList", artistPortfolioService.getEarningsByArtist(artistId, page));
            model.put("transactionsList", artistPortfolioService.getTransactionsByArtist(artistId, page));
            model.put("deductionsList", artistPortfolioService.getDeductionsByArtist(artistId, page));
            model.put("artworkImages", artistPortfolioService.getArtworkImagesByArtist(artistId));
            model.put("homePageURL", homePageURL);
            return new ModelAndView("artist-dashboard", model);
        }
        return null;
    }
}
