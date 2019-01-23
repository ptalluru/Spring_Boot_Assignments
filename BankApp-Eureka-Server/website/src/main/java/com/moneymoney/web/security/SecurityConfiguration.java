package com.moneymoney.web.security;

import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfiguration{ /*extends WebSecurityConfigurerAdapter{

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("user1").password("1234")
		.roles("USER").and().withUser("admin1").password("secret1")
		.roles("USER", "ADMIN");

	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.httpBasic().and().authorizeRequests().antMatchers("/students/**")
		.hasRole("USER").antMatchers("/**").hasRole("ADMIN").and()
		.csrf().disable().headers().frameOptions().disable();
	}*/

}
