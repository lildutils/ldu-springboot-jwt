package com.lildutils.springboot.jwt.config;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.lildutils.springboot.jwt.auth.LDuJwtAuthenticationProvider;
import com.lildutils.springboot.jwt.controller.advice.LDuJwtControllerAdvice;
import com.lildutils.springboot.jwt.filter.LDuJwtAuthenticationFilter;
import com.lildutils.springboot.jwt.security.LDuJwtSecurityRegister;
import com.lildutils.springboot.jwt.service.LDuJwtService;
import com.lildutils.springboot.jwt.service.impl.LDuJwtServiceImpl;

@Configuration
@ComponentScan(basePackageClasses =
{ LDuJwtControllerAdvice.class, LDuJwtSecurityRegister.class })
public class LDuJwtConfigurer
{
	@Autowired
	private Environment				environment;

	@Autowired
	private AuthenticationManager	authenticationManager;

	@Bean("authenticationFilter")
	public Filter getAuthenticationFilter() throws Exception
	{
		final LDuJwtConfig config = new LDuJwtConfig();
		config.setAuthorizationHeader( environment.getProperty( "ldu.jwt.auth.header" ) );
		config.setAuthorizationSchema( environment.getProperty( "ldu.jwt.auth.schema" ) );
		return new LDuJwtAuthenticationFilter( authenticationManager, config );
	}

	@Bean("authenticationProvider")
	public AuthenticationProvider getLDuJwtAuthenticationProvider()
	{
		return new LDuJwtAuthenticationProvider();
	}

	@Bean("passwordEncoder")
	public PasswordEncoder getPasswordEncoder()
	{
		return new BCryptPasswordEncoder();
	}

	@Bean("jwtService")
	public LDuJwtService getLDuJwtService()
	{
		final LDuJwtConfig config = new LDuJwtConfig();
		config.setSecret( environment.getProperty( "ldu.jwt.secret" ) );
		config.setIssuer( environment.getProperty( "ldu.jwt.issuer" ) );
		return new LDuJwtServiceImpl( config );
	}

}
