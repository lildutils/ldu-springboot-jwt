package com.lildutils.springboot.jwt.security;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class LDuJwtSecurityRegister
{
	@Autowired
	@Qualifier("authenticationFilter")
	@Lazy
	private Filter authenticationFilter;

	public void run( HttpSecurity http ) throws Exception
	{
		http.sessionManagement().sessionCreationPolicy( SessionCreationPolicy.STATELESS ).and()
				.addFilterBefore( authenticationFilter, UsernamePasswordAuthenticationFilter.class );
	}

}
