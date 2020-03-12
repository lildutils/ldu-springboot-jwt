package com.lildutils.springboot.jwt.service.impl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.lildutils.springboot.jwt.filter.LDuJwtAuthenticationFilterConfig;
import com.lildutils.springboot.jwt.service.LDuJwtService;
import com.lildutils.springboot.jwt.service.ex.LDuJwtException;

@Service
public class LDuJwtServiceImpl implements LDuJwtService
{
	private final Algorithm	algorithm;
	private final String	issuer;

	public LDuJwtServiceImpl( LDuJwtAuthenticationFilterConfig config )
	{
		super();
		this.algorithm = Algorithm.HMAC256( config.getSecret().getBytes() );
		this.issuer = config.getIssuer();
	}

	@Override
	public String pack( String subject, Map<String, String> claims, long expirationTime )
	{
		try
		{
			final LocalDateTime now = LocalDateTime.now();

			final String jwtId = String.valueOf( (new Date()).getTime() + (int) (Math.random() * 100) );

			final Date createdAt = Date.from( now.atZone( ZoneId.systemDefault() ).toInstant() );
			final Date expiration = Date
					.from( now.plusSeconds( expirationTime ).atZone( ZoneId.systemDefault() ).toInstant() );

			final Builder jwtBuilder = JWT.create().withJWTId( jwtId ).withSubject( subject ).withIssuedAt( createdAt )
					.withExpiresAt( expiration ).withNotBefore( createdAt ).withAudience( "JWT" ).withIssuer( issuer );

			if( claims != null && !claims.isEmpty() )
			{
				for( Entry<String, String> entry : claims.entrySet() )
				{
					jwtBuilder.withClaim( entry.getKey(), entry.getValue() );
				}
			}
			return jwtBuilder.sign( algorithm );
		}
		catch( Exception e )
		{
			throw new LDuJwtException( e.getMessage() );
		}
	}

	@Override
	public DecodedJWT unpack( String jwt )
	{
		try
		{
			validate( jwt );
			return JWT.decode( jwt );
		}
		catch( Exception e )
		{
			throw new LDuJwtException( e.getMessage() );
		}
	}

	@Override
	public boolean validate( String jwt )
	{
		try
		{
			return JWT.require( algorithm ).build().verify( jwt ) != null;
		}
		catch( Exception e )
		{
			throw new LDuJwtException( e.getMessage() );
		}
	}

}
