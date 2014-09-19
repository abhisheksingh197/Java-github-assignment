package com.hashedin.artcollective;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



@EnableWebSecurity
@Configuration
@EnableAutoConfiguration
@ComponentScan
public class Main extends WebMvcConfigurerAdapter {

	private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
	
	public static void main(String args[]) {
		//System.setProperty("spring.profiles.active", "prod");
		SpringApplication.run(Main.class, args);
		LOGGER.info("Started Application Art Collective");
	}
	@Value("${shopify.apikey}")
 	private String shopifyApiKey;
 
 	@Value("${shopify.apipassword}")
 	private String shopifyApiPassword;
 
 	@Value("${shopify.authurl}")
 	private String shopifyAuthUrl;

 	@Value("${tinEye.apikey}")
	private String tinEyeApiKey;
	
	@Value("${tinEye.apipassword}")
	private String tinEyeApiPassword;
	
	@Value("${tinEye.authurl}")
	private String tinEyeAuthUrl;
	
	@Bean
	public RestTemplate getRestTemplate() {
		BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
		UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(
				shopifyApiKey, 
				shopifyApiPassword);
		
		AuthScope shopifyScope = new AuthScope(shopifyAuthUrl, -1);
		credentialsProvider.setCredentials(shopifyScope, credentials);
		
		
		AuthScope tinEyeScope = new AuthScope(tinEyeAuthUrl, -1);
		UsernamePasswordCredentials tinEyeCredentials = new UsernamePasswordCredentials(
				tinEyeApiKey, 
				tinEyeApiPassword);
		
		credentialsProvider.setCredentials(tinEyeScope, tinEyeCredentials);
		
		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
		HttpClient httpClient = HttpClientBuilder
								.create()
								.setDefaultCredentialsProvider(credentialsProvider)
								.build();
		factory.setHttpClient(httpClient);
		
		RestTemplate template = new RestTemplate(factory);
		return template;
	}

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
