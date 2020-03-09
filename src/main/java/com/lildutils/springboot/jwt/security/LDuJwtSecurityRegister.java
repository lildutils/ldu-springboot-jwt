package com.lildutils.springboot.jwt.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.lildutils.springboot.jwt.filter.LDuJwtAuthenticationFilter;

@Configuration
public class LDuJwtSecurityRegister
{
	@Autowired
	@Lazy
	private LDuJwtAuthenticationFilter lduJwtAuthenticationFilter;

	public void run( HttpSecurity http ) throws Exception
	{
		http.sessionManagement().sessionCreationPolicy( SessionCreationPolicy.STATELESS ).and()
				.addFilterBefore( lduJwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class );
	}

}
