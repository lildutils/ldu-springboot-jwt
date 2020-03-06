package com.lildutils.springboot.jwt.auth;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.lildutils.springboot.jwt.service.LDuJwtService;

public class LDuJwtAuthenticationProvider implements AuthenticationProvider
{
	@Autowired
	private LDuJwtService jwtService;

	@Override
	public boolean supports( Class<?> authentication )
	{
		return LDuJwtAuthenticationToken.class.isAssignableFrom( authentication );
	}

	@Override
	public Authentication authenticate( Authentication authentication )
	{
		final String jwt = (String) ((LDuJwtAuthenticationToken) authentication).getCredentials();
		final DecodedJWT decodedJwt = jwtService.unpack( jwt );
		return new LDuJwtAuthenticationToken( decodedJwt.getSubject(), jwt, new ArrayList<>() );
	}

}
