package com.hashedin.javatemplate;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.ebaysf.web.cors.CORSFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


class ApplicationSecurity extends WebSecurityConfigurerAdapter {

	private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationSecurity.class);
	
	@Value(value = "${cors.allowed.origins}")
	private String corsAllowedOrigins;
	
	@Value(value = "${cors.allowed.methods}")
	private String corsAllowedMethods;
	
	@Value(value = "${cors.allowed.headers}")
	private String corsAllowedHeaders;
	
	@Value(value = "${cors.exposed.headers}")
	private String corsExposedHeaders;
	
	@Value(value = "${cors.support.credentials}")
	private String corsSuportCredentials;
	
	@Value(value = "${cors.preflight.maxage}")
	private String corsPreflightMaxAge;
	
	@Value(value = "${cors.request.decorate}")
	private String corsDecorateRequest;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		/*
		 * Disable http headers, because it messes up caching completely
		 */
		http.headers().disable();
		
		http.authorizeRequests()
			.antMatchers("/secure/**").hasRole("SUPERADMIN")
			.antMatchers("/admin/**").permitAll()
			.anyRequest().fullyAuthenticated();
		
		http
			.formLogin()
				.loginPage("/login").failureUrl("/login?error").permitAll()
			.and()
				.logout().logoutRequestMatcher(
					new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login")
			.and()		
				.exceptionHandling().accessDeniedPage("/access-denied");
		
		http.csrf().disable();
		
		addCorsFilter(http);
	}
	
	private Map<String, String> getCorsConfiguration() {
		Map<String, String> config = new HashMap<String, String>();
		
		/*
		 * because we are setting ServletContext to null, logging cannot be enabled
		 * See CORSFilter.log method for more information
		 */
		config.put("cors.logging.enabled", "false");
		
		config.put("cors.allowed.origins", corsAllowedOrigins);
		config.put("cors.allowed.methods", corsAllowedMethods);
		config.put("cors.allowed.headers", corsAllowedHeaders);
		config.put("cors.exposed.headers", corsExposedHeaders);
		config.put("cors.support.credentials", corsSuportCredentials);
		config.put("cors.preflight.maxage", corsPreflightMaxAge);
		config.put("cors.decorate.request", corsDecorateRequest);
		
		return config;
	}
	
	private void addCorsFilter(HttpSecurity http) {
		Filter corsFilter = new CORSFilter();
		Map<String, String> configuration = getCorsConfiguration();
		try {
			corsFilter.init(new MapBasedFilterConfig("CORS Filter", configuration));
		}
		catch (ServletException e) {
			LOGGER.error("Could not init CORSFilter. "
					+ "Application will not work across cross-domains", e);
		}
		
		/*
		 * Add filter to spring security chain
		 */
		http.addFilterAfter(corsFilter, AbstractPreAuthenticatedProcessingFilter.class);
		
	}
		
}
class MapBasedFilterConfig implements FilterConfig {

	private final Map<String, String> initParams;
	private final String filterName;
	
	public MapBasedFilterConfig(String filterName, Map<String, String> initParams) {
		this.filterName = filterName;
		if (initParams != null && !initParams.isEmpty()) {
			this.initParams = Collections.unmodifiableMap(initParams);
		}
		else {
			this.initParams = Collections.emptyMap();
		}
	}
	
	@Override
	public String getFilterName() {
		return this.filterName;
	}

	/*
	 * We know that CORSFilter doesn't really use the ServletContext
	 * .. except for logging
	 * We don't really care about loggin in CORSFilter, so to hell with it
	 */
	@Override
	public ServletContext getServletContext() {
		return null;
	}

	@Override
	public String getInitParameter(String name) {
		return this.initParams.get(name);
	}

	@Override
	public Enumeration<String> getInitParameterNames() {
		return Collections.enumeration(this.initParams.keySet());
	}
	
}

