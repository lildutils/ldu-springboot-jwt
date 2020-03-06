package com.lildutils.springboot.jwt.service;

import java.util.Map;

import com.auth0.jwt.interfaces.DecodedJWT;

public interface LDuJwtService
{
	String pack( String subject, Map<String, String> claims, long expirationTime );

	DecodedJWT unpack( String jwt );

}
