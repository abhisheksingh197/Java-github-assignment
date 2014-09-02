package com.hashedin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.hashedin.controller.JsonViewResolver;


@EnableWebSecurity
@Configuration
@EnableAutoConfiguration
@ComponentScan
public class Main extends WebMvcConfigurerAdapter {

	private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

	public static void main(String args[]) {
		SpringApplication.run(Main.class, args);
		LOGGER.info("Started Application");
	}
	
	/**
	 * This exists to override default HTTP client library to use
	 * Apache components HTTPClient implementation which is more robust
	 * and configurable.
	 */
	@Bean
	public RestTemplate getRestTemplate() {
		
		/* If its needed to build authentication parameters into this */
		/*
		BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
		UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(
				<USER_NAME>, 
				<PASSWORD>);
		
		AuthScope authScope = new AuthScope(<AUTH_SCOPE>, -1);
		credentialsProvider.setCredentials(authScope, credentials);
		*/
		
		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
		HttpClient httpClient = HttpClientBuilder
								.create()
								// Uncomment if authentication is needed.
								// .setDefaultCredentialsProvider(credentialsProvider)
								.build();
		factory.setHttpClient(httpClient);
		
		return new RestTemplate(factory);
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		/*
		 * Forcefully return JSON when showModel=true
		 * Helps debugging views
		 */
		registry.addInterceptor(new HandlerInterceptorAdapter() {

			@Override
			public void postHandle(HttpServletRequest request,
					HttpServletResponse response, Object handler,
					ModelAndView modelAndView) throws Exception {
				
				if ("true".equalsIgnoreCase(request.getParameter("showModel"))) {
					modelAndView.setView(
							JsonViewResolver.INSTANCE.resolveViewName(null, null));
				}
			}
			
		});
	};
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/login").setViewName("login");
	}

	@Bean
	public ApplicationSecurity getApplicationSecurity() {
		return new ApplicationSecurity();
	}
	
	@Bean
	public AuthenticationSecurity authenticationSecurity() {
		return new AuthenticationSecurity();
	}

}
