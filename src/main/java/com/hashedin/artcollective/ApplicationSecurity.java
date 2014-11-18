package com.hashedin.artcollective;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

class ApplicationSecurity extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		
		http.authorizeRequests()
			.antMatchers("/").hasAuthority("ARTIST")
			.antMatchers("/dashboard").authenticated()
			.antMatchers("/assets/**").permitAll()
			.antMatchers("/admin/**").hasRole("SUPERADMIN")
			.antMatchers("/api/**").permitAll()
			.antMatchers("/proxy/**").permitAll()
			.antMatchers("/api/uploadImage").permitAll()
			.antMatchers("/leads").permitAll()
			.antMatchers("/manage/**").hasRole("MANAGER")
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
	}
}