package com.lildutils.springboot.jwt.config;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.lildutils.springboot.jwt.auth.LDuJwtAuthenticationProvider;
import com.lildutils.springboot.jwt.filter.LDuJwtAuthenticationFilter;
import com.lildutils.springboot.jwt.service.LDuJwtService;
import com.lildutils.springboot.jwt.service.impl.LDuJwtServiceImpl;

@Configuration
@Order(200)
public class LDuJwtConfigurer extends WebSecurityConfigurerAdapter
{
	@Autowired
	private Environment environment;

	@Override
	protected void configure( HttpSecurity http ) throws Exception
	{
		super.configure( http );
		http.sessionManagement().sessionCreationPolicy( SessionCreationPolicy.STATELESS ).and()
				.addFilterBefore( getAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class );;
	}

	@Bean("authenticationFilter")
	public Filter getAuthenticationFilter() throws Exception
	{
		final LDuJwtConfig config = new LDuJwtConfig();
		config.setAuthorizationHeader( environment.getProperty( "ldu.jwt.auth.header" ) );
		config.setAuthorizationSchema( environment.getProperty( "ldu.jwt.auth.schema" ) );
		return new LDuJwtAuthenticationFilter( getAuthenticationManager(), config );
	}

	@Bean("authenticationManager")
	public AuthenticationManager getAuthenticationManager() throws Exception
	{
		return super.authenticationManager();
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
