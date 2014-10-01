package com.hashedin.artcollective.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public final class WebApplicationController {
	
	@RequestMapping("/")
	public ModelAndView index() {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("time", new Date());
		model.put("message", "Hello World!");
		
		return new ModelAndView("index", model);
	}
	
	@RequestMapping("/secure/dashboard")
	public ModelAndView dashboard() {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("time", new Date());
		model.put("message", "Hello to Secure World!");
		
		return new ModelAndView("index", model);
	}
	
	@RequestMapping("/proxy/search")
	public ModelAndView search(HttpServletResponse response) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("time", new Date());
		model.put("message", "Hello to Secure World!");
		
		response.setContentType("application/liquid");
		return new ModelAndView("index", model);
	}
}
