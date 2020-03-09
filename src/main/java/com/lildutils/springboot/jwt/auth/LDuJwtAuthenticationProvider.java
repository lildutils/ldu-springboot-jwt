package com.lildutils.springboot.jwt.auth;

import java.util.ArrayList;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.lildutils.springboot.jwt.service.LDuJwtService;

public class LDuJwtAuthenticationProvider implements AuthenticationProvider
{
	private LDuJwtService jwtService;

	public LDuJwtAuthenticationProvider( LDuJwtService jwtService )
	{
		super();
		this.jwtService = jwtService;
	}

	@Override
	public boolean supports( Class<?> authentication )
	{
		return authentication.equals( UsernamePasswordAuthenticationToken.class );
	}

	@Override
	public LDuJwtAuthenticationToken authenticate( Authentication authentication )
	{
		final String jwt = (String) authentication.getCredentials();
		final DecodedJWT decodedJwt = jwtService.unpack( jwt );
		return new LDuJwtAuthenticationToken( decodedJwt.getSubject(), jwt, new ArrayList<>() );
	}

}
