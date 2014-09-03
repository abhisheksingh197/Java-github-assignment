package com.hashedin.artcollective;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;

class AuthenticationSecurity extends
		GlobalAuthenticationConfigurerAdapter {

	@Override
	public void init(AuthenticationManagerBuilder auth) throws Exception {
		
		/* 
		 * Always assign "permissions" to users
		 * See http://springinpractice.com/2010/10/27
		 * /quick-tip-spring-security-role-based-authorization-and-permissions/
		 */
		auth.inMemoryAuthentication()
				.withUser("superadmin").password("superadmin").roles("SUPERADMIN").and()
				.withUser("artist").password("artist")
					.authorities("PERM_READ_ARTIST_DASHBOARD").and()
				.withUser("shopper").password("shopper")
					.authorities("PERM_READ_CUSTOMER_ORDERS").and();
	}
}