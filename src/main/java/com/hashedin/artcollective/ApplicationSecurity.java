package com.hashedin.artcollective;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

class ApplicationSecurity extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		
		
		http.authorizeRequests()
			.antMatchers("/", "/home").permitAll()
			.antMatchers("/secure/artist").hasAuthority("PERM_READ_ARTIST_DASHBOARD")
			.antMatchers("/assets/**").permitAll()
			.antMatchers("/manage/**").hasRole("SUPERADMIN")
			.antMatchers("/api/**").permitAll()
			.antMatchers("/proxy/**").permitAll()
			.antMatchers("/api/uploadImage").permitAll()
			.anyRequest().fullyAuthenticated();
		
		http
			.formLogin()
				.loginPage("/login").failureUrl("/login?error").permitAll()
			.and()
				.logout().logoutRequestMatcher(
					new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login");
		http.csrf().disable();
	}
}