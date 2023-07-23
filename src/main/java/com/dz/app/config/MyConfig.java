package com.dz.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class MyConfig extends WebSecurityConfigurerAdapter {

	// here we specify
	// 1. which authenticator we using (we are using daoAuthenticator because we use
	// data from db)
	// 2. Url which we are protecting means to which url we are providing
	// authentication and authorization.
	// like admin user authorization and normal user authorization and public URL

	@Bean
	public UserDetailsService getUserDetailsService() {
		return new CustomUserDetailsServiceImpl();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	// to authenticate user from database
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {

		// here we will provide user detail service to authenticate user
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(getUserDetailsService());
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		return daoAuthenticationProvider;
	}

	// we need Authentication Manager.
	// we need tell which authenticator provider we are using.
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}

	// route configuration.
	// here we tell boot , don't protect all the route
	// protect only those route which we configure
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests()
			.antMatchers("/admin/**").hasRole("ADMIN")
			.antMatchers("/user/**").hasRole("USER")
			.antMatchers("/restuser/**").hasAnyRole("USER")
			.antMatchers("/**").permitAll()
			.and().formLogin().and().csrf().disable();
	}

}