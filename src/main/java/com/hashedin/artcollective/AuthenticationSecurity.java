package com.hashedin.artcollective;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

class AuthenticationSecurity extends
		GlobalAuthenticationConfigurerAdapter {

	private final UserDetailsService userDetailsService;
	
	public AuthenticationSecurity(UserDetailsService service) {
		userDetailsService = service;
	}
	
	@Override
	public void init(AuthenticationManagerBuilder auth) throws Exception {
		
		/* 
		 * Always assign "permissions" to users
		 * See http://springinpractice.com/2010/10/27
		 * /quick-tip-spring-security-role-based-authorization-and-permissions/
		 */
		auth.inMemoryAuthentication()
				.withUser("superadmin").password("superadmin").roles("SUPERADMIN").and()
				.withUser("manager").password("manager").roles("MANAGER");
		
		auth.userDetailsService(userDetailsService);
	}
}