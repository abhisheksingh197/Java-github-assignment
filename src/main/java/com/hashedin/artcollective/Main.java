package com.hashedin.artcollective;

import java.util.Properties;

import javax.imageio.ImageIO;

import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.PersistenceConfiguration;
import net.sf.ehcache.config.PersistenceConfiguration.Strategy;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleKeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.hashedin.artcollective.service.ArtistPortfolioService;


@EnableWebSecurity
@Configuration
@EnableAutoConfiguration
@ComponentScan
public class Main extends WebMvcConfigurerAdapter implements CachingConfigurer {

	private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
	
	public static void main(String args[]) {
		//System.setProperty("spring.profiles.active", "dev");
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
	
	@Value("${mail.protocol}")
    private String protocol;
    @Value("${mail.host}")
    private String host;
    @Value("${mail.port}")
    private int port;
    @Value("${mail.smtp.auth}")
    private boolean auth;
    @Value("${mail.smtp.starttls.enable}")
    private boolean starttls;
    @Value("${mail.from}")
    private String from;
    @Value("${mail.username}")
    private String username;
    @Value("${mail.password}")
    private String password;
    
	@Autowired
	private ArtistPortfolioService userDetailsService;
	
	
	@Bean
	public RestTemplate getRestTemplate() {
		/*
		 * Crazy bug in ImageIO. At random times, it stops loading images
		 * 
		 * Cause :
		 *   This line in ImageIO throws NullPointerException
		 *   private static final IIORegistry theRegistry = IIORegistry.getDefaultInstance();
		 * 
		 *  ... because 
		 *  AppContext context = AppContext.getAppContext();
		 *  context is null
		 *  
		 *  Our HACK:
		 *  Loading an image using ImageIO very early in the application startup cycle seems to work
		 *  ... and also continues working thereafter. 
		 *  Why this is so, nobody knows
		 *  
		 *  So, here we are. We load an image at startup - 
		 *  and then pray that image loading works forever after that
		 *  
		 *  P.S. This hack can be put in any place that is loaded by Spring. 
		 *  Got nothing to do with RestTemplate
		 */
		try {
			ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("lucky_image.jpg"));
		}
		catch (Throwable t) {
			LOGGER.error("Cannot load images using ImageIO. A lot of things will fail subsequently");
		}
		
		/*END OF ImageIO HACK*/
		
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
		return new AuthenticationSecurity(userDetailsService);
	}
	
    @Bean(destroyMethod="shutdown")
    public net.sf.ehcache.CacheManager ehCacheManager() {
        CacheConfiguration cacheConfiguration = new CacheConfiguration();
        cacheConfiguration.setName("artcollective");
        cacheConfiguration.setMemoryStoreEvictionPolicy("LRU");
        cacheConfiguration.setMaxEntriesLocalHeap(100);
        cacheConfiguration.setMaxEntriesLocalDisk(1000);
        
        PersistenceConfiguration persistenceConfiguration = new PersistenceConfiguration();
        persistenceConfiguration.strategy(Strategy.LOCALTEMPSWAP);
        cacheConfiguration.persistence(persistenceConfiguration);
        
        net.sf.ehcache.config.Configuration config = new net.sf.ehcache.config.Configuration();
        config.addCache(cacheConfiguration);

        return net.sf.ehcache.CacheManager.newInstance(config);
    }

    @Bean
    @Override
    public CacheManager cacheManager() {
        return new EhCacheCacheManager(ehCacheManager());
    }

    @Bean
    @Override
    public KeyGenerator keyGenerator() {
        return new SimpleKeyGenerator();
    }
    
    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        Properties mailProperties = new Properties();
        mailProperties.put("mail.smtp.auth", auth);
        mailProperties.put("mail.smtp.starttls.enable", starttls);
        mailSender.setJavaMailProperties(mailProperties);
        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setProtocol(protocol);
        mailSender.setUsername(username);
        mailSender.setPassword(password);
        return mailSender;
    }

}
