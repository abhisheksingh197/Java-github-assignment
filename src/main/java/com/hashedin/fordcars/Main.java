package com.hashedin.fordcars;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.BasicCredentialsProvider;
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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@EnableWebSecurity
@Configuration
@EnableAutoConfiguration
@ComponentScan
public class Main extends WebMvcConfigurerAdapter {

	private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
	private UserDetailsService uds;
	public static void main(String args[]) {
		//System.setProperty("spring.profiles.active", "dev");
		SpringApplication.run(Main.class, args);
		LOGGER.info("Started Application Art Collective");
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
	public RestTemplate getRestTemplate() {
		
		BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
		UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(
				"be145ef230d51528b314f245551fc148", 
				"cdfb381000588518e2bb3981444a86d3");
		
		AuthScope shopifyScope = new AuthScope("artcollectiveindialtd.myshopify.com", -1);
		credentialsProvider.setCredentials(shopifyScope, credentials);
		
		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
		HttpClient httpClient = HttpClientBuilder
								.create()
								.setDefaultCredentialsProvider(credentialsProvider)
								.build();
		factory.setHttpClient(httpClient);
		
		RestTemplate template = new RestTemplate(factory);
		return template;
	}

}
