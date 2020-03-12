package com.lildutils.springboot.jwt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.lildutils.springboot.jwt.auth.LDuJwtAuthenticationProvider;
import com.lildutils.springboot.jwt.filter.LDuJwtAuthenticationFilter;
import com.lildutils.springboot.jwt.filter.LDuJwtAuthenticationFilterConfig;
import com.lildutils.springboot.jwt.security.LDuJwtWebSecurityConfigurer;
import com.lildutils.springboot.jwt.service.LDuJwtService;
import com.lildutils.springboot.jwt.service.impl.LDuJwtServiceImpl;

@Configuration
@ComponentScan(basePackageClasses = LDuJwtWebSecurityConfigurer.class)
public class LDuJwtConfigurer
{
	@Autowired
	private Environment				environment;

	@Autowired
	private AuthenticationManager	authenticationManager;

	@Bean("lduJwtAuthenticationFilter")
	public LDuJwtAuthenticationFilter getAuthenticationFilter() throws Exception
	{
		final LDuJwtAuthenticationFilterConfig config = new LDuJwtAuthenticationFilterConfig();
		config.setAuthorizationHeader( environment.getProperty( "ldu.jwt.auth.header" ) );
		config.setAuthorizationSchema( environment.getProperty( "ldu.jwt.auth.schema" ) );
		return new LDuJwtAuthenticationFilter( authenticationManager, config );
	}

	@Bean("lduJwtAuthenticationProvider")
	public LDuJwtAuthenticationProvider getLDuJwtAuthenticationProvider()
	{
		return new LDuJwtAuthenticationProvider( getLDuJwtService() );
	}

	@Bean("lduJwtService")
	public LDuJwtService getLDuJwtService()
	{
		final LDuJwtAuthenticationFilterConfig config = new LDuJwtAuthenticationFilterConfig();
		config.setSecret( environment.getProperty( "ldu.jwt.secret" ) );
		config.setIssuer( environment.getProperty( "ldu.jwt.issuer" ) );
		return new LDuJwtServiceImpl( config );
	}

	@Bean("passwordEncoder")
	public PasswordEncoder getPasswordEncoder()
	{
		return new BCryptPasswordEncoder();
	}

}
