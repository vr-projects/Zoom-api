package com.userOnboard.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.userOnboard.filter.JWTRequestFilter;
import com.userOnboard.service.impl.MyUserDetailsService;

@SuppressWarnings("deprecation")
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	MyUserDetailsService myUserDetailService;
	
	@Autowired
	JWTRequestFilter jwtRequestFilter;
	
	public static final String[] PUBLIC_URL = {
			"/authenticate" ,
			"/v2/api-docs",
			"/swagger-resources/**",
			"/swagger-ui/**",
			"/webjars/**"
	};
	
	
	public SecurityConfig(MyUserDetailsService myUserDetailService) {
		super();
		this.myUserDetailService = myUserDetailService;
	}


	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(myUserDetailService);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		// Set permissions on endpoints
		/*
		 * http.authorizeRequests() // Our public endpoints
		 * .antMatchers("/api/public/**").permitAll() .antMatchers(HttpMethod.GET,
		 * "/api/author/**").permitAll() .antMatchers(HttpMethod.POST,
		 * "/api/author/search").permitAll() .antMatchers(HttpMethod.GET,
		 * "/api/book/**").permitAll() .antMatchers(HttpMethod.POST,
		 * "/api/book/search").permitAll() // Our private endpoints
		 * .anyRequest().authenticated();
		 */
		
		
		// Enable CORS and disable CSRF
      //  http = http.cors().and().csrf().disable();
		
		
		  http.cors().and().csrf().disable().authorizeRequests()
		  .antMatchers(PUBLIC_URL).permitAll()
		  .anyRequest().authenticated().and().sessionManagement().sessionCreationPolicy
		  (SessionCreationPolicy.STATELESS);
		  
		  http.addFilterAfter(jwtRequestFilter,
		  UsernamePasswordAuthenticationFilter.class);
		 
		 
	}
	
	@Bean
	public PasswordEncoder passwordEncoder () {
		return NoOpPasswordEncoder.getInstance();
	}
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
}
