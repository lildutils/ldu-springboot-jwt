package com.lildutils.springboot.jwt.auth;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

@SuppressWarnings("serial")
public class LDuJwtAuthenticationToken extends UsernamePasswordAuthenticationToken
{
	public LDuJwtAuthenticationToken( Object credentials )
	{
		super( null, credentials );
	}

	public LDuJwtAuthenticationToken( Object principal, Object credentials,
			Collection<? extends GrantedAuthority> authorities )
	{
		super( principal, credentials, authorities );
	}

}
