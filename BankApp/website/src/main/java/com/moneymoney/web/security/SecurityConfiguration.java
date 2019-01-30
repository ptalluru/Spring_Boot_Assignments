package com.moneymoney.web.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	/*
	 * @Override protected void configure(AuthenticationManagerBuilder auth) throws
	 * Exception {
	 * auth.inMemoryAuthentication().withUser("ajay").password("1234").roles("USER")
	 * .and().withUser("prasanth") .password("1234").roles("USER",
	 * "ADMIN").and().withUser("prasanth Kumar").password("1234").roles("ADMIN");
	 * 
	 * }
	 * 
	 * @Override protected void configure(HttpSecurity http) throws Exception {
	 * http.httpBasic().and().authorizeRequests().antMatchers("/withdraw").hasRole(
	 * "ADMIN").antMatchers("/**")
	 * .hasRole("USER").antMatchers("/deposit").hasRole("ADMIN").antMatchers(
	 * "/fundTransfer").hasRole("ADMIN")
	 * .antMatchers("/statementDeposit").hasRole("ADMIN"); }
	 */

	@Bean
	@Override
	protected UserDetailsService userDetailsService() {
		List<UserDetails> users = new ArrayList<>();
		users.add(User.withDefaultPasswordEncoder().username("ajay").password("ajay").roles("USER").build());
		// users.add(User.withDefaultPasswordEncoder().username("ajay").password("ajay").roles("").build());
		return new InMemoryUserDetailsManager(users);
	}

}
