package com.hashedin.fordcars.controller;

import java.util.Locale;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

public class JsonViewResolver implements ViewResolver {
	
	public static final ViewResolver INSTANCE = new JsonViewResolver();
    
	/**
      * Get the view to use.
      *
      * @return Always returns an instance of {@link MappingJacksonJsonView}.
     */
    @Override
    public View resolveViewName(String viewName, Locale locale) throws Exception {
    	MappingJackson2JsonView view = new MappingJackson2JsonView();
        view.setPrettyPrint(true); 
        return view;
    }
}